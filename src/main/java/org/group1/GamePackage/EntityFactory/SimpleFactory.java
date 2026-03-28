package org.group1.GamePackage.EntityFactory;

import org.group1.GamePackage.Components.AnimationComponent;
import org.group1.GamePackage.Components.BulletAnimationComponent;
import org.group1.GamePackage.Components.CupHeadComponent;
import org.group1.GamePackage.Components.EnemyAnimationComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.KeepOnScreenComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;

import javafx.geometry.Point2D;


//nothing to implement, just marks your factory as the entity
public class SimpleFactory implements EntityFactory {

    public enum EntityType {
        PLAYER, 
        ENEMY, 
        BULLET
    }

    //Tells FXGL which methods to call when spawning <entity> ("enemy") in this case
    @Spawns("bullet")

    //Method should be precisely what it is, with the method name being the only one that can be anything.
    public Entity newBullet(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.BULLET)
                .with(new BulletAnimationComponent())
                .with(new ProjectileComponent(new Point2D(1,0), 1000))
                .bbox(new HitBox(BoundingShape.box(25, 25)))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("cupheadPlane")

    public Entity cupheadPlane(SpawnData data) {

        return FXGL.entityBuilder(data)
                // Declare new Point2D(x,y) class with its corresponding speed (pixel per second)
                .type(EntityType.PLAYER)
                .with(new CupHeadComponent())
                .with(new AnimationComponent())
                .bbox(new HitBox(BoundingShape.box(120, 100)))
                .with(new KeepOnScreenComponent())
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("enemy")

    public Entity enemy(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(EntityType.ENEMY)
            .with(new ProjectileComponent(new Point2D(-1,0), 500))
            .with(new EnemyAnimationComponent())
            .bbox(new HitBox(BoundingShape.box(100, 100)))
            .with(new CollidableComponent(true))
            .with(new KeepOnScreenComponent())
            .build();

    }
}
