package org.group1.GamePackage.Handlers;

import com.almasb.fxgl.dsl.FXGL;

public class LevelManager {
    /**
     * Uses {@link org.group1.GamePackage.Factory.BackgroundFactory}'s background entities,
     * sets it up with its corresponding coordinates, and spawns it, initializing the level.
     */
    public void initBackground() {
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
    }
}
