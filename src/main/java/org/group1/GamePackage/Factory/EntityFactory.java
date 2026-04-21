package org.group1.GamePackage.Factory;

import org.group1.GamePackage.Application;
import org.group1.GamePackage.Components.Enemy.EnemyAnimationComponent;
import org.group1.GamePackage.Components.Enemy.EnemyDropsAnimationComponent;
import org.group1.GamePackage.Components.Player.PlayerComponent;
import org.group1.GamePackage.Components.PowerUps.BoostUpComponent;
import org.group1.GamePackage.Components.PowerUps.FirePowerUpComponent;
import org.group1.GamePackage.Components.PowerUps.IcePowerUpComponent;
import org.group1.GamePackage.Components.Projectiles.BulletAnimationComponent;

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
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class EntityFactory implements com.almasb.fxgl.entity.EntityFactory {
    public enum EntityType {
        PLAYER, 
        ENEMY, 
        BULLET,
        FIRE_BULLET,
        ICE_BULLET,
        POWER_UP
    }

    private final double BULLET_HITBOX = 60;
    private final double LIFE_HITBOX = 60;

    @Spawns("bullet")

    public Entity newBullet(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.BULLET)
                .bbox(new HitBox(BoundingShape.box(BULLET_HITBOX, BULLET_HITBOX)))
                .with(new BulletAnimationComponent(EntityType.BULLET))
                .with(new ProjectileComponent(new Point2D(1,0), 1000))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("fire_bullet")

    public Entity fireBullet(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(EntityType.FIRE_BULLET)
            .bbox(new HitBox(BoundingShape.box(BULLET_HITBOX,BULLET_HITBOX)))
            .with(new BulletAnimationComponent(EntityType.FIRE_BULLET))
            .with(new ProjectileComponent(new Point2D(1,0), 1000))
            .with(new CollidableComponent(true))
            .build();
    }

    @Spawns("ice_bullet")

    public Entity iceBullet(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(EntityType.ICE_BULLET)
            .bbox(new HitBox(BoundingShape.box(BULLET_HITBOX,BULLET_HITBOX)))
            .with(new BulletAnimationComponent(EntityType.ICE_BULLET))
            .with(new ProjectileComponent(new Point2D(1,0), 1000))
            .with(new CollidableComponent(true))
            .build();
    }

    @Spawns("player")

    public Entity newPlayer(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(EntityType.PLAYER)
                .bbox(new HitBox(BoundingShape.box(100, 80)))
                .with(new PlayerComponent(Application.inputManager))
                .with(new KeepOnScreenComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("enemy")

    public Entity enemy(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(EntityType.ENEMY)
            .bbox(new HitBox(BoundingShape.box(80, 80)))
            .with(new ProjectileComponent(new Point2D(-1,0), 500))
            .with(new EnemyAnimationComponent())
            .with(new CollidableComponent(true))
            .with(new KeepOnScreenComponent())
            .scale(0.7, 0.7)
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

    @Spawns("boostUp")

    public Entity boostUpPowerUp(SpawnData data) {
        
        return FXGL.entityBuilder(data)
            .type(EntityType.POWER_UP)
            .viewWithBBox(new Circle(30, Color.AZURE))
            .with(new BoostUpComponent())
            .bbox(new HitBox(BoundingShape.box(LIFE_HITBOX, LIFE_HITBOX)))
            .with(new ProjectileComponent(new Point2D(-1,0), 300))
            .with(new CollidableComponent(true))
            .build();
    }

    @Spawns("ice_powerUp")

    public Entity icePowerUp(SpawnData data) {
        
        return FXGL.entityBuilder(data)
            .type(EntityType.POWER_UP)
            .viewWithBBox(new Circle(30, Color.BLUE))
            .with(new IcePowerUpComponent())
            .bbox(new HitBox(BoundingShape.box(LIFE_HITBOX, LIFE_HITBOX)))
            .with(new ProjectileComponent(new Point2D(-1,0), 300))
            .with(new CollidableComponent(true))
            .build();
    }

    @Spawns("fire_powerUp")

    public Entity firePowerUp(SpawnData data) {
        
        return FXGL.entityBuilder(data)
            .type(EntityType.POWER_UP)
            .viewWithBBox(new Circle(30, Color.ORANGE))
            .with(new FirePowerUpComponent())
            .bbox(new HitBox(BoundingShape.box(LIFE_HITBOX, LIFE_HITBOX)))
            .with(new ProjectileComponent(new Point2D(-1,0), 300))
            .with(new CollidableComponent(true))
            .build();
    }

    public double getLifeHitbox() {
        return LIFE_HITBOX;
    }
}
