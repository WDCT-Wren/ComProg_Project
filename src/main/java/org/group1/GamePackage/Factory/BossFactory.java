package org.group1.GamePackage.Factory;

import com.almasb.fxgl.dsl.components.ProjectileComponent;
import javafx.geometry.Point2D;
import javafx.scene.shape.Rectangle;
import org.group1.GamePackage.Components.Enemy.BossComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;


public class BossFactory implements EntityFactory {

    public enum BossType {
        BOSS,
        MINI_BOSS,
        BOSS_LASER
    }

    private int BOSS_HITBOX = 400;
    private int MINI_BOSS_HITBOX = 80;
    private final double BULLET_HITBOX = 100;

    @Spawns("boss")

    public Entity newBoss(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(BossType.BOSS)
            .viewWithBBox(FXGL.texture("boss_placeholder.png", BOSS_HITBOX, BOSS_HITBOX))
            .with(new BossComponent())
            .with(new CollidableComponent(true))
            .build();
    }

    @Spawns("mini_boss")

    public Entity newMiniBoss(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(BossType.MINI_BOSS)
            .viewWithBBox(FXGL.texture("boss_placeholder.png", MINI_BOSS_HITBOX, MINI_BOSS_HITBOX))
            .with(new BossComponent())
            .with(new CollidableComponent(true))
            .build();
    }

    @Spawns("lasers")

    public Entity newLasers(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(BossType.BOSS_LASER)
                .viewWithBBox(new Rectangle(70, 40))
                .with(new ProjectileComponent(new Point2D(-1,0), 500))
                .with(new CollidableComponent(true))
                .build();
    }
}

