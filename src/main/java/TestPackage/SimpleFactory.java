package TestPackage;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

    @Spawns("movingEntity")

    public Entity newMovingEnemy(SpawnData data) {

        return FXGL.entityBuilder(data)
                // Declare new Point2D(x,y) class with its corresponding speed (pixel per second)
                .with(new ProjectileComponent(new Point2D(1,0), 0))
                .with(new AnimationComponent())
                .build();
    }
}
