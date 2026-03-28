package TestPackage;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;

import TestPackage.SimpleFactory.EntityType;
import javafx.scene.input.KeyCode;

//GameApplication is used to start the game instead of
//JavaFX's native Application class
public class BasicGameSample extends GameApplication {

    // Bullet Cooldown
    private long firstBullet = 0;
    private final long BULLET_COOLDOWN = 300;

    // Boolean To Keep the up and Down Animation
    private boolean movingUp = false;
    private boolean movingDown = false;

    // Variable to hold our controllable entity
    private Entity player;

    // Variable for enemies
    private Entity normalEnemy;

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
                        enemy.removeFromWorld();
                    }
                });
    }

    @Override
    //Overriden to set up the factory and
    //spawn the entities in the specific x and y coordinates in pixels.
    protected void initGame() {
        //Used to initialize the world and start to add entities
        FXGL.getGameWorld().addEntityFactory(new SimpleFactory());

        // add entities

        // IMPORTANT: entityName String must be the same as in the Spawn annotation
        
        // Create a player entity that we can control
        player = FXGL.spawn("cupheadPlane", 100, 200);
        normalEnemy = FXGL.spawn("enemy", 1000, 300);

    }

    //
    
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
    }

    public static void main(String[] args) {
        launch(args);
    }
}
