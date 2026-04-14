package org.group1.GamePackage.UI;

import org.group1.GamePackage.Components.Player.PlayerComponent;
import org.group1.GamePackage.Components.UI.TimerComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.beans.binding.Bindings;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class HUDInterface {
    public Text displayTimer(Entity time) {
        Text timerText = FXGL.getUIFactoryService().newText("", Color.AQUAMARINE, 24);

        // bind the text to the property so it auto-updates
        timerText.textProperty().bind(
                Bindings.createStringBinding(() -> {
                    // takes the time from TimerComponent class.
                    int total = time.getComponent(TimerComponent.class).timeLeftProperty().get();

                    // Turns the total time (in seconds) into a proper minute format
                    int minutes = total / 60;
                    int seconds = total % 60;

                    // returns the final string into m:ss format
                    return String.format("Time Left: %2d:%02d", minutes, seconds);
                }, time.getComponent(TimerComponent.class).timeLeftProperty()));

        timerText.setTranslateX(50);
        timerText.setTranslateY(50);
        FXGL.addUINode(timerText);
        return timerText;
    }

    public Text displayBoostLevel() {
        String boostMessage = "Boost: " + PlayerComponent.getBoostLevel();
        Text boostText = FXGL.getUIFactoryService().newText(boostMessage, Color.AQUAMARINE, 24);
        boostText.setTranslateX(50);
        boostText.setTranslateY(80);
        FXGL.addUINode(boostText);
        return boostText;
    }

    public Text displayLives() {
        String livesMessage = "Lives: " + PlayerComponent.getLives();
        Text livesText = FXGL.getUIFactoryService().newText(livesMessage, Color.AQUAMARINE, 24);
        livesText.setTranslateX(50);
        livesText.setTranslateY(110);
        FXGL.addUINode(livesText);
        return livesText;
    }

    public Text displayScore() {
        String scoreMessage = "Score: " + PlayerComponent.getScore();
        Text scoreText = FXGL.getUIFactoryService().newText(scoreMessage, Color.AQUAMARINE, 24);

        scoreText.setTranslateX(50);
        scoreText.setTranslateY(140);

        FXGL.addUINode(scoreText);
        return scoreText;
    }

    public Text displayBulletCount() {
        String scoreMessage = "";
        Text bulletText = FXGL.getUIFactoryService().newText(scoreMessage, Color.AQUAMARINE, 24);

        bulletText.setTranslateX(230);
        bulletText.setTranslateY(600);

        FXGL.addUINode(bulletText);
        return bulletText;
    }

}
