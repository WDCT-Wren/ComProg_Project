package org.group1.GamePackage;

import org.group1.GamePackage.Components.AnimationComponent;
import org.group1.GamePackage.Components.CupHeadComponent;
import org.group1.GamePackage.Components.EnemyAnimationComponent;
import org.group1.GamePackage.Components.TimerComponent;
import org.group1.GamePackage.Factory.BackgroundFactory;
import org.group1.GamePackage.Factory.EntityFactory;
import org.group1.GamePackage.Factory.EntityFactory.EntityType;
import org.group1.GamePackage.Factory.MainSceneFactory;
import org.group1.GamePackage.Handlers.CollisionManager;
import org.group1.GamePackage.Handlers.GameMechanics;
import org.group1.GamePackage.Handlers.InputManager;
import org.group1.GamePackage.Handlers.LevelManager;
import org.group1.GamePackage.Music.AudioManager;
import org.group1.GamePackage.UI.HUDInterface;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.time.TimerAction;

import javafx.geometry.Point2D;
import javafx.scene.text.Text;
import javafx.util.Duration;

//GameApplication is used to start the game instead of
//JavaFX's native Application class
public class Application extends GameApplication {

    private TimerComponent timerComponent;

    // Instantiate CupHeadComponent
    CupHeadComponent cupHeadComponent = new CupHeadComponent();

    // Instantiate GameMechanics 
    GameMechanics gameMechanics = new GameMechanics();

    // Instantiate AudioManager
    AudioManager audioManager = new AudioManager();

    // Variable to hold our controllable entity
    private Entity player;
    private final Point2D playerSpawnPoint = new Point2D(100, 200);

    // Variables
    private static final double NORMAL_ENEMY_SPAWN_RATE = 0.5; // every seconds
    private static final double NORMAL_ENEMY_SPAWN_DISTANCE = 1000; // Spawns at 1000 in the x-axis


    // Input manager
    private InputManager inputManager;

    //Enemy presence manager
    private boolean enemyPresent = false;

    /*
    Initializes HUD object
    Variables for HUD texts
     */
    HUDInterface HUD = new HUDInterface();
    private Text boostText;
    private Text livesText; 
    private Text timerText;
    private Text scoreText;

    @Override
    protected void initUI() {
        boostText = HUD.displayLives(player);
        livesText = HUD.displayBoostLevel(player);
        scoreText = HUD.displayScore(player);
    }

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setHeight(720);
        gameSettings.setWidth(1280);
        gameSettings.setMainMenuEnabled(true);
        gameSettings.setGameMenuEnabled(true);
        gameSettings.setSceneFactory(new MainSceneFactory());
    }

    @Override
    protected void initPhysics() {
        audioManager.playBackgroundMusic();
        
        CollisionManager collisionManager = new CollisionManager();
        collisionManager.init();
    }

    @Override
    protected void initGame() {
        // Initialize Factories
        FXGL.getGameWorld().addEntityFactory(new EntityFactory());
        FXGL.getGameWorld().addEntityFactory(new BackgroundFactory());


        // Initalize Level Handler
        // Level Manager
        LevelManager levelManager = new LevelManager();
        levelManager.initBackground();

        // Builds the entity and attach the component
        Entity timerEntity = FXGL.entityBuilder()
                .with(new TimerComponent())
                .buildAndAttach();

        // Grabs the component from the entity and start it
        // Timer Component instantiation
        // Displays timer text
        timerComponent = timerEntity.getComponent(TimerComponent.class);
        timerComponent.initTimer();
        timerText = HUD.displayTimer(timerEntity);

        // Create a controllable player entity
        player = FXGL.spawn("player", playerSpawnPoint);
        
        // Spawn the entity (defined in your EntityFactory)
        // Variable for enemies
        TimerAction normalEnemy = FXGL.getGameTimer().runAtInterval(() -> {
            // Generate random position within 2 /3 of screen bounds
            double enemyBounds = (double) (FXGL.getAppHeight() * 2) / 3;
            double y = FXGLMath.random(0, enemyBounds);

            // Spawn the entity (defined in your EntityFactory)
            FXGL.spawn("enemy", NORMAL_ENEMY_SPAWN_DISTANCE, y);

            enemyPresent = true;
        }, Duration.seconds(NORMAL_ENEMY_SPAWN_RATE));

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
        int playerSpeed = 5;
        var playerMainComponent = player.getComponent(CupHeadComponent.class);
        var playerAnimationComponent = player.getComponent(AnimationComponent.class);
        if (inputManager.isMovingUp() && inputManager.isMovingDown()) {
            playerAnimationComponent.onIdle();
        } else if (inputManager.isMovingUp()) {
            player.translateY(-playerSpeed);
            playerAnimationComponent.onUp();
        } else if (inputManager.isMovingDown()) {
            player.translateY(playerSpeed);
            playerAnimationComponent.onDown();
        } else {
            playerAnimationComponent.onIdle();
        }
        
        if (inputManager.isMovingLeft()) {
            player.translateX(-playerSpeed);
        } else if (inputManager.isMovingRight()) {
            player.translateX(playerSpeed);
        }
        
        //TODO: Complete game over logic
        // Added some logic but its ragebait and honestly incomplete
        if (playerMainComponent.getLives() == 0 || 
                timerComponent.timeEnded()) {
            FXGL.spawn("death_overlay");
            FXGL.getGameTimer().runOnceAfter(() -> {
            audioManager.playGameOver();
            cupHeadComponent.die();
            }, Duration.seconds(3));
        }

        // Updates GameMechanics if the player is invincible
        if (playerMainComponent.isInvincible()) {
            gameMechanics.setInvincible();
        } 
        else if (!playerMainComponent.isInvincible()) {
            gameMechanics.offInvincible();
        } 

        if (playerMainComponent.getBoostLevel() > 0) {
            FXGL.getGameTimer().runAtInterval(() -> {
                playerMainComponent.decreaseBoostLevel(0.5);
            }, Duration.seconds(1.5));
        }
            
        /*
         Checks if enemy entities are present
         If true, entities explode when they reach the end of the screen
        */
        if (enemyPresent) {
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
        livesText.setText("Lives: " + pc.getLives());
        boostText.setText("Boost: " + pc.getBoostLevel());
        scoreText.setText("Score: " + pc.getScore());
        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
