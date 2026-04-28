package org.group1.GamePackage.Components.PowerUps;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.scene.image.Image;
import javafx.util.Duration;

public class BoostUpComponent extends Component {

    private final AnimatedTexture texture;
    private final AnimationChannel boostUp;
    private final AnimationChannel boostExplode;
    private boolean exploding = false;

    public BoostUpComponent() {
        Image boost_sprite = FXGL.image("boost_sprite.png");
        boostUp = new AnimationChannel(
                boost_sprite,
                1,
                62,
                99,
                Duration.seconds(0.3),
                0,
                7
        );

        Image boost_explode = FXGL.image("boost_explode.png");
        boostExplode = new AnimationChannel(
                boost_explode,
                2,
                369,
                291,
                Duration.seconds(0.6),
                0,
                7
        );

        texture = new AnimatedTexture(boostUp);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(boostUp);
        texture.setScaleY(-1);
    }

    public void explode() {
        if (exploding) return;
        exploding = true;

        texture.stop();
        entity.getViewComponent().clearChildren();

        AnimatedTexture explodeTexture = new AnimatedTexture(boostExplode);
        // Center the 369x210 explosion relative to the 62x99 boost sprite
        explodeTexture.setTranslateX(-369 / 2.0 + 62 / 2.0);
        explodeTexture.setTranslateY(-210 / 2.0 + 99 / 2.0);

        entity.getViewComponent().addChild(explodeTexture);
        explodeTexture.playAnimationChannel(boostExplode);

        FXGL.runOnce(() -> {
            if (entity != null) {
                entity.removeFromWorld();
                exploding = false;
            }
        }, Duration.seconds(0.6));
    }
}
