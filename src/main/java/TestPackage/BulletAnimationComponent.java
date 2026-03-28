package TestPackage;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.scene.image.Image;
import javafx.util.Duration;

public class BulletAnimationComponent extends Component {

    private final AnimatedTexture texture; 
    private final AnimationChannel normalBullet;


    BulletAnimationComponent() {
        Image bullet_sprite = FXGL.image("bullet_sprite.png");
        normalBullet = new AnimationChannel(
                bullet_sprite,
                4,
                76,
                29,
                Duration.seconds(0.1),
                0,
                3
                );

        texture = new AnimatedTexture(normalBullet);
    }


    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(normalBullet);
    }
}
