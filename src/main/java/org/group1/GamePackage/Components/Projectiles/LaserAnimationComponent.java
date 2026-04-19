package org.group1.GamePackage.Components.Projectiles;

import org.group1.GamePackage.Factory.EntityFactory.EntityType;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.scene.image.Image;
import javafx.util.Duration;

public class LaserAnimationComponent extends Component {

    private final AnimatedTexture texture;
    private final AnimationChannel laserAnimation;

    public LaserAnimationComponent() {
        Image laser_sprite = FXGL.image("laser_sprite.png");
        laserAnimation = new AnimationChannel(
            laser_sprite,
                5,
                204,
                36,
                Duration.seconds(0.5),
                0,
                4
                );

        texture = new AnimatedTexture(laserAnimation);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(laserAnimation);
    }
}
