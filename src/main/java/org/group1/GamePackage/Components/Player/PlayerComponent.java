package org.group1.GamePackage.Components.Player;

import org.group1.GamePackage.Components.UI.GameOverComponent;
import org.group1.GamePackage.Handlers.GameMechanics;
import org.group1.GamePackage.Handlers.InputManager;
import org.group1.GamePackage.Music.AudioManager;
import org.group1.GamePackage.UI.Interfaces.HUDInterface;

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
    private static int MAX_BULLET_COUNT = 60;
    private int playerSpeed = 5;

    private static int score = 0;

    private boolean isInvincible = false;
    private boolean visible = true;

    private AnimatedTexture texture;
    private AnimationChannel idleChannel;

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
                8,
                120, //Width
                120, //Height
                Duration.seconds(1.3),
                0,
                7
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
            onIdle();
        } else if (inputManager.isMovingDown()) {
            entity.translateY(playerSpeed);
            onIdle();
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

        checkBoundaries();
        // Checks if boost is active and applies it if it is
        checkBoost(timePerFrame);
    }

    private void checkBoundaries() {
        double screenWidth = FXGL.getAppWidth();
        double screenHeight = FXGL.getAppHeight();
        
        // Account for scale factor and offset
        double scale = entity.getScaleX();
        double hitboxWidth = entity.getWidth();
        double hitboxHeight = entity.getHeight();
        double offsetX = 10.0;  // From EntityFactory hitbox offset
        double offsetY = 20.0;  // From EntityFactory hitbox offset
        
        double playerWidth = (hitboxWidth + offsetX) * scale;
        double playerHeight = (hitboxHeight + offsetY) * scale;
        double x = entity.getX();
        double y = entity.getY();

        // Clamp position to screen bounds
        if (x < 0) {
            entity.setX(0);
        } 
        if ((x + playerWidth) >= screenWidth) {
            entity.setX(screenWidth - playerWidth);
        }

        if (y < 0) {
            entity.setY(0);
        } 
        if ((y + playerHeight) >= screenHeight) {
            entity.setY(screenHeight - playerHeight);
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
            AudioManager.FAH();
            lives -= 1;
            boostLevel = 0;

            HUDInterface.updateLivesSprite();
            HUDInterface.flashLivesHUD();
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

    // added a parameter for multiple points 
    public static void addScore(int value) {
        score += value;
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

    public static int getMaxBulletCount() {
        return MAX_BULLET_COUNT;
    }

    // setters
    public void setBoostLevel (int amount) {
        boostLevel = amount;
    }

    public void setScore (int amount) {
        score = amount;
    }

    public void toggleFireBullet(boolean trigger) {
        hasFireBullets = trigger;
    }

    public void toggleIceBullet(boolean trigger) {
        hasIceBullets = trigger;
    }

    public static void fireBulletAdd(int amt) {
        fireBulletsCount += amt;
        fireBulletsCount = (fireBulletsCount > MAX_BULLET_COUNT) ? MAX_BULLET_COUNT: fireBulletsCount;
        fireBulletsCount = (fireBulletsCount <= 0) ? 0 : fireBulletsCount;

        if (fireBulletsCount == 0) {
            hasFireBullets = false;
            GameMechanics.setDefaultBullet();
            InputManager.replaceHud("default_hud");
        }

        
    }

    public static void iceBulletAdd(int amt) {
        iceBulletsCount += amt;
        iceBulletsCount = (iceBulletsCount > MAX_BULLET_COUNT) ? MAX_BULLET_COUNT : iceBulletsCount;
        iceBulletsCount = (iceBulletsCount <= 0) ? 0 : iceBulletsCount;

        if (iceBulletsCount == 0) {
            hasIceBullets = false;
            GameMechanics.setDefaultBullet();
            InputManager.replaceHud("default_hud");
        }
    }


    // reset static fields coz FXGL doesnt reset those
    public static void reset() {
        lives = 9;
        score = 0;
        boostLevel = 0;
        fireBulletsCount = 0;
        iceBulletsCount = 0;
        hasFireBullets = false;
        hasIceBullets = false;
    }
}
