package org.group1.GamePackage.Handlers;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

public class GameMechanics {

    private static long firstBullet = 0;
    private static final long BULLET_COOLDOWN = 300;
    private static boolean isInvincible = false;

    public static void shoot(Entity player) {
        // Null check - player might not be initialized yet
        if (player == null) {
            return;
        }

        // Delay curretTimeMillis
        long currentTime = System.currentTimeMillis();
        if (!isInvincible && currentTime - firstBullet >= BULLET_COOLDOWN) {
            FXGL.spawn("bullet", player.getX() + 120, player.getY() + 50);
            firstBullet = currentTime;
        }
    }

    public void setInvincible() {
        isInvincible = true;
    }

    public void offInvincible() {
        isInvincible = false;
    }

    public void pauseGame() {
        FXGL.getGameController().pauseEngine();
    }

    public void resumeGame() {
        FXGL.getGameController().resumeEngine();
    }

}
