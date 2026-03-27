package TestPackage;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.KeepOnScreenComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

//nothing to implement, just marks your factory as the entity
public class SimpleFactory implements EntityFactory {

    //Tells FXGL which methods to call when spawning <entity> ("enemy") in this case
    @Spawns("enemy")

    //Method should be precisely what it is, with the method name being the only one that can be anything.
    public Entity newEnemy(SpawnData data) {
        return FXGL.entityBuilder(data)
                .view(new Rectangle(40, 40, Color.RED))
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
