package org.group1.GamePackage;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.scene.image.Image;
import javafx.util.Duration;

public class EnemyAnimationComponent extends Component {

    private final AnimatedTexture texture; 
    private final AnimationChannel normalEnemy;

    EnemyAnimationComponent() {
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

        texture = new AnimatedTexture(normalEnemy);
    }


    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(normalEnemy);
    }

}


