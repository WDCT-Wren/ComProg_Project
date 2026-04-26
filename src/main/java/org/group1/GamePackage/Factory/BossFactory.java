package org.group1.GamePackage.Factory;

import org.group1.GamePackage.Components.Enemy.BossComponent;
import org.group1.GamePackage.Components.Enemy.MiniBossComponent;
import org.group1.GamePackage.Components.Projectiles.LaserAnimationComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class BossFactory implements EntityFactory {

    public enum BossType {
        BOSS,
        BOSS_LASER,
        MINI_BOSS,
        MINI_BOSS_LASER
    }

    private static final int BOSS_HITBOX = 400;
    private static final int MINI_BOSS_HITBOX = 400;

    //Method for adding hitbox using viewWithBbox
    private Rectangle hitboxVisual(double h, double w) {
        Rectangle hitboxVisual = new Rectangle(h, w);
            hitboxVisual.setFill(Color.TRANSPARENT);
            hitboxVisual.setStroke(Color.RED);
            hitboxVisual.setStrokeWidth(1);
        return hitboxVisual;
    }

    @Spawns("boss")

    public Entity newBoss(SpawnData data) {

        return FXGL.entityBuilder(data)
            .type(BossType.BOSS)
            .viewWithBBox(hitboxVisual(BOSS_HITBOX, BOSS_HITBOX))
            .bbox(new HitBox(BoundingShape.box(BOSS_HITBOX, BOSS_HITBOX)))
            .with(new BossComponent())
            .with(new CollidableComponent(true))
            .build();
    }

    @Spawns("mini_boss")

    public Entity newMiniBoss(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(BossType.MINI_BOSS)
            .viewWithBBox(hitboxVisual(MINI_BOSS_HITBOX, MINI_BOSS_HITBOX))
            .bbox(new HitBox(BoundingShape.box(MINI_BOSS_HITBOX, MINI_BOSS_HITBOX)))
            .with(new MiniBossComponent())
            .with(new CollidableComponent(true))
            .build();
    }

    @Spawns("lasers")

    public Entity newLasers(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(BossType.BOSS_LASER)
                .viewWithBBox(hitboxVisual(150, 15))
                .bbox(new HitBox(BoundingShape.box(150, 15)))
                .with(new LaserAnimationComponent()) 
                .with(new ProjectileComponent(new Point2D(-1,0), 500))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("mini_lasers")
    public Entity newMiniLasers(SpawnData data) {

        return FXGL.entityBuilder(data)
                .type(BossType.MINI_BOSS_LASER)
                .viewWithBBox(hitboxVisual(80, 15))
                .bbox(new HitBox(BoundingShape.box(80, 15)))
                .with(new LaserAnimationComponent()) 
                .with(new ProjectileComponent(new Point2D(-1, 0), 600))
                .with(new CollidableComponent(true))
                .build();
    }
}

