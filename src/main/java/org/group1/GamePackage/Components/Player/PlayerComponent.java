package org.group1.GamePackage.Components.Player;

import org.group1.GamePackage.Components.UI.GameOverComponent;
import org.group1.GamePackage.Handlers.GameMechanics;
import org.group1.GamePackage.Handlers.InputManager;
import org.group1.GamePackage.Music.AudioManager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.scene.image.Image;
import javafx.util.Duration;


public class PlayerComponent extends Component {

    GameOverComponent gameOverComponent = new GameOverComponent();
    AudioManager audioManager = new AudioManager();
    GameMechanics gameMechanics = new GameMechanics();

    private static final double INVINCIBLE_DURATION = 1.5; // seconds
    private static final double FLASH_INTERVAL = 0.1; // seconds between flashes

    private InputManager inputManager;

    // Boost level decrease timer
    private double boostDecreaseTimer = 0;


    private static int lives = 9;
    private static int boostLevel = 0;
    private static int fireBulletsCount = 0;
    private static int iceBulletsCount = 0;
    private int playerSpeed = 5;

    private static int score = 0;

    private boolean isInvincible = false;
    private boolean visible = true;

    private AnimatedTexture texture;
    private AnimationChannel idleChannel;
    private AnimationChannel downChannel;
    private AnimationChannel upChannel;

    private static boolean hasFireBullets = false;
    private static boolean hasIceBullets = false;

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
    public void onUpdate(double timePerFrame) {
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

        // Updates GameMechanics if the player is invincible
        if (isInvincible) {
            gameMechanics.setInvincible();
        }
        else {
            gameMechanics.offInvincible();
        }


        // Checks if boost is active and applies it if it is
        checkBoost(timePerFrame);
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

    /**
     * <ul> Checks if the player has boost and applies it if they do:
     *      <li>Sets player speed to {@code boostedSpeed}</li>
     *      <li>Sets bullet cooldown to 200</li>
     *      <li>Checks if {@code boostlevel} is above zero</li>
     *      <li>Decreases boost level by 1 every 1.5 seconds</li>
     *      <li>Implemented boostDecreastTimer since onUpdate updates every frame</li>
     *</ul>
     * @param timePerFrame Time elapsed since the last frame in seconds
     */
    private void checkBoost(double timePerFrame) {
        long BOOST_FIRE_RATE = 200;
        int boostedSpeed = 10;

        if (boostLevel > 0) {
            playerSpeed = boostedSpeed;
            GameMechanics.setBULLET_COOLDOWN(BOOST_FIRE_RATE);
            boostDecreaseTimer += timePerFrame;
            if (boostDecreaseTimer >= 1) {
                boostLevel--;
                boostDecreaseTimer = 0;
            }
        } else {
            // Reset boost level and fire rate
            GameMechanics.setBULLET_COOLDOWN(300);
            playerSpeed = 5;
            boostDecreaseTimer = 0;
        }
    }

    // getters
    public static int getLives() {
        return lives;
    }

    public static int getScore() {
        return score;
    }

    public static int getBoostLevel() {
        return boostLevel;
    }

    public boolean isInvincible() {
        return isInvincible;
    }

    public boolean getFireBulletsStatus() {
        return hasFireBullets;
    }

    public boolean getIceBulletsStatus() {
        return hasIceBullets;
    }

    public static int getFireBulletsCount() {
        return fireBulletsCount;
    }

    public static int getIceBulletsCount() {
        return iceBulletsCount;
    }

    // setters
    public void setBoostLevel (int amount) {
        boostLevel = amount;
    }

    public void toggleFireBullet(boolean trigger) {
        hasFireBullets = trigger;
    }

    public void toggleIceBullet(boolean trigger) {
        hasIceBullets = trigger;
    }

    public static void fireBulletAdd(int amt) {
        fireBulletsCount += amt;
        fireBulletsCount = (fireBulletsCount > 60) ? 60 : fireBulletsCount;
        fireBulletsCount = (fireBulletsCount <= 0) ? 0 : fireBulletsCount;

        if (fireBulletsCount == 0) {
            hasFireBullets = false;
            GameMechanics.setDefaultBullet();
            InputManager.replaceHud("default_hud");
        }

        
    }

    public static void iceBulletAdd(int amt) {
        iceBulletsCount += amt;
        iceBulletsCount = (iceBulletsCount > 60) ? 60 : iceBulletsCount;
        iceBulletsCount = (iceBulletsCount <= 0) ? 0 : iceBulletsCount;

        if (iceBulletsCount == 0) {
            hasIceBullets = false;
            GameMechanics.setDefaultBullet();
            InputManager.replaceHud("default_hud");
        }
    }
}
