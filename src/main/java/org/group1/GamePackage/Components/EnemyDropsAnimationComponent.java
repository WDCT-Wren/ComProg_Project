package org.group1.GamePackage.Components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.scene.image.Image;
import javafx.util.Duration;

public class EnemyDropsAnimationComponent extends Component {

    private final AnimatedTexture texture; 
    private final AnimationChannel heartDrop;
    private final AnimationChannel heartExplode;
    private boolean exploding = false;

    private int HEART_EXPLOSION_SIZE = 350;

    public EnemyDropsAnimationComponent() {
        Image heart_sprite = FXGL.image("heart_sprite.png");
        heartDrop = new AnimationChannel(
                heart_sprite,
                12,
                157,
                128,
                Duration.seconds(0.1),
                0,
                11
                );

        Image heart_explode_sprite = FXGL.image("heart_explode_sprite.png");
        heartExplode = new AnimationChannel(
                heart_explode_sprite,
                8,
                HEART_EXPLOSION_SIZE,
                HEART_EXPLOSION_SIZE,
                Duration.seconds(0.3),
                0,
                7
                );

        texture = new AnimatedTexture(heartDrop);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(heartDrop);

    }

    public void explodeHeart() {
        if (exploding) return;
        exploding = true;


        texture.stop();
        entity.getViewComponent().clearChildren();
        AnimatedTexture explodeTexture = new AnimatedTexture(heartExplode);


        explodeTexture.setScaleY(-1);
        explodeTexture.setScaleX(1);
        explodeTexture.setTranslateX(-HEART_EXPLOSION_SIZE / 2.0 + 157 / 2.0);
        explodeTexture.setTranslateY(-HEART_EXPLOSION_SIZE / 2.0 + 128 / 2.0);

        entity.getViewComponent().addChild(explodeTexture);
        explodeTexture.playAnimationChannel(heartExplode);

       FXGL.runOnce(() -> {
            // Checks if entity is not null before removing 
            if (entity != null) {
                entity.removeFromWorld();
                exploding = false;
            }
        }, Duration.seconds(0.3));
    }
}


