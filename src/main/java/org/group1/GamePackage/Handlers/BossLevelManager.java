package org.group1.GamePackage.Handlers;

import org.group1.GamePackage.Components.Enemy.BossComponent;
import org.group1.GamePackage.Components.Player.PlayerComponent;
import org.group1.GamePackage.Components.UI.TimerComponent;
import org.group1.GamePackage.Factory.BossFactory.BossType;
import org.group1.GamePackage.Music.AudioManager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.ui.ProgressBar;

import javafx.scene.paint.Color;
import javafx.util.Duration;

public class BossLevelManager extends Component {

    // Needed score to spawn boss
    private static int SCORE_TO_SPAWN = 100;
    private static ProgressBar healthBar;

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
        healthBar.setTranslateY(65);
        healthBar.setMinValue(0);
        healthBar.setMaxValue(BossComponent.getBOSS_HEALTH());
        healthBar.currentValueProperty().setValue(BossComponent.getBOSS_HEALTH());
        // set it to false first then on bossSpawn make it visible
        healthBar.setVisible(false);
        FXGL.addUINode(healthBar);
    }

    // Override onUpdate and call this class methods
    @Override
    public void onUpdate(double tpf) {
        checkBossSpawn();
        checkGameOver();
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
        if (gameOverTriggered)
            return;

        if (PlayerComponent.getLives() == 0 || timerComponent.timeEnded()) {
            gameOverTriggered = true;
            FXGL.spawn("death_overlay");
            FXGL.getGameTimer().runOnceAfter(() -> {
                player.die();
            }, Duration.seconds(3));
        }
    }

    public void spawnBoss() {
        // cant call FXGL without it initializing learned the hard way
        int X_LOCATION = 700;
        int Y_LOCATION = 160;
        FXGL.spawn("boss", X_LOCATION, Y_LOCATION);

        // kill mini bosses on boss spawn
        FXGL.getGameWorld().getEntitiesByType(BossType.MINI_BOSS)
                .forEach(Entity::removeFromWorld);
    }

    public boolean inBossLevel() {
        return PlayerComponent.getScore() >= SCORE_TO_SPAWN;
    }

    public static boolean getBossLevel() {
        return bossLevel;
    }

    public static ProgressBar getHealthBar() {
        return healthBar;
    }

    public static int getScoreToSpawn() {
        return SCORE_TO_SPAWN;
    }

    // reset static fields coz FXGL doesnt reset those
    public static void reset() {
        bossLevel = false;
    }

}
