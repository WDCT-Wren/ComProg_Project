package TestPackage;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;

import javafx.scene.input.KeyCode;

//GameApplication is used to start the game instead of
//JavaFX's native Application class
public class BasicGameSample extends GameApplication {

    // Variable to hold our controllable entity
    private Entity player;

    @Override
    protected void initSettings(GameSettings gameSettings) {
        gameSettings.setHeight(720);
        gameSettings.setWidth(1280);
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
                        //Offset to match sprite position
                        FXGL.spawn("bullet", player.getX()+120, player.getY()+50);
                    }
                },
                KeyCode.Q
        );

        // Move up
        FXGL.getInput().addAction(
            new UserAction("Move up") {
                @Override
                protected void onAction() {
                    player.translateY(-5);
                    player.getComponent(AnimationComponent.class).onUp();
                }

                @Override
                protected void onActionEnd() {
                    player.getComponent(AnimationComponent.class).onIdle();
                }
            },
            KeyCode.W
        );
        
        // Move down
        FXGL.getInput().addAction(
            new UserAction("Move down") {
                @Override
                protected void onAction() {
                    player.translateY(5);
                    player.getComponent(AnimationComponent.class).onDown();
                }

                @Override
                protected void onActionEnd() {
                    player.getComponent(AnimationComponent.class).onIdle();
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

    public static void main(String[] args) {
        launch(args);
    }
}
