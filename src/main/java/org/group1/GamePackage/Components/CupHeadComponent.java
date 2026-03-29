package org.group1.GamePackage.Components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import javafx.util.Duration;


public class CupHeadComponent extends Component {
    private static final double INVINSIBLE_DURATION = 1.5; // seconds
    private static final double FLASH_INTERVAL = 0.1; // seconds between flashes

    private int lives = 9;
    private int boostLevel = 10;

    private boolean isInvincible = false;
    private boolean visible = true;

    public void takeDamage () {
        if (!isInvincible && lives > 0) {
            lives -= 1;
        }
        triggerInvincibility();
    }

    public void increaseLives() {
        if (lives <9) {
            lives += 1;
        }
    }

    private void triggerInvincibility() {
        isInvincible = true;

        var flashTask = FXGL.getGameTimer().runAtInterval(() -> {
            visible = !visible;
            entity.getViewComponent().setOpacity(visible ? 1.0 : 0.3);
        }, Duration.seconds(FLASH_INTERVAL));

        FXGL.getGameTimer().runOnceAfter(() -> {
            flashTask.expire();
            isInvincible = false;
            entity.getViewComponent().setOpacity(1.0);
        }, Duration.seconds(INVINSIBLE_DURATION));
    }

    public boolean isInvincible() {return isInvincible;}

    public void decreaseBoostLevel (int amount) {
        if (boostLevel < 10) boostLevel-=amount;
    }

    public int getLives() {
        return lives;
    }

    public int getBoostLevel() {
        return boostLevel;
    }

}
