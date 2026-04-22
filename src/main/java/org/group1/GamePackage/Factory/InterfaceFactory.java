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
        DEFAULT_SWITCH_HUD,
        LIVES
    }

    @Spawns("fire_hud")

    public Entity fireHud(SpawnData data) {
        return FXGL.entityBuilder(data)
            .at(0, 500)
            .with(new SwitchBulletAnimationComponent(EntityType.FIRE_SWITCH_HUD))
            .zIndex(Integer.MAX_VALUE)
            .build();
    }

    @Spawns("ice_hud")

    public Entity iceHud(SpawnData data) {
        return FXGL.entityBuilder(data)
            .at(0, 500)
            .with(new SwitchBulletAnimationComponent(EntityType.ICE_SWITCH_HUD))
            .zIndex(Integer.MAX_VALUE)
            .build();
    }

    @Spawns("default_hud")

    public Entity defaultHud(SpawnData data) {
        return FXGL.entityBuilder(data)
            .at(30, 540)
            .scale(0.7, 0.7)
            .with(new SwitchBulletAnimationComponent(EntityType.DEFAULT_SWITCH_HUD))
            .zIndex(Integer.MAX_VALUE)
            .build();
    }

    // Lives till death lol
    @Spawns("lives_09")

    public Entity lives_09(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(EntityType.LIVES)
            .view(FXGL.texture("lives_09.png", 600, 180))
            .scale( 0.6 , 0.6 )
            .zIndex(Integer.MAX_VALUE)
            .build();
    }

    @Spawns("lives_08")

    public Entity lives_08(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(EntityType.LIVES)
            .view(FXGL.texture("lives_08.png", 600, 180))
            .scale( 0.6 , 0.6 )
            .zIndex(Integer.MAX_VALUE)
            .build();
    }

    @Spawns("lives_07")

    public Entity lives_07(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(EntityType.LIVES)
            .view(FXGL.texture("lives_07.png", 600, 180))
            .scale( 0.6 , 0.6 )
            .zIndex(Integer.MAX_VALUE)
            .build();
    }

    @Spawns("lives_06")

    public Entity lives_06(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(EntityType.LIVES)
            .view(FXGL.texture("lives_06.png", 600, 180))
            .scale( 0.6 , 0.6 )
            .zIndex(Integer.MAX_VALUE)
            .build();
    }

    @Spawns("lives_05")
    
    public Entity lives_05(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(EntityType.LIVES)
            .view(FXGL.texture("lives_05.png", 600, 180))
            .scale( 0.6 , 0.6 )
            .zIndex(Integer.MAX_VALUE)
            .build();
    }

    @Spawns("lives_04")
    
    public Entity lives_04(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(EntityType.LIVES)
            .view(FXGL.texture("lives_04.png", 600, 180))
            .scale( 0.6 , 0.6 )
            .zIndex(Integer.MAX_VALUE)
            .build();
    }

    @Spawns("lives_03")
    public Entity lives_03(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(EntityType.LIVES)
            .view(FXGL.texture("lives_03.png", 600, 180))
            .scale( 0.6 , 0.6 )
            .zIndex(Integer.MAX_VALUE)
            .build();
    }

    @Spawns("lives_02")
    public Entity lives_02(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(EntityType.LIVES)
            .view(FXGL.texture("lives_02.png", 600, 180))
            .scale( 0.6 , 0.6 )
            .zIndex(Integer.MAX_VALUE)
            .build();
    }
    
    @Spawns("lives_01")
    public Entity lives_01(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(EntityType.LIVES)
            .view(FXGL.texture("lives_01.png", 600, 180))
            .scale( 0.6 , 0.6 )
            .zIndex(Integer.MAX_VALUE)
            .build();
    }

    @Spawns("lives_00")
    public Entity lives_00(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(EntityType.LIVES)
            .view(FXGL.texture("lives_00.png", 600, 180))
            .scale( 0.6 , 0.6 )
            .zIndex(Integer.MAX_VALUE)
            .build();
    }

} 
