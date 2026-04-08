package org.group1.GamePackage.Components.Enemy;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.scene.image.Image;
import javafx.util.Duration;

public class EnemyAnimationComponent extends Component {

    private final AnimatedTexture texture; 
    private final AnimationChannel normalEnemy;
    private final AnimationChannel explosionAnimation;
    private boolean exploding = false;

    public EnemyAnimationComponent() {
        Image enemy_sprite = FXGL.image("enemy_sprite.png");
        normalEnemy = new AnimationChannel(
                enemy_sprite,
                8,
                104,
                97,
                Duration.seconds(0.3),
                0,
                7
                );


        Image explosion_sprite = FXGL.image("explosion_sprite.png");
        explosionAnimation = new AnimationChannel(
                explosion_sprite,
                13,
                500,
                500,
                Duration.seconds(0.2),
                0,
                12
                );


        texture = new AnimatedTexture(normalEnemy);
    }


    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(normalEnemy);
    }

    // Explode method gets called when bullet collides with enemy at initPhysics 
    public void explode() {
        // returns if currently exploding
        if (exploding) return;
        exploding = true;

        // Stops current animation
        texture.stop();
        // Clear View Component because the explosion_sprite is 500x500
        // This is larger than the normalEnemy sprite so we have to change it
        entity.getViewComponent().clearChildren();

        // Construct a new AnimatedTexture with the explosionAnimation
        AnimatedTexture explodeTexture = new AnimatedTexture(explosionAnimation);

        // Translate X and Y to be 500x500
        explodeTexture.setTranslateX(-500 / 2.0 + 104 / 2.0);
        explodeTexture.setTranslateY(-500 / 2.0 + 97 / 2.0);

        // Plays the animation
        entity.getViewComponent().addChild(explodeTexture);
        explodeTexture.playAnimationChannel(explosionAnimation);

        // runOnce before removing the enemy 
        FXGL.runOnce(() -> {
            // Checks if entity is not null before removing 
            if (entity != null) {
                entity.removeFromWorld();
                exploding = false;
            }
        }, Duration.seconds(0.1));
    }
}


