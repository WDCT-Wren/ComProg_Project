package org.group1.GamePackage.Components.PowerUps;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.entity.component.Component;

import javafx.scene.image.Image;
import javafx.util.Duration;

public class FirePowerUpComponent extends Component{
    private static final int FIRE_DAMAGE = 2;

    private final AnimatedTexture texture;
    private final AnimationChannel FirePowerUp;

    public FirePowerUpComponent() {
        Image fire_potion = FXGL.image("fire_potion_sprite.png");
        FirePowerUp = new AnimationChannel(
                fire_potion,
                8,
                28,
                48,
                Duration.seconds(0.3),
                0,
                7
                );
        texture = new AnimatedTexture(FirePowerUp);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(FirePowerUp);

        // flips it vertically
        texture.setScaleY(-1);
    }

    public static int getFIRE_DAMAGE() {
        return FIRE_DAMAGE;
    }
    
}
