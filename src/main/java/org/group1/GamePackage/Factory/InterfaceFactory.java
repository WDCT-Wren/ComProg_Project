package org.group1.GamePackage.Factory;

import org.group1.GamePackage.Components.UI.SwitchBulletAnimationComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

public class InterfaceFactory implements EntityFactory {

    public enum EntityType {
        FIRE_SWITCH_HUD,
        ICE_SWITCH_HUD,
        DEFAULT_SWITCH_HUD
    }

    @Spawns("fire_hud")
    public Entity fireHud(SpawnData data) {
        return FXGL.entityBuilder(data)
            .at(0, 500)
            .with(new SwitchBulletAnimationComponent(EntityType.FIRE_SWITCH_HUD))
            .zIndex(100)
            .build();
    } 

    @Spawns("ice_hud")
    public Entity iceHud(SpawnData data) {
        return FXGL.entityBuilder(data)
            .at(0,500)
            .with(new SwitchBulletAnimationComponent(EntityType.ICE_SWITCH_HUD))
            .zIndex(100)
            .build();
    }

    @Spawns("default_hud")
    public Entity defaultHud(SpawnData data) {
        return FXGL.entityBuilder(data)
            .at(20, 520)
            .scale(0.8, 0.8) 
            .with(new SwitchBulletAnimationComponent(EntityType.DEFAULT_SWITCH_HUD))
            .zIndex(100)
            .build();
    }
} 
