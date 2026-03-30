package org.group1.GamePackage.Factory;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.group1.GamePackage.Components.AnimationComponent;
import org.group1.GamePackage.Components.BulletAnimationComponent;
import org.group1.GamePackage.Components.CupHeadComponent;
import org.group1.GamePackage.Components.EnemyAnimationComponent;
import org.group1.GamePackage.Components.EnemyDropsAnimationComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.KeepOnScreenComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;

import javafx.geometry.Point2D;

public class EntityFactory implements com.almasb.fxgl.entity.EntityFactory {

    public enum EntityType {
        PLAYER, 
        ENEMY, 
        BULLET,
        POWER_UP
    }

    private double BULLET_HITBOX = 25;
    private double LIFE_HITBOX = 40;

    @Spawns("bullet")

    public Entity newBullet(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.BULLET)
                .bbox(new HitBox(BoundingShape.box(BULLET_HITBOX, BULLET_HITBOX)))
                .with(new BulletAnimationComponent())
                .with(new ProjectileComponent(new Point2D(1,0), 1000))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("player")

    public Entity newPlayer(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(EntityType.PLAYER)
                .bbox(new HitBox(BoundingShape.box(120, 100)))
                .with(new CupHeadComponent())
                .with(new AnimationComponent())
                .with(new KeepOnScreenComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("enemy")

    public Entity enemy(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(EntityType.ENEMY)
            .bbox(new HitBox(BoundingShape.box(100, 100)))
            .with(new ProjectileComponent(new Point2D(-1,0), 500))
            .with(new EnemyAnimationComponent())
            .with(new CollidableComponent(true))
            .with(new KeepOnScreenComponent())
            .build();

    }

    @Spawns("extraLife")

    public Entity newPowerUp(SpawnData data) {

        return FXGL.entityBuilder(data)
            .type(EntityType.POWER_UP)
            .with(new EnemyDropsAnimationComponent())
            .bbox(new HitBox(BoundingShape.box(LIFE_HITBOX, LIFE_HITBOX)))
            .with(new ProjectileComponent(new Point2D(-1,0), 300))
            .with(new CollidableComponent(true))
            .build();
    }

    public double getLifeHitbox() {
        return LIFE_HITBOX;
    }
}
