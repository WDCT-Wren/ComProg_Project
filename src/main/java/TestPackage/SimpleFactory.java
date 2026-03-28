package TestPackage;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.KeepOnScreenComponent;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.geometry.Point2D;

//nothing to implement, just marks your factory as the entity
public class SimpleFactory implements EntityFactory {

    //Tells FXGL which methods to call when spawning <entity> ("enemy") in this case
    @Spawns("bullet")

    //Method should be precisely what it is, with the method name being the only one that can be anything.
    public Entity newBullet(SpawnData data) {
        return FXGL.entityBuilder(data)
                .with(new BulletAnimationComponent()) 
                .with(new ProjectileComponent(new Point2D(1,0), 1000))
                .build();
    }

    @Spawns("cupheadPlane")

    public Entity cupheadPlane(SpawnData data) {

        return FXGL.entityBuilder(data)
                // Declare new Point2D(x,y) class with its corresponding speed (pixel per second)
                .with(new AnimationComponent())
                .bbox(new HitBox(BoundingShape.box(120, 100)))
                .with(new KeepOnScreenComponent())
                .build();
    }
}
