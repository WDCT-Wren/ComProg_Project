package org.group1.GamePackage.Factory;

import org.group1.GamePackage.Components.Enemy.BossComponent;
import org.group1.GamePackage.Handlers.BossLevelManager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;

public class BossFactory implements EntityFactory {

    public enum BossType {
        BOSS
    }

    private int BOSS_HITBOX = 400;


    @Spawns("boss")

    public Entity newBoss(SpawnData data) {
        return FXGL.entityBuilder(data)
            .type(BossType.BOSS)
            .viewWithBBox(FXGL.texture("boss_placeholder.png", BOSS_HITBOX, BOSS_HITBOX))
            .with(new BossComponent())
            .with(new CollidableComponent(true))
            .build();
    }


}

