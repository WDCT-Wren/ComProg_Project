package org.group1.GamePackage.Handlers;

import org.group1.GamePackage.Components.Player.PlayerComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

public class GameMechanics {

    private static long firstBullet = 0;
    private static long BULLET_COOLDOWN = 300;
    private static boolean isInvincible = false;
    private static String currentBulletType = "bullet";

    public static void shoot(Entity player) {
        // Null check - player might not be initialized yet
        if (player == null) {
            return;
        }

        // Delay curretTimeMillis
        long currentTime = System.currentTimeMillis();
        if (!isInvincible && currentTime - firstBullet >= BULLET_COOLDOWN) {
            FXGL.spawn(currentBulletType, player.getX() + 120, player.getY() + 50);
            firstBullet = currentTime;
        }
    }

    public static void setFireBullet() {
        currentBulletType = "fire_bullet";
    }
    
    public static void setIceBullet() {
        currentBulletType = "ice_bullet";
    }

    public static void speedUp(Entity player) {
        player.getComponent(PlayerComponent.class).setBoostLevel(10);
    }

    public void setInvincible() {
        isInvincible = true;
    }

    public void offInvincible() {
        isInvincible = false;
    }

    public static void pauseGame() {
        FXGL.getGameController().pauseEngine();
    }

    public static void resumeGame() {
        FXGL.getGameController().resumeEngine();
    }

    public static void setBULLET_COOLDOWN(long BULLET_COOLDOWN) {
        GameMechanics.BULLET_COOLDOWN = BULLET_COOLDOWN;
    }

}
