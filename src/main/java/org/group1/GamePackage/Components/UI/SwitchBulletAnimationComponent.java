package org.group1.GamePackage.Components.UI;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import javafx.scene.image.Image;
import javafx.util.Duration;

import org.group1.GamePackage.Factory.InterfaceFactory.EntityType;
public class SwitchBulletAnimationComponent extends Component {
    private AnimatedTexture texture;
    private AnimationChannel currentHUD;
    private final EntityType HUDtype;

    public SwitchBulletAnimationComponent(EntityType bulletType) {
        this.HUDtype = bulletType;
    }

    @Override
    public void onAdded() {
        switch (HUDtype) {
            case FIRE_SWITCH_HUD -> {
                Image fire_HUD_sprite = FXGL.image("fire_HUD_sprite.png");
                currentHUD = new AnimationChannel(
                        fire_HUD_sprite, 
                        1, 
                        250, 
                        250, 
                        Duration.seconds(1), 
                        0, 
                        0
                        );
            }
            case ICE_SWITCH_HUD -> {
                Image ice_HUD_sprite = FXGL.image("ice_HUD_sprite.png");
                currentHUD = new AnimationChannel(ice_HUD_sprite, 
                        1, 
                        250, 
                        250, 
                        Duration.seconds(1), 
                        0, 
                        0
                        );
            }
            default -> {
                Image bullet_HUD_selection = FXGL.image("bullet_HUD_selection.png");
                currentHUD = new AnimationChannel(bullet_HUD_selection, 
                        1, 
                        350, 
                        200, 
                        Duration.seconds(1), 
                        0, 
                        0
                        );
            }
        }

        texture = new AnimatedTexture(currentHUD);
        entity.getViewComponent().addChild(texture);
        texture.loopAnimationChannel(currentHUD);
    }
}
