package org.group1.GamePackage.Components.Player;

import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.scene.image.Image;
import org.group1.GamePackage.Components.UI.GameOverComponent;
import org.group1.GamePackage.Handlers.InputManager;
import org.group1.GamePackage.Music.AudioManager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

import javafx.util.Duration;


public class PlayerComponent extends Component {

    GameOverComponent gameOverComponent = new GameOverComponent();
    AudioManager audioManager = new AudioManager();

    private static final double INVINCIBLE_DURATION = 1.5; // seconds
    private static final double FLASH_INTERVAL = 0.1; // seconds between flashes

    private InputManager inputManager;

    // Boost level decrease timer
    private double boostDecreaseTimer = 0;

    private int lives = 9;
    private int boostLevel = 0;
    private int playerSpeed = 5;

    private static int score = 0;

    private boolean isInvincible = false;
    private boolean visible = true;

    private AnimatedTexture texture;
    private AnimationChannel idleChannel;
    private AnimationChannel downChannel;
    private AnimationChannel upChannel;

    public PlayerComponent(InputManager inputManager) {
        this.inputManager = inputManager;
    }

    // Idle Animation Loop On Spawn
    @Override
    public void onAdded() {
        Image idle_sprite = FXGL.image("idle_sprite.png"); //idle_sprite image in resource folder
        idleChannel = new AnimationChannel(
                idle_sprite,
                1,
                112, //Width
                98, //Height
                Duration.seconds(0.1),
                0,
                3
        );

        Image down_sprite = FXGL.image("down_sprite.png"); //down_sprite image in resource folder
        downChannel = new AnimationChannel(
                down_sprite,
                1,
                110, // Width
                103, // Total Height divided by amount of frames (4)
                Duration.seconds(0.1),
                0,
                3
        );

        Image up_sprite = FXGL.image("up_sprite.png"); //image in resource folder
        upChannel = new AnimationChannel(
                up_sprite,
                1,
                114, // Width
                92, // Total Height divided by amount of frames (4)
                Duration.seconds(0.1),
                0,
                3
        );

        texture = new AnimatedTexture(idleChannel);
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(idleChannel);
    }
    
    @Override
    public void onUpdate(double tpf) {

        //Handles up and down input
        if (inputManager.isMovingUp() && inputManager.isMovingDown()) {
            onIdle();
        } else if (inputManager.isMovingUp()) {
            entity.translateY(-playerSpeed);
            onUp();
        } else if (inputManager.isMovingDown()) {
            entity.translateY(playerSpeed);
            onDown();
        } else {
            onIdle();
        }

        //handles A and D key
        if (inputManager.isMovingLeft()) {
            entity.translateX(-playerSpeed);
        } else if (inputManager.isMovingRight()) {
            entity.translateX(playerSpeed);
        }

        /*
        - Checks if boost level is above zero
        - Decreases boost level by 1 every 1.5 seconds
        - Implemented boostDecreastTimer since onUpdate updates every frame
        - 1s / 60frames = 0.16 seconds per frame
         */
        if (boostLevel > 0) {
            playerSpeed *= 2;
            boostDecreaseTimer += 0.016; // ~60 FPS
            if (boostDecreaseTimer >= 0.5) {
                --boostLevel;
                boostDecreaseTimer = 0;
            }
        } else {
            boostDecreaseTimer = 0; // Reset if boost hits zero
        }
    }

    // Down Animation Loop
    public void onDown() {
        if (texture.getAnimationChannel() != downChannel) {
            texture.loopAnimationChannel(downChannel);
        }
    }

    // Up Animation Loop
    public void onUp() {
        if (texture.getAnimationChannel() != upChannel) {
            texture.loopAnimationChannel(upChannel);
        }
    }

    // Idle Animation Loop
    public void onIdle() {
        if (texture.getAnimationChannel() != idleChannel) {
            texture.loopAnimationChannel(idleChannel);
        }
    }


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

    public int getLives() {
        return lives;
    }

    public boolean isInvincible() {return isInvincible;}

    // static as well so that getScore keeps updating in this class
    public static int getScore() {
        return score;
    }

    public int getBoostLevel() {
        return boostLevel;
    }

    public void setBoostLevel (int amount) {
        boostLevel = amount;
    }
}
