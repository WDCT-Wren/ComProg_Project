package org.group1.GamePackage.Handlers;

import java.util.Random;

import org.group1.GamePackage.Application;
import org.group1.GamePackage.Components.BoostUpComponent;
import org.group1.GamePackage.Components.PlayerComponent;
import org.group1.GamePackage.Components.EnemyAnimationComponent;
import org.group1.GamePackage.Components.EnemyDropsAnimationComponent;
import org.group1.GamePackage.Factory.BossFactory.BossType;
import org.group1.GamePackage.Factory.EntityFactory.EntityType;
import org.group1.GamePackage.Music.AudioManager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

public class CollisionManager {
    // RNG for chance extra life drops
    private static final Random random = new Random();
    private static double POWER_UP_DROP_RATE = 0.10;
    private int randomIndex;

    AudioManager audioManager = new AudioManager();
    PlayerComponent playerComponent = new PlayerComponent(Application.inputManager);

    public void init() {
        enemyVSbullet();
        enemyVSplayer();
        playerVSpowerUp();
        bossVSbullet();
        playerVSboss();
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
                        .getComponent(PlayerComponent.class);

                    enemyComponent.addScore();
                    dropPowerUp(enemy);

                    // your existing explosion code here
                    audioManager.playDeathSound();
                    enemy.getComponent(EnemyAnimationComponent.class).explode();
                }
            });
    }

    public void bossVSbullet () {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                    BossType.BOSS,
                    EntityType.BULLET
                    ) 
                {
                    @Override
                    protected void onCollisionBegin(Entity boss, Entity bullet) {
                    var BOSS = boss.getComponent(BossLevelManager.class);
                        bullet.removeFromWorld();
                        BOSS.takeDamage();

                        // Sets the POWER_UP_DROP_RATE lower to avoid powerup exploit lmao
                        POWER_UP_DROP_RATE = 0.01;
                        System.out.println(POWER_UP_DROP_RATE);
                        dropPowerUp(boss);
                    }
        });

    }

    public void playerVSboss () {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                    EntityType.PLAYER,
                    BossType.BOSS
                    ) 
                {
                    @Override
                    protected void onCollisionBegin(Entity player, Entity boss){
                        var playerComponent = player.getComponent(PlayerComponent.class);
                        audioManager.playDeathSound();
                        playerComponent.takeDamage();

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
                    var playerComponent = player.getComponent(PlayerComponent.class);
                    if (!playerComponent.isInvincible()) {
                        audioManager.playDeathSound();
                        playerComponent.takeDamage();

                        enemy.getComponent(EnemyAnimationComponent.class).explode();
                    }

                    if (playerComponent.getLives() == 0) {
                        // IDK ANIMATE IT IDK
                        player.getViewComponent().setVisible(false);
                    }
                }
            });
    }

    public void playerVSpowerUp() {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                EntityType.POWER_UP,
                EntityType.PLAYER)
        {
            @Override
            protected void onCollisionBegin(Entity powerUp, Entity player) {
                if (powerUp.hasComponent(BoostUpComponent.class)) {
                    boostUp(powerUp, player);
                } else if (powerUp.hasComponent(EnemyDropsAnimationComponent.class)) {
                    extraLives(powerUp, player);
                }
            }
        });
    }

    private void extraLives(Entity powerUp, Entity player) {
        var playerComponent = player.getComponent(PlayerComponent.class);
        audioManager.playHeartGain();

        playerComponent.increaseLives();

        powerUp.getComponent(EnemyDropsAnimationComponent.class).explodeHeart();
    }

    private void boostUp(Entity powerUp, Entity player) {
        powerUp.removeFromWorld();
        GameMechanics.speedUp(player);
    }

    // method takes an Entity parameter to get its center
    // RNG powerup spawn at Center 
    // 10% chance for powerup, then 50/50 split between boostUp and extraLife
    private void dropPowerUp(Entity entity) {
        if (random.nextDouble() < POWER_UP_DROP_RATE) {
            String[] powerUps = {"extraLife", "boostUp"};
            randomIndex = random.nextInt(2);

            String powerUpType = powerUps[randomIndex];
            System.out.println(powerUpType);

            FXGL.spawn(powerUpType, entity.getCenter());
        }

    }
}
