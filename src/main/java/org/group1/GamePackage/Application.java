package org.group1.GamePackage;

import org.group1.GamePackage.Components.AnimationComponent;
import org.group1.GamePackage.Components.CupHeadComponent;
import org.group1.GamePackage.Components.EnemyAnimationComponent;
import org.group1.GamePackage.EntityFactory.SimpleFactory;
import org.group1.GamePackage.EntityFactory.SimpleFactory.EntityType;
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

//GameApplication is used to start the game instead of
//JavaFX's native Application class
public class Application extends GameApplication {

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
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                    EntityType.BULLET,
                    EntityType.ENEMY)
                {
                    @Override
                    protected void onCollisionBegin(Entity bullet, Entity enemy){
                        bullet.removeFromWorld();
                        enemy.getComponent(EnemyAnimationComponent.class).explode();
                    }
                });
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                    EntityType.ENEMY,
                    EntityType.PLAYER) 
                {
                    @Override
                    protected void onCollisionBegin(Entity enemy, Entity player) {
                        var playerComponent = player.getComponent(CupHeadComponent.class);
                        playerComponent.takeDamage(10);
                        playerComponent.decreaseLives();

                        enemy.getComponent(EnemyAnimationComponent.class).explode();
                    }  
                });
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new SimpleFactory());
        // Create a controllable player entity
        player = FXGL.spawn("cupheadPlane", 100, 200);
    
        normalEnemy = FXGL.getGameTimer().runAtInterval(() -> {
            // Generate random position within screen bounds
            double y = FXGLMath.random(0, FXGL.getAppHeight());

            // Spawn the entity (defined in your EntityFactory)
            FXGL.spawn("enemy", 1000, y);

            enemyPresent = true;
        }, Duration.seconds(3.0)); // Spawn every 2 seconds

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
