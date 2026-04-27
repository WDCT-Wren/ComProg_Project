package org.group1.GamePackage.Components.Enemy;

import org.group1.GamePackage.Components.Player.PlayerComponent;
import org.group1.GamePackage.Components.PowerUps.FirePowerUpComponent;
import org.group1.GamePackage.Components.PowerUps.IcePowerUpComponent;

import com.almasb.fxgl.dsl.FXGL;

import javafx.util.Duration;

public class MiniBossComponent extends BossComponent {

    private static int MINI_BOSS_HEALTH = 75;
    private static double MINI_BOSS_SPEED = 250;
    private static double MINI_BOSS_CHARGE_SPEED = 1500; 
    private static double MINI_BOSS_MOVE_RANGE = 100;

    private static final double MINI_BOSS_BURN_RATE = 1;
    private static final double MINI_BOSS_BURN_DURATION = 3;
    private static final double MINI_BOSS_SHOOTING_RATE = 0.5;

    @Override 
    public void onAdded() {
        super.onAdded();
        entity.setScaleX(0.4);
        entity.setScaleY(0.4);
        CURRENT_HEALTH = MINI_BOSS_HEALTH;
        SPEED_Y = MINI_BOSS_SPEED;
        CHARGE_SPEED = MINI_BOSS_CHARGE_SPEED;
        MOVE_RANGE = MINI_BOSS_MOVE_RANGE;
    }

    @Override
    public void takeDamage(int damage) {
        if (entity == null || !entity.isActive()) return;

        CURRENT_HEALTH -= damage;

        // Mini boss dies silently with no health bar
        if (dead()) {
            PlayerComponent.addScore(20);
            entity.removeFromWorld();
        }
    }

    @Override
    public boolean dead() {
        return CURRENT_HEALTH <= 0;
    }

    @Override
    public void slowEffect() {
        SPEED_Y = IcePowerUpComponent.getSLOW_EFFECT();
        CHARGE_SPEED = IcePowerUpComponent.getDASH_SLOW();
        BOSS_SHOOTING_RATE = IcePowerUpComponent.getSLOW_SHOOTING_EFFECT();

        // overrides this logic
        if (shootInterval != null && !shootInterval.isExpired()) {
            shootInterval.expire();
            shootInterval = FXGL.getGameTimer().runAtInterval(() -> {
                FXGL.spawn(getLaser(), INITIAL_BOSS_X, CHARGE_TARGET_Y);
            }, Duration.seconds(BOSS_SHOOTING_RATE));
        }

        FXGL.getGameTimer().runOnceAfter(() -> {
            SPEED_Y = MINI_BOSS_SPEED;
            CHARGE_SPEED = MINI_BOSS_CHARGE_SPEED;
            BOSS_SHOOTING_RATE = MINI_BOSS_SHOOTING_RATE;
        }, javafx.util.Duration.seconds(SLOW_DURATION));
    }

    @Override
    public void burnEffect() {
        var burnTask = FXGL.getGameTimer().runAtInterval(() -> {
            if (entity == null || !entity.isActive()) return;
            takeDamage(FirePowerUpComponent.getFIRE_DAMAGE());
        }, Duration.seconds(MINI_BOSS_BURN_RATE));

        FXGL.getGameTimer().runOnceAfter(burnTask::expire, Duration.seconds(MINI_BOSS_BURN_DURATION));
    }

    @Override 
    protected String getLaser() {
        return "mini_lasers";
    }

}
