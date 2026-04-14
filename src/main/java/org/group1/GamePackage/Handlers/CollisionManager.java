package org.group1.GamePackage.Handlers;

import java.util.Random;

import org.group1.GamePackage.Application;
import org.group1.GamePackage.Components.Enemy.BossComponent;
import org.group1.GamePackage.Components.Enemy.EnemyAnimationComponent;
import org.group1.GamePackage.Components.Enemy.EnemyDropsAnimationComponent;
import org.group1.GamePackage.Components.Player.PlayerComponent;
import org.group1.GamePackage.Components.PowerUps.BoostUpComponent;
import org.group1.GamePackage.Components.PowerUps.FirePowerUpComponent;
import org.group1.GamePackage.Components.PowerUps.IcePowerUpComponent;
import org.group1.GamePackage.Factory.BossFactory.BossType;
import org.group1.GamePackage.Factory.EntityFactory.EntityType;
import org.group1.GamePackage.Music.AudioManager;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;

public class CollisionManager {
    // RNG for chance extra life drops
    private static final Random random = new Random();
    private static double POWER_UP_DROP_RATE = 1;
    private static double BOSS_POWER_UP_DROP_RATE = 0.05;
    private int randomIndex;

    AudioManager audioManager = new AudioManager();
    PlayerComponent playerComponent = new PlayerComponent(Application.inputManager);

    public void init() {
        enemyVSbullet();
        enemyVSfire();
        enemyVSice();
        enemyVSplayer();
        playerVSpowerUp();
        bossVSbullet();
        bossVSice();
        bossVSfire();
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

    // basic fire and ice bullet collsionhandler, removed the removeFromWorld if hit the enemy for piercing effect
    public void enemyVSfire () {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                EntityType.FIRE_BULLET,
                EntityType.ENEMY
                )
            {
                @Override
                protected void onCollisionBegin(Entity bullet, Entity enemy){
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

    public void enemyVSice () {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                EntityType.ICE_BULLET,
                EntityType.ENEMY
                )
            {
                @Override
                protected void onCollisionBegin(Entity bullet, Entity enemy){
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
                        bullet.removeFromWorld();

                        // get boss from the gameWorld and call it's method
                        FXGL.getGameWorld()
                            .getEntitiesByComponent(BossLevelManager.class)
                            .stream()
                            .findFirst()
                            .ifPresent(e -> e.getComponent(BossLevelManager.class).takeDamage());

                        // Sets the POWER_UP_DROP_RATE lower to avoid powerup exploit lmao
                        POWER_UP_DROP_RATE = BOSS_POWER_UP_DROP_RATE;
                        dropPowerUp(boss);
                    }
        });
    }

    // boss collision logic removes piercing effect
    public void bossVSice () {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                    BossType.BOSS,
                    EntityType.ICE_BULLET
                    ) 
                {
                    @Override
                    protected void onCollisionBegin(Entity boss, Entity bullet) {
                        bullet.removeFromWorld();

                        // get boss from the gameWorld and call it's method
                        FXGL.getGameWorld()
                            .getEntitiesByComponent(BossLevelManager.class)
                            .stream()
                            .findFirst()
                            .ifPresent(e -> e.getComponent(BossLevelManager.class).takeDamage());

                        boss.getComponent(BossComponent.class).slowEffect();
                        // Sets the POWER_UP_DROP_RATE lower to avoid powerup exploit lmao
                        POWER_UP_DROP_RATE = BOSS_POWER_UP_DROP_RATE;
                        dropPowerUp(boss);
                    }
        });
    }


    public void bossVSfire () {
        FXGL.getPhysicsWorld().addCollisionHandler(new CollisionHandler(
                    BossType.BOSS,
                    EntityType.FIRE_BULLET
                    ) 
                {
                    @Override
                    protected void onCollisionBegin(Entity boss, Entity bullet) {
                        bullet.removeFromWorld();

                        // get boss from the gameWorld and call it's method
                        FXGL.getGameWorld()
                            .getEntitiesByComponent(BossLevelManager.class)
                            .stream()
                            .findFirst()
                            .ifPresent(e -> e.getComponent(BossLevelManager.class).takeDamage());

                        // Sets the POWER_UP_DROP_RATE lower to avoid powerup exploit lmao
                        POWER_UP_DROP_RATE = BOSS_POWER_UP_DROP_RATE;
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

                    if (PlayerComponent.getLives() == 0) {
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
                var playerComponent = player.getComponent(PlayerComponent.class);

                if (powerUp.hasComponent(BoostUpComponent.class)) {
                    boostUp(powerUp, player);
                } else if (powerUp.hasComponent(IcePowerUpComponent.class)) {
                    enableIceBullet(powerUp, playerComponent);
                } else if (powerUp.hasComponent(FirePowerUpComponent.class)) {
                    enableFireBullet(powerUp, playerComponent);
                } else if (powerUp.hasComponent(EnemyDropsAnimationComponent.class)) {
                    extraLives(powerUp, playerComponent);
                }
            }
        });
    }

    private void extraLives(Entity powerUp, PlayerComponent playerComponent) {
        audioManager.playHeartGain();

        playerComponent.increaseLives();

        powerUp.getComponent(EnemyDropsAnimationComponent.class).explodeHeart();
    }

    private void boostUp(Entity powerUp, Entity player) {
        powerUp.removeFromWorld();
        GameMechanics.speedUp(player);
    }

    private void enableIceBullet(Entity powerUp,  PlayerComponent playerComponent) {
        playerComponent.toggleIceBullet(true);
        PlayerComponent.iceBulletAdd(10);
        powerUp.removeFromWorld();
    }

    private void enableFireBullet(Entity powerUp,  PlayerComponent playerComponent) {
        playerComponent.toggleFireBullet(true);
        PlayerComponent.fireBulletAdd(10);
        powerUp.removeFromWorld();
    }

    // method takes an Entity parameter to get its center
    // RNG powerup spawn at Center 
    // 10% chance for powerup, then 50/50 split between boostUp and extraLife
    private void dropPowerUp(Entity entity) {
        if (random.nextDouble() < POWER_UP_DROP_RATE) {
            String[] powerUps = {"extraLife", "boostUp", "ice_powerUp", "fire_powerUp"};
            randomIndex = random.nextInt(4);

            String powerUpType = powerUps[randomIndex];
            System.out.println(powerUpType);

            FXGL.spawn(powerUpType, entity.getCenter());
        }

    }
}
