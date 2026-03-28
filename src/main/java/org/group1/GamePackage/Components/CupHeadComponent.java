package org.group1.GamePackage.Components;

import com.almasb.fxgl.entity.component.Component;

public class CupHeadComponent extends Component {
    private int health = 100;
    private int lives = 3;
    private int boostLevel = 10;
    private boolean isInvinsible = false;

    public void takeDamage (int damageAmount) {
        if (!isInvinsible) {
            health-=damageAmount;
        }
    }

    public void decreaseBoostLevel (int amount) {
        if (boostLevel < 10) boostLevel-=amount;
    }

    public void heal (int healAmount) {
        if (health < 100) health += healAmount; 
    }

    public int getHealth() {
        return health;
    }

    public int getLives() {
        return lives;
    }

    public int getBoostLevel() {
        return boostLevel;
    }

}
