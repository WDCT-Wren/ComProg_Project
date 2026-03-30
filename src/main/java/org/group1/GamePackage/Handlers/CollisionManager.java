package org.group1.GamePackage.Handlers;

import java.util.Random;

import org.group1.GamePackage.Components.CupHeadComponent;
import org.group1.GamePackage.Components.EnemyAnimationComponent;
import org.group1.GamePackage.Components.EnemyDropsAnimationComponent;
import org.group1.GamePackage.Factory.EntityFactory.EntityType;
import org.group1.GamePackage.Music.AudioManager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

public class CollisionManager {
    // RNG for chance extra life drops
    private static final Random random = new Random();
    private static final double LIFE_DROP_RATE = 0.10;

    AudioManager audioManager = new AudioManager();
    CupHeadComponent cupHeadComponent = new CupHeadComponent();

    public void init() {
        enemyVSbullet();
        enemyVSplayer();
        playerGetsExtraLife();
    }

    public void enemyVSbullet () {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                EntityType.BULLET,
                EntityType.ENEMY
                )
            {
                @Override
                protected void onCollisionBegin(Entity bullet, Entity enemy){
                    bullet.removeFromWorld();
                    var enemyComponent = FXGL.getGameWorld()
                        .getSingleton(EntityType.PLAYER)
                        .getComponent(CupHeadComponent.class);

                    enemyComponent.addScore();
                    audioManager.playDeathSound();
                    enemy.getComponent(EnemyAnimationComponent.class).explode();

                    // your existing explosion code here

                    // RNG powerup spawn at Center 
                    if (random.nextDouble() < LIFE_DROP_RATE) {
                        FXGL.spawn("extraLife", enemy.getCenter());
                    }
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
                    if (!playerComponent.isInvincible()) {
                        audioManager.playDeathSound();
                        playerComponent.takeDamage();

                        enemy.getComponent(EnemyAnimationComponent.class).explode();
                    }
                }
            });
    }

    public void playerGetsExtraLife() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                EntityType.POWER_UP,
                EntityType.PLAYER)
        {
            @Override
            protected void onCollisionBegin(Entity powerUp, Entity player) {
                var playerComponent = player.getComponent(CupHeadComponent.class);
                audioManager.playHeartGain();

                playerComponent.increaseLives();

                powerUp.getComponent(EnemyDropsAnimationComponent.class).explodeHeart();
            }
        });
    }
}
