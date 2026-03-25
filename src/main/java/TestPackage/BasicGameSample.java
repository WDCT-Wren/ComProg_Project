package TestPackage;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;

//GameApplication is used to start the game instead of
//JavaFX's native Application class
public class BasicGameSample extends GameApplication {

    @Override
    protected void initSettings(GameSettings gameSettings) {

    }

    @Override
    //Overriden to set up the factory and
    //spawn the entities in the specific x and y coordinates in pixels.
    protected void initGame() {
        //Used to initialize the world and start to add entities
        FXGL.getGameWorld().addEntityFactory(new SimpleFactory());

        // add entities

        // IMPORTANT: entityName String must be the same as in the Spawn annotation.
        FXGL.spawn("enemy", 100, 100); //buildAndAttatch() method is not necessary if this is called
        FXGL.spawn("movingEntity", 200, 200);
    }

    public static void main(String[] args) {
        launch(args);
    }
}