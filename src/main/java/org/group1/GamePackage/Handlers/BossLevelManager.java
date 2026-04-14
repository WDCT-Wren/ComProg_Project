package org.group1.GamePackage.Handlers;

import com.almasb.fxgl.dsl.FXGL;

import javafx.scene.paint.Color;
import javafx.util.Duration;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.ui.ProgressBar;

import org.group1.GamePackage.Components.Player.PlayerComponent;
import org.group1.GamePackage.Components.UI.GameOverComponent;
import org.group1.GamePackage.Components.UI.TimerComponent;
import org.group1.GamePackage.Music.AudioManager;

public class BossLevelManager extends Component {

    private static int BOSS_HEALTH = 100;

    // Needed score to spawn boss
    private int SCORE_TO_SPAWN = 100;
    private ProgressBar healthBar;

    private PlayerComponent player;
    private TimerComponent timerComponent;
    private AudioManager audioManager;
    private boolean bossSpawned = false;
    private static boolean bossLevel = false;
    private boolean gameOverTriggered = false;

    public BossLevelManager(PlayerComponent player, TimerComponent timerComponent, AudioManager audioManager) {
        this.player = player;
        this.timerComponent = timerComponent;
        this.audioManager = audioManager;

    }

    @Override
    public void onAdded() {
        healthBar = new ProgressBar(false);
        healthBar.setFill(Color.RED);
        healthBar.setBackgroundFill(Color.BLACK);
        healthBar.setWidth(400);
        healthBar.setHeight(25);
        healthBar.setTranslateX((FXGL.getAppWidth() - 400) / 2);
        healthBar.setTranslateY(20);
        healthBar.setMinValue(0);
        healthBar.setMaxValue(BOSS_HEALTH);
        healthBar.currentValueProperty().setValue(BOSS_HEALTH);
        // set it to false first then on bossSpawn make it visible
        healthBar.setVisible(false);
        FXGL.addUINode(healthBar);
    }

    // Override onUpdate and call this class methods
    @Override
    public void onUpdate(double tpf) {
        checkWinCondition();
        checkBossSpawn();
        checkGameOver();
    }

    private void checkWinCondition() {
        if(dead()) {
            GameOverComponent.winGame();
        }
    }

    // check if inBossLevel and boss is not spawned,
    // then sets the booleans to true, spawn boss and set the healthBar visible
    private void checkBossSpawn() {
        if (inBossLevel() && !bossSpawned) {
            bossLevel = true;
            bossSpawned = true;
            spawnBoss();
            healthBar.setVisible(true);
        }
    }


    // if gameOverTriggered return so it only updates one time
    // if live 0 or timer ended, trigger game over
    private void checkGameOver() {
        if (gameOverTriggered) return;

        if (player.getLives() == 0 || timerComponent.timeEnded()) {
            gameOverTriggered = true;
            FXGL.spawn("death_overlay");
            FXGL.getGameTimer().runOnceAfter(() -> {
                audioManager.playGameOver();
                player.die();
                FXGL.getGameController().pauseEngine();
            }, Duration.seconds(3));
        }
    }

    public void spawnBoss() {
        // cant call FXGL without it initializing learned the hard way
        int X_LOCATION = 700;
        int Y_LOCATION = 160;
        FXGL.spawn("boss", X_LOCATION, Y_LOCATION);
    }

    public boolean inBossLevel() {
        return PlayerComponent.getScore() >= SCORE_TO_SPAWN;
    }

    public void takeDamage() {
        BOSS_HEALTH--;
        healthBar.currentValueProperty().setValue(BOSS_HEALTH);

        if (dead()) {
            FXGL.removeUINode(healthBar);
        }
    }

    private boolean dead() {
        return BOSS_HEALTH <= 0;
    }

    public static boolean getBossLevel(){
        return bossLevel;
    }
}

