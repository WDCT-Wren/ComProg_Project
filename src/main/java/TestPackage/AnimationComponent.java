package TestPackage;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.scene.image.Image;
import javafx.util.Duration;



public class AnimationComponent extends Component {

    private final AnimatedTexture texture;
    private final AnimationChannel idleChannel;
    private final AnimationChannel downChannel;
    private final AnimationChannel upChannel;


    AnimationComponent() {
        Image idle_sprite = FXGL.image("idle_sprite.png"); //idle_sprite image in resource folder
        idleChannel = new AnimationChannel(
                idle_sprite,
                1,
                112, //Width
                98, //Height 
                Duration.seconds(0.1),
                0,
                3
                );

        Image down_sprite = FXGL.image("down_sprite.png"); //down_sprite image in resource folder
        downChannel = new AnimationChannel(
                down_sprite,
                1,
                110, // Width
                103, // Total Height divided by amount of frames (4)
                Duration.seconds(0.1),
                0,
                3
                );

        Image up_sprite = FXGL.image("up_sprite.png"); //up_sprite image in resource folder
        upChannel = new AnimationChannel(
                up_sprite,
                1,
                114, // Width
                92, // Total Height divided by amount of frames (4)
                Duration.seconds(0.1),
                0,
                3
                );



        texture = new AnimatedTexture(idleChannel);
    }



    // Idle Animation Loop On Spawn 
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(idleChannel);
    }

    // Down Animation Loop
    public void onDown() {
        if (texture.getAnimationChannel() != downChannel) {
            texture.loopAnimationChannel(downChannel);
        }
    }

    // Up Animation Loop
    public void onUp() {
        if (texture.getAnimationChannel() != upChannel) {
            texture.loopAnimationChannel(upChannel);
        }
    }

    // Idle Animation Loop
    public void onIdle() {
        if (texture.getAnimationChannel() != idleChannel) {
            texture.loopAnimationChannel(idleChannel);
        }
    }

}
