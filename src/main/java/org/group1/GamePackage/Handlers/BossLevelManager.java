package org.group1.GamePackage.Handlers;

import com.almasb.fxgl.dsl.FXGL;

import javafx.scene.paint.Color;
import javafx.util.Duration;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.ui.ProgressBar;

import org.group1.GamePackage.Components.PlayerComponent;

public class BossLevelManager extends Component {

    // Needed score to spawn boss
    private int SCORE_TO_SPAWN = 100;
    private static int BOSS_HEALTH = 100;
    private ProgressBar healthBar;


    private static final double FLASH_DURATION = 0.3; // seconds
    private static final double FLASH_INTERVAL = 0.1; // seconds between flashes
    private boolean visible = true;

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
        FXGL.addUINode(healthBar);
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
        triggerDamage();

        if (dead()) {
            FXGL.removeUINode(healthBar);
        }
    }

    public boolean dead() {
        return BOSS_HEALTH <= 0;
        }

    // ABSOLUTELY COPIED FROM WREN THE GOAT
    private void triggerDamage() {
        var flashTask = FXGL.getGameTimer().runAtInterval(() -> {
            visible = !visible;
            entity.getViewComponent().setOpacity(visible ? 1.0 : 0.3);
        }, Duration.seconds(FLASH_INTERVAL));

        FXGL.getGameTimer().runOnceAfter(() -> {
            flashTask.expire();
            entity.getViewComponent().setOpacity(1.0);
        }, Duration.seconds(FLASH_DURATION));
    }
}

