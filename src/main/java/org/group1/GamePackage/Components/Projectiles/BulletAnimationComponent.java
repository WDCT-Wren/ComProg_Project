package org.group1.GamePackage.Components.Projectiles;

import org.group1.GamePackage.Factory.EntityFactory.EntityType;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.scene.image.Image;
import javafx.util.Duration;

public class BulletAnimationComponent extends Component {

    private final AnimatedTexture texture; 
    private final AnimationChannel bulletAnimation;

    private final EntityType bulletType;


    public BulletAnimationComponent(EntityType bulletType) {

        this.bulletType = bulletType;

        switch (bulletType) {
            case FIRE_BULLET -> {
                Image fire_bullet_sprite = FXGL.image("fire_bullet_sprite.png");
                bulletAnimation = new AnimationChannel(
                        fire_bullet_sprite,
                        4,
                        170,
                        170,
                        Duration.seconds(1),
                        0,
                        7
                        );
            }
            case ICE_BULLET -> {
                Image ice_bullet_sprite = FXGL.image("ice_bullet_sprite.png");
                bulletAnimation = new AnimationChannel(
                        ice_bullet_sprite,
                        2,
                        159,
                        43,
                        Duration.seconds(1),
                        0,
                        3
                        );

            }
            default -> { 
                Image bullet_sprite = FXGL.image("bullet_sprite.png");
                bulletAnimation = new AnimationChannel(
                        bullet_sprite,
                        4,
                        76,
                        29,
                        Duration.seconds(0.1),
                        0,
                        3
                        );
            }
        }

        texture = new AnimatedTexture(bulletAnimation);
    }


    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(bulletAnimation);
    }
}
