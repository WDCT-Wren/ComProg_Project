package org.group1.GamePackage.EntityFactory;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.views.SelfScrollingBackgroundView;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

import javafx.geometry.Point2D;

public class BackgroundFactory implements EntityFactory {
    public enum EntityType {
        LARGE_TOWER
    }

    @Spawns("background")
    public Entity mainBackground(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view("background.png")
                .zIndex(-100)
                .build();
    }

    @Spawns("hills")
    public Entity hills(SpawnData data) {
        var background_hills = new SelfScrollingBackgroundView(
                FXGL.getAssetLoader().loadTexture("background_hills.png").getImage(),
                FXGL.getAppWidth(),
                FXGL.getAppHeight(),
                5
                );
        return FXGL.entityBuilder(data)
                .view(background_hills)
                .zIndex(-10)
                .build();
    }

    @Spawns("forest")
    public Entity forest(SpawnData data) {
        var background_forest = new SelfScrollingBackgroundView(
                FXGL.getAssetLoader().loadTexture("background_forest.png").getImage(),
                FXGL.getAppWidth(),
                FXGL.getAppHeight(),
                10
                );
        return FXGL.entityBuilder(data)
                .view(background_forest)
                .zIndex(10)
                .build();
    }

    @Spawns("large_forest")
    public Entity large_forest(SpawnData data) {
        var background_large_forest = new SelfScrollingBackgroundView(
                FXGL.getAssetLoader().loadTexture("background_large_forest.png").getImage(),
                FXGL.getAppWidth(),
                FXGL.getAppHeight(),
                10
                );
        return FXGL.entityBuilder(data)
                .view(background_large_forest)
                .zIndex(0)
                .build();
    }


    @Spawns("stars1")
    public Entity star1(SpawnData data) {
        var background_stars1 = new SelfScrollingBackgroundView(
                FXGL.getAssetLoader().loadTexture("background_stars1.png").getImage(),
                FXGL.getAppWidth(),
                FXGL.getAppHeight(),
                10
                );
        return FXGL.entityBuilder(data)
                .view(background_stars1)
                .zIndex(-90)
                .build();
    }

    @Spawns("stars2")
    public Entity star2(SpawnData data) {
        var background_stars2 = new SelfScrollingBackgroundView(
                FXGL.getAssetLoader().loadTexture("background_stars2.png").getImage(),
                FXGL.getAppWidth(),
                FXGL.getAppHeight(),
                15
                );
        return FXGL.entityBuilder(data)
                .view(background_stars2)
                .zIndex(-90)
                .build();
    }

    @Spawns("stars3")
    public Entity star3(SpawnData data) {
        var background_stars3 = new SelfScrollingBackgroundView(
                FXGL.getAssetLoader().loadTexture("background_stars3.png").getImage(),
                FXGL.getAppWidth(),
                FXGL.getAppHeight(),
                13
                );
        return FXGL.entityBuilder(data)
                .view(background_stars3)
                .zIndex(-90)
                .build();
    }

    @Spawns("cloud1")
    public Entity cloud1(SpawnData data) {
        var background_cloud1 = new SelfScrollingBackgroundView(
                FXGL.getAssetLoader().loadTexture("background_cloud1.png").getImage(),
                FXGL.getAppWidth(),
                FXGL.getAppHeight(),
                20
                );
        return FXGL.entityBuilder(data)
                .view(background_cloud1)
                .zIndex(-50)
                .build();
    }

    @Spawns("cloud2")
    public Entity cloud2(SpawnData data) {
        var background_cloud2 = new SelfScrollingBackgroundView(
                FXGL.getAssetLoader().loadTexture("background_cloud2.png").getImage(),
                FXGL.getAppWidth(),
                FXGL.getAppHeight(),
                30
                );
        return FXGL.entityBuilder(data)
                .view(background_cloud2)
                .zIndex(-50)
                .build();
    }

    @Spawns("mist1")
    public Entity mist1(SpawnData data) {
        var background_mist1 = new SelfScrollingBackgroundView(
                FXGL.getAssetLoader().loadTexture("background_mist1.png").getImage(),
                FXGL.getAppWidth(),
                FXGL.getAppHeight(),
                30
                );
        return FXGL.entityBuilder(data)
                .view(background_mist1)
                .zIndex(-20)
                .build();
    }

}
