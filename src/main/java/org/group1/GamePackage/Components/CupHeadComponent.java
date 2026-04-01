package org.group1.GamePackage.Components;

import org.group1.GamePackage.Music.AudioManager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

import javafx.util.Duration;


public class CupHeadComponent extends Component {

    GameOverComponent gameOverComponent = new GameOverComponent();
    AudioManager audioManager = new AudioManager();

    private static final double INVINCIBLE_DURATION = 1.5; // seconds
    private static final double FLASH_INTERVAL = 0.1; // seconds between flashes

    private int lives = 9;
    private int boostLevel = 0;

    // static so it belongs to the class itself
    private static int score = 0;

    private boolean isInvincible = false;
    private boolean visible = true;

    public void takeDamage () {
        if (!isInvincible && lives > 0) {
            audioManager.FAH();
            lives -= 1;
        } 

        triggerInvincibility();
    }

    // Just Die
    public void die() {
            gameOverComponent.gameOver();
    }

    public void increaseLives() {
        if (lives <9) {
            lives += 1;
        }
    }

    public void addScore() {
        score++;
    }

    /**
     * Triggers the player's invincibility. Making the player flash and invincible for 1.5 seconds
     */
    private void triggerInvincibility() {
        isInvincible = true;

        var flashTask = FXGL.getGameTimer().runAtInterval(() -> {
            visible = !visible;
            entity.getViewComponent().setOpacity(visible ? 1.0 : 0.3);
        }, Duration.seconds(FLASH_INTERVAL));

        FXGL.getGameTimer().runOnceAfter(() -> {
            flashTask.expire();
            isInvincible = false;
            entity.getViewComponent().setOpacity(1.0);
        }, Duration.seconds(INVINCIBLE_DURATION));
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public void decreaseBoostLevel (double amount) {
        boostLevel -= amount;
    }

    public void setBoostLevel (int amount) {
        boostLevel = amount;
    }

    public int getLives() {
        return lives;
    }

    // static as well so that getScore keeps updating in this class
    public static int getScore() {
        return score;
    }

    public int getBoostLevel() {
        return boostLevel;
    }

}
