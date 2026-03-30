package org.group1.GamePackage.Components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.scene.image.Image;
import javafx.util.Duration;

public class OverlayAnimationComponent extends Component {

    private final AnimatedTexture texture; 
    private final AnimationChannel deathChannel;

    public OverlayAnimationComponent(){
        Image death_sprite = FXGL.image("death_sprite.png");
        deathChannel = new AnimationChannel(
                death_sprite,
                18,
                1080,
                234,
                Duration.seconds(3),
                0,
                17
                );
                
        texture = new AnimatedTexture(deathChannel);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(deathChannel);
    }


}


