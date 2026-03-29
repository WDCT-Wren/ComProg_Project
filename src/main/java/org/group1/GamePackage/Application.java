package org.group1.GamePackage;

import com.almasb.fxgl.physics.HitBox;
import org.group1.GamePackage.Components.AnimationComponent;
import org.group1.GamePackage.Components.CupHeadComponent;
import org.group1.GamePackage.Components.EnemyAnimationComponent;
import org.group1.GamePackage.Factory.BackgroundFactory;
import org.group1.GamePackage.Factory.EntityFactory;
import org.group1.GamePackage.Factory.EntityFactory.EntityType;
import org.group1.GamePackage.Music.AudioManager;
import org.group1.GamePackage.UI.HUDInterface;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.time.TimerAction;

import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.Random;

public class Application extends GameApplication {

    // Instantiate AudioManager
    AudioManager audioManager = new AudioManager();

    // Game Duration for timer
    private static final double GAME_DURATION = 2.0;

    // Bullet Cooldown
    private long firstBullet = 0;
    private final long BULLET_COOLDOWN = 300;

    // Boolean To Keep the up and Down Animation
    private boolean movingUp = false;
    private boolean movingDown = false;

    // Variable to hold our controllable entity
    private Entity player;

    // Variable for enemies
    private TimerAction normalEnemy;
    private boolean enemyPresent = false;

    // RNG for power up generation
    private static final Random random = new Random();
    private static final double POWERUP_SPAWN_CHANCE = 0.30;

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

        // Bullet hits enemy
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                EntityType.BULLET,
                EntityType.ENEMY)
            {

                @Override
                protected void onCollisionBegin(Entity bullet, Entity enemy){
                    bullet.removeFromWorld();
                    audioManager.playDeathSound();
                    enemy.getComponent(EnemyAnimationComponent.class).explode();

                    // Random generation of extra life
                    if (random.nextDouble() < POWERUP_SPAWN_CHANCE) {
                        FXGL.spawn("extraLife", enemy.getPosition());
                    }
                }
            });

        // Enemy hits player
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                EntityType.ENEMY,
                EntityType.PLAYER)
            {
                @Override
                protected void onCollisionBegin(Entity enemy, Entity player) {
                    var playerComponent = player.getComponent(CupHeadComponent.class);
                    audioManager.FAH();
                    audioManager.playDeathSound();
                    playerComponent.takeDamage();

                    enemy.getComponent(EnemyAnimationComponent.class).explode();
                }
            });

        // Player gets power ups
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                EntityType.POWER_UP,
                EntityType.PLAYER)
            {
                @Override
                protected void onCollisionBegin(Entity powerUp, Entity player) {
                    var playerComponent = player.getComponent(CupHeadComponent.class);
                    audioManager.FAH();
                    playerComponent.increaseLives();

                    powerUp.removeFromWorld();
            }
        });
    }

    @Override
    protected void initGame() {
        //Factory Initialization
        FXGL.getGameWorld().addEntityFactory(new EntityFactory());
        FXGL.getGameWorld().addEntityFactory(new BackgroundFactory());

        //Background Initialization
        LevelManager levelManager = new LevelManager();
        levelManager.initBackground();

        // Create a controllable player entity
        player = FXGL.spawn("player", 100, 200);
    
        normalEnemy = FXGL.getGameTimer().runAtInterval(() -> {
            // Generate random position within 2 /3 of screen bounds
            double y = FXGLMath.random(0, FXGL.getAppHeight() * 2 / 3 );

            // Spawn the entity (defined in your EntityFactory)
            FXGL.spawn("enemy", 1000, y);

            enemyPresent = true;
        }, Duration.seconds(2.0)); // Spawn every 2 seconds

    }

    // Method to handle input/key listeners
    @Override
    protected void initInput() {
        FXGL.getAudioPlayer();

        // Shooting mechanic
        FXGL.getInput().addAction(
            new UserAction("Shoot") {
                @Override
                protected void onAction() {
                    // Delay curretTimeMillis
                    long currentTime = System.currentTimeMillis();
                    if (currentTime - firstBullet >= BULLET_COOLDOWN){
                        FXGL.spawn("bullet", player.getX()+120, player.getY()+50);
                        firstBullet = currentTime;
                    }
                }
            },
            KeyCode.SPACE
        );

        // Move up
        FXGL.getInput().addAction(
            new UserAction("Move up") {
                
                @Override
                protected void onActionBegin() {
                    movingUp = true;
                }

                @Override
                protected void onActionEnd() {
                    movingUp = false;
                }
            },
            KeyCode.W
        );
        
        // Move down
        FXGL.getInput().addAction(
            new UserAction("Move down") {

                @Override
                protected void onActionBegin() {
                    movingDown = true;
                }

                @Override
                protected void onActionEnd() {
                    movingDown = false;
                }
            },
            KeyCode.S
        );
        
        // Move left
        FXGL.getInput().addAction(
           new UserAction("Move left") {
                @Override
                protected void onAction() {
                    player.translateX(-5);
                }
            },
            KeyCode.A
        );
        
        // Move right
        FXGL.getInput().addAction(
            new UserAction("Move right") {
                @Override
                protected void onAction() {
                    player.translateX(5);
                }
            },
            KeyCode.D
        );
    }

    @Override
    protected void onUpdate(double update) {
        if (movingUp && movingDown) {
            player.getComponent(AnimationComponent.class).onIdle();
        } else if (movingUp) {
            player.translateY(-5);
            player.getComponent(AnimationComponent.class).onUp();
        } else if (movingDown) {
            player.translateY(5);
            player.getComponent(AnimationComponent.class).onDown();
        } else {
            player.getComponent(AnimationComponent.class).onIdle();
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
    }

    public static void main(String[] args) {
        launch(args);
    }
}
