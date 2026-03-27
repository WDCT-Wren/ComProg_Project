package TestPackage;

import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import javafx.util.Duration;

import javafx.scene.image.Image;



public class AnimationComponent extends Component {

    private final AnimatedTexture texture;
    private final AnimationChannel idleChannel;


    AnimationComponent() {
    Image idle_sprite = FXGL.image("idle_sprite.png"); //sprite image in resource folder
    idleChannel = new AnimationChannel(
        idle_sprite,
            1,
            112, //Width
            98, //Height 
            Duration.seconds(0.1),
            0,
            3
            );

    texture = new AnimatedTexture(idleChannel);
    }

    // Animation loop 
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(idleChannel);
    }

}
