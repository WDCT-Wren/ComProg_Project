package org.group1.GamePackage.Components.PowerUps;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.entity.component.Component;

import javafx.scene.image.Image;
import javafx.util.Duration;

public class IcePowerUpComponent extends Component{
    private static final int ICE_DAMAGE = 2;
    private static final int SLOW_EFFECT = 100;
    private static final int DASH_SLOW = 950;
    private static final double SLOW_SHOOTING_EFFECT = 1.2;

    private final AnimatedTexture texture;
    private final AnimationChannel IcePowerUp;

    public IcePowerUpComponent() {
        Image ice_potion = FXGL.image("ice_potion_sprite.png");
        IcePowerUp = new AnimationChannel(
                ice_potion,
                8,
                28,
                48,
                Duration.seconds(0.3),
                0,
                7
                );
        texture = new AnimatedTexture(IcePowerUp);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(IcePowerUp);

        // flips it vertically
        texture.setScaleY(-1);
    }



    public static int getDASH_SLOW() {
        return DASH_SLOW;
    }

    public static int getSLOW_EFFECT() {
        return SLOW_EFFECT;
    }

    public static int getICE_DAMAGE() {
        return ICE_DAMAGE;
    }

    public static double getSLOW_SHOOTING_EFFECT() {
        return SLOW_SHOOTING_EFFECT;
    }
    
}
