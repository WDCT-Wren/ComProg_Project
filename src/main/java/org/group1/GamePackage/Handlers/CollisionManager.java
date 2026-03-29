package org.group1.GamePackage.Handlers;

import org.group1.GamePackage.Components.CupHeadComponent;
import org.group1.GamePackage.Components.EnemyAnimationComponent;
import org.group1.GamePackage.EntityFactory.SimpleFactory.EntityType;
import org.group1.GamePackage.Music.AudioManager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

public class CollisionManager {
    AudioManager audioManager = new AudioManager();

    public void init() {
        enemyVSbullet();
        enemyVSplayer ();
    }

    public void enemyVSbullet () {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                    EntityType.BULLET,
                    EntityType.ENEMY)
                {
                    @Override
                    protected void onCollisionBegin(Entity bullet, Entity enemy){
                        bullet.removeFromWorld();
                        audioManager.playDeathSound();
                        enemy.getComponent(EnemyAnimationComponent.class).explode();
                    }
                });
    }

    public void enemyVSplayer () {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                    EntityType.ENEMY,
                    EntityType.PLAYER) 
                {
                    @Override
                    protected void onCollisionBegin(Entity enemy, Entity player) {
                        var playerComponent = player.getComponent(CupHeadComponent.class);
                        audioManager.FAH();
                        audioManager.playDeathSound();
                        playerComponent.takeDamage(10);
                        playerComponent.decreaseLives();

                        enemy.getComponent(EnemyAnimationComponent.class).explode();
                    }  
                });
    }
}
