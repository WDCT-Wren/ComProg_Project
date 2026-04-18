package org.group1.GamePackage;

import org.group1.GamePackage.Components.Player.PlayerComponent;
import org.group1.GamePackage.Components.UI.TimerComponent;
import org.group1.GamePackage.Factory.BackgroundFactory;
import org.group1.GamePackage.Factory.BossFactory;
import org.group1.GamePackage.Factory.EntityFactory;
import org.group1.GamePackage.Factory.InterfaceFactory;
import org.group1.GamePackage.Factory.MainSceneFactory;
import org.group1.GamePackage.Handlers.BossLevelManager;
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
import com.almasb.fxgl.ui.ProgressBar;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

//GameApplication is used to start the game instead of
//JavaFX's native Application class
public class Application extends GameApplication {

    private TimerComponent timerComponent;

    private boolean bossLevel = false;
    private boolean bossSpawned = false;

    // Input manager
    public static InputManager inputManager;

    public static PlayerComponent playerMainComponent;

    // Instantiate AudioManager
    AudioManager audioManager = new AudioManager();

    // Variable to hold our controllable entity
    private Entity player;
    private final Point2D playerSpawnPoint = new Point2D(100, 200);

    // Variables
    private static final double NORMAL_ENEMY_SPAWN_RATE = 0.7; // every seconds
    private static final double NORMAL_ENEMY_SPAWN_DISTANCE = 1000; // Spawns at 1000 in the x-axis
    private static final double MINI_BOSS_SPAWN_DISTANCE = 1000;
    private static final double MINI_BOSS_SPAWN_RATE = 10;

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
    private ProgressBar bulletProgress;
    private ProgressBar scoreProgress;

    @Override
    protected void initUI() {
        boostText = HUD.displayLives();
        livesText = HUD.displayBoostLevel();
        bulletProgress = HUD.displayBulletCount();
        scoreProgress = HUD.displayScore();
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

    // Method to handle input/key listeners
    @Override
    protected void initInput() {
        // Initialize InputManager and register inputs here
        FXGL.getAudioPlayer();
        inputManager = new InputManager();
        inputManager.registerInputs();
    }

    /**
     * Initializes all existing entityFactories.
     * <br><br>
     * I just simply did this to reduce line coverage of {@code initGame()}
     * and to have a centralized method just in case more entity factories
     * are implemented.
     */
    protected void initFactory() {
        FXGL.getGameWorld().addEntityFactory(new EntityFactory());
        FXGL.getGameWorld().addEntityFactory(new BackgroundFactory());
        FXGL.getGameWorld().addEntityFactory(new BossFactory());
        FXGL.getGameWorld().addEntityFactory(new InterfaceFactory());
    }

    /**
     * Spawns enemies based on the {@code NORMAL_ENEMY_SPAWN_RATE}
     * where it spawns an enemy within the bounds of the 2/3 of the screen.
     */
    protected void spawnEnemies() {
        TimerAction normalEnemy = FXGL.getGameTimer().runAtInterval(() -> {
            // Don't spawn at bossLevel
            if (BossLevelManager.getBossLevel()) return;
            // Generate random position within 2 /3 of screen bounds
            double enemyBounds = (double) (FXGL.getAppHeight() * 2) / 3;
            double y = FXGLMath.random(0, enemyBounds);

            // Spawn the entity (defined in your EntityFactory)
            FXGL.spawn("enemy", NORMAL_ENEMY_SPAWN_DISTANCE, y);

            enemyPresent = true;
        }, Duration.seconds(NORMAL_ENEMY_SPAWN_RATE));

        TimerAction miniBoss = FXGL.getGameTimer().runAtInterval(() -> {
            if (BossLevelManager.getBossLevel()) return;
            double enemyBounds = (double) (FXGL.getAppHeight() * 2) / 3;
            double y = FXGLMath.random(0, enemyBounds);
            FXGL.spawn("mini_boss", MINI_BOSS_SPAWN_DISTANCE, y);
        }, Duration.seconds(MINI_BOSS_SPAWN_RATE)); 

}

    @Override
    protected void initGame() {
        initFactory();

        // Initalize Level Handler
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
        // Player Component
        playerMainComponent = player.getComponent(PlayerComponent.class);

        // Spawns the default switching hud 
        FXGL.spawn("default_hud");

        FXGL.entityBuilder()
            .with(new BossLevelManager(playerMainComponent, timerComponent, audioManager))
            .buildAndAttach();
        spawnEnemies();
    }

    @Override
    protected void onUpdate(double update) {
       
        // Display static getters in PlayerComponent
        
        livesText.setText("Lives: " + PlayerComponent.getLives());
        boostText.setText("Boost: " + PlayerComponent.getBoostLevel());
        scoreProgress.currentValueProperty().setValue(PlayerComponent.getScore());
        
        switch (GameMechanics.getCurrentBulletType()) {
            case "fire_bullet" -> {
                bulletProgress.setVisible(true);
                bulletProgress.setFill(Color.ORANGE);
                bulletProgress.currentValueProperty().setValue(PlayerComponent.getFireBulletsCount());
            }
            case "ice_bullet" -> {
                bulletProgress.setVisible(true);
                bulletProgress.setFill(Color.LIGHTBLUE);
                bulletProgress.currentValueProperty().setValue(PlayerComponent.getIceBulletsCount());
            }
            default -> {
                bulletProgress.setVisible(false);
            }
        }        
    }

    public static void main(String[] args) {
        launch(args);
    }
}
