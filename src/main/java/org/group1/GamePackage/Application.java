package org.group1.GamePackage;

import org.group1.GamePackage.Components.AnimationComponent;
import org.group1.GamePackage.Components.CupHeadComponent;
import org.group1.GamePackage.Components.EnemyAnimationComponent;
import org.group1.GamePackage.EntityFactory.BackgroundFactory;
import org.group1.GamePackage.EntityFactory.SimpleFactory;
import org.group1.GamePackage.EntityFactory.SimpleFactory.EntityType;
import org.group1.GamePackage.Handlers.CollisionManager;
import org.group1.GamePackage.Handlers.InputManager;
import org.group1.GamePackage.Music.AudioManager;
import org.group1.GamePackage.UI.HUDInterface;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.TimerAction;

import javafx.scene.text.Text;
import javafx.util.Duration;

//GameApplication is used to start the game instead of
//JavaFX's native Application class
public class Application extends GameApplication {

    // Instantiate AudioManager
    AudioManager audioManager = new AudioManager();

    // Bullet Cooldown
    private long firstBullet = 0;
    private final long BULLET_COOLDOWN = 300;

    // Variable to hold our controllable entity
    private Entity player;

    // Input manager
    private InputManager inputManager;

    // Variable for enemies
    private TimerAction normalEnemy;
    private boolean enemyPresent = false;

    /*
    Initializes HUD object
    Variables for HUD texts
     */
    HUDInterface HUD = new HUDInterface();
    private Text hpText;
    private Text boostText;
    private Text livesText; 

    @Override
    protected void initUI() {
        hpText = HUD.displayHealth(player, hpText);
        boostText = HUD.displayLives(player, livesText);
        livesText = HUD.displayBoostLevel(player, boostText);
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setHeight(720);
        gameSettings.setWidth(1280);
    }

    @Override
    protected void initPhysics() {
        audioManager.playBackgroundMusic();
        
        CollisionManager collisionManager = new CollisionManager();
        collisionManager.init();
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new SimpleFactory());

        // Background
        FXGL.getGameWorld().addEntityFactory(new BackgroundFactory());
        FXGL.spawn("background");
        FXGL.spawn("stars1");
        FXGL.spawn("stars2");
        FXGL.spawn("stars3");

        FXGL.spawn("cloud1");
        FXGL.spawn("cloud2", 0, 100);

        FXGL.spawn("mist1", 0, 500);
        FXGL.spawn("hills", 0 , 500);
        FXGL.spawn("forest", 0 , 550);
        FXGL.spawn("large_forest", 0 , 550);

        // Create a controllable player entity
        player = FXGL.spawn("cupheadPlane", 100, 200);
    
        normalEnemy = FXGL.getGameTimer().runAtInterval(() -> {
            // Generate random position within 2 /3 of screen bounds
            double y = FXGLMath.random(0, FXGL.getAppHeight() * 2 / 3 );

            // Spawn the entity (defined in your EntityFactory)
            FXGL.spawn("enemy", 1000, y);

            enemyPresent = true;
        }, Duration.seconds(3.0)); // Spawn every 2 seconds

    }

    // Method to handle input/key listeners
    @Override
    protected void initInput() {
        // Initialize InputManager and register inputs here
        FXGL.getAudioPlayer();
        inputManager = new InputManager();
        inputManager.registerInputs();
    }

    @Override
    protected void onUpdate(double update) {
        if (inputManager.isMovingUp() && inputManager.isMovingDown()) {
            player.getComponent(AnimationComponent.class).onIdle();
        } else if (inputManager.isMovingUp()) {
            player.translateY(-5);
            player.getComponent(AnimationComponent.class).onUp();
        } else if (inputManager.isMovingDown()) {
            player.translateY(5);
            player.getComponent(AnimationComponent.class).onDown();
        } else {
            player.getComponent(AnimationComponent.class).onIdle();
        }
        
        if (inputManager.isMovingLeft()) {
            player.translateX(-5);
        } else if (inputManager.isMovingRight()) {
            player.translateX(5);
        }

        /*
         Checks if enemy entities are present
         If true, entities explode when they reach the end of the screen
        */
        if (enemyPresent == true) {
            try {
                if (FXGL.getGameWorld().getSingleton(EntityType.ENEMY).getPosition().getX() == 0) {
                var enemyInstance = FXGL.getGameWorld().getSingleton(EntityType.ENEMY);
                enemyInstance.getComponent(EnemyAnimationComponent.class).explode();
                }
            } catch (Exception NoSuchElementException) {
                enemyPresent = false;
            }
        }
        
        //Updates HUD texts if player attributes change
        var pc = player.getComponent(CupHeadComponent.class);
        hpText.setText("HP: " + pc.getHealth());
        livesText.setText("Lives: " + pc.getLives());
        boostText.setText("Boost: " + pc.getBoostLevel());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
