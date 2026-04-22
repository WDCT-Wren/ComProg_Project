package org.group1.GamePackage.UI;

import org.group1.GamePackage.Components.Player.PlayerComponent;
import org.group1.GamePackage.Components.UI.TimerComponent;
import org.group1.GamePackage.Handlers.BossLevelManager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.ui.ProgressBar;

import javafx.beans.binding.Bindings;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class HUDInterface {

    private static Entity currentLivesHUD;
    private static boolean flashing = false;

    public Text displayTimer(Entity time) {
        Text timerText = FXGL.getUIFactoryService().newText("", Color.LIGHTGRAY, 48);


        // bind the text to the property so it auto-updates
        timerText.textProperty().bind(
                Bindings.createStringBinding(() -> {
                    // takes the time from TimerComponent class.
                    int total = time.getComponent(TimerComponent.class).timeLeftProperty().get();

                    // Turns the total time (in seconds) into a proper minute format
                    int minutes = total / 60;
                    int seconds = total % 60;

                    // returns the final string into m:ss format
                    return String.format("%2d:%02d", minutes, seconds);
                }, time.getComponent(TimerComponent.class).timeLeftProperty()));

        timerText.setTranslateX(580);
        timerText.setTranslateY(50);
        FXGL.addUINode(timerText);
        return timerText;
    }

    public Text displayBoostLevel() {
        String boostMessage = "";
        Text boostText = FXGL.getUIFactoryService().newText(boostMessage, Color.LIGHTGRAY, 32);

        boostText.setTranslateX(1200);
        boostText.setTranslateY(50);
        FXGL.addUINode(boostText);
        return boostText;
    }

    public void displayLives() {
        updateLivesSprite();
    }

    public static void updateLivesSprite() {
        if (currentLivesHUD != null) {
            currentLivesHUD.removeFromWorld();
        }

        int lives = PlayerComponent.getLives();
        int clamped = Math.max(0, Math.min(9, lives));
        String spawnName = String.format("lives_%02d", clamped);

        currentLivesHUD = FXGL.spawn(spawnName, new Point2D(20, 20));
    }

    public static void flashLivesHUD() {
        if (currentLivesHUD == null || flashing)
            return;

        flashing = true;

        var view = currentLivesHUD.getViewComponent();

        var flashTask = FXGL.getGameTimer().runAtInterval(() -> {
            double opacity = view.getOpacity() == 1.0 ? 0.3 : 1.0;
            view.setOpacity(opacity);
        }, Duration.seconds(0.1));

        FXGL.getGameTimer().runOnceAfter(() -> {
            flashTask.expire();
            view.setOpacity(1.0);
            flashing = false;
        }, Duration.seconds(0.5));
    }

    public ProgressBar displayScore() {
        ProgressBar scoreProgress = new ProgressBar();

        scoreProgress.setMinValue(0);
        scoreProgress.setMaxValue(BossLevelManager.getScoreToSpawn());

        scoreProgress.setWidth(240);
        scoreProgress.setHeight(10);

        scoreProgress.setTranslateX(50);
        scoreProgress.setTranslateY(130);

        FXGL.addUINode(scoreProgress);
        return scoreProgress;
    }

    public ProgressBar displayBulletCount() {
        // ProgressBar(false) with the boolean parameter to remove the add or subtract
        // messages
        ProgressBar bulletProgress = new ProgressBar(false);

        bulletProgress.setMinValue(0);
        bulletProgress.setMaxValue(PlayerComponent.getMaxBulletCount());

        bulletProgress.setTranslateX(230);
        bulletProgress.setTranslateY(600);
        bulletProgress.setVisible(false);

        FXGL.addUINode(bulletProgress);
        return bulletProgress;
    }

}
