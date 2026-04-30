package org.group1.GamePackage.Components.Enemy;

import org.group1.GamePackage.Components.PowerUps.FirePowerUpComponent;
import org.group1.GamePackage.Components.PowerUps.IcePowerUpComponent;
import org.group1.GamePackage.Components.UI.GameOverComponent;
import org.group1.GamePackage.Factory.EntityFactory.EntityType;
import org.group1.GamePackage.Handlers.BossLevelManager;

import com.almasb.fxgl.core.math.FXGLMath;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.time.TimerAction;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.util.Duration;

public class BossComponent extends Component {

    // State of the BOSS initial state IDLE
    private enum State {
        IDLE,
        SHOOTING,
        CHARGING,
        RECOVERING,
        DEAD
    }

    private static int BOSS_HEALTH = 1000;
    protected int CURRENT_HEALTH;

    private State state = State.IDLE;
    protected TimerAction shootInterval;

    // Initial position of BOSS set by FXGL.spawn
    protected double INITIAL_BOSS_Y;
    protected double INITIAL_BOSS_X;

    /*
     * Y movement SPEED and MOVE_RANGE
     * Initial DIRECTION is 1 (positive Y movement)
     * RANDOM_DIRECTION_CHANCE: chance to change determined by randomBoolean
     */
    protected double SPEED_Y = 300;
    protected double MOVE_RANGE = 150;
    protected double DIRECTION = 1;
    protected double RANDOM_DIRECTION_CHANCE = 0.01;

    /*
     * Charge Attack Variables
     * CHARGE_SPEED: How fast move to player
     * RECOVER_SPEED: How fast till boss goes back to initial X position
     * CHARGE_CHANCE: chance to attack determined by randomBoolean
     * CHARGE_TARGET_X CHARGE_TARGET_Y: player position
     */
    protected double CHARGE_SPEED = 1200;

    private static final double RECOVER_SPEED = 800;
    private static final double CHARGE_CHANCE = 0.005;
    protected double CHARGE_TARGET_X;
    protected double CHARGE_TARGET_Y;
    protected final double SLOW_DURATION = 8;
    protected final int SLOW_SPEED_Y = 300;
    protected final int SLOW_CHARGE_SPEED = 1200;
    protected final double BURN_RATE = 1;
    private double BURN_DURATION = 5;

    private static final double FLASH_DURATION = 0.3; // seconds
    private static final double FLASH_INTERVAL = 0.1; // seconds between flashes
    private boolean visible = true;

    private static final double SHOOT_CHANCE = 0.003; // 0.3% per frame from IDLE
    protected static final double SHOOT_DURATION = 4.0; // seconds spent in SHOOTING state

    protected double BOSS_SHOOTING_RATE = 0.5;
    protected double SLOW_SHOOTING_RATE = 0.5;

    private final AnimatedTexture texture;
    private final AnimationChannel idleAnimation;
    private final AnimationChannel chargingAnimation;
    private final AnimationChannel recoveringAnimation;
    private final AnimationChannel deathAnimation;

    private double normalAnimationSpeed = 0.3;
    private double slowAnimationSpeed = 0.3;
    private double normalIdleSpeed= 0.7;
    private double slowIdleSpeed = 0.7;

    public BossComponent() {
        Image idle_sprite = FXGL.image("boss_idle_sprite.png");
        idleAnimation = new AnimationChannel(
                idle_sprite,
                5,
                400,
                400,
                Duration.seconds(normalIdleSpeed),
                0,
                4
                );

        Image charging_sprite = FXGL.image("boss_charging_sprite.png");
        chargingAnimation = new AnimationChannel(
                charging_sprite,
                5,
                400,
                400,
                Duration.seconds(normalAnimationSpeed),
                0,
                9
                );

        Image recovering_sprite = FXGL.image("boss_recovering_sprite.png");
        recoveringAnimation = new AnimationChannel(
                recovering_sprite,
                5,
                400,
                400,
                Duration.seconds(normalAnimationSpeed),
                0,
                4
                );

        Image death_sprite = FXGL.image("boss_death.png");
        deathAnimation = new AnimationChannel(
                death_sprite,
                2,
                400,
                400,
                Duration.seconds(0.6),
                0,
                7
                );

        texture = new AnimatedTexture(idleAnimation);
        texture.loopAnimationChannel(idleAnimation);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        // When boss spawns, get the position and initialize it
        INITIAL_BOSS_Y = entity.getY();
        INITIAL_BOSS_X = entity.getX();

        CURRENT_HEALTH = BOSS_HEALTH;

        // Picks a random Y movement direction
        DIRECTION = FXGLMath.randomBoolean() ? 1 : -1;
    }

    @Override
    public void onUpdate(double update) {
        System.out.println(entity.getY());
        // switch states that updates all the time by overriding FXGL onUpdate(double)
        switch (state) {
            case IDLE -> onIdle(update);
            case SHOOTING -> shoot(update);
            case CHARGING -> currentlyCharging(update);
            case RECOVERING -> currentlyRecovering(update);
            case DEAD -> {}
        }
    }

    private void setState(State newState) {
        if (state == newState)
            return;

        state = newState;

        switch (newState) {
            case IDLE -> texture.loopAnimationChannel(idleAnimation);
            case SHOOTING -> texture.loopAnimationChannel(idleAnimation);
            case CHARGING -> texture.loopAnimationChannel(chargingAnimation);
            case RECOVERING -> texture.playAnimationChannel(recoveringAnimation);
            case DEAD -> texture.playAnimationChannel(deathAnimation);
        }
    }

    /**
     * Tracks the player's position
     */
    private void trackPlayer() {
        var player = FXGL.getGameWorld()
                .getSingleton(e -> e.isType(EntityType.PLAYER));

        CHARGE_TARGET_X = player.getX();
        CHARGE_TARGET_Y = player.getY();
    }

    /**
     * BOSS MOVEMENT LOGIC
     * <br>
     * <br>
     * if INITIAL_BOSS_Y + MOVE_RANGE is greater than the current Y
     * set it to INITIAL_BOSS_Y + MOVE_RANGE
     * and CHANGE THE DIRECTION by multiplying to -1
     * else if it's less than INITIAL_BOSS_Y - MOVE_RANGE
     * set it to INITIAL_BOSS_Y - MOVE_RANGE
     * change the direction again
     */
    private void bossMovement(double update) {
        entity.translateY(SPEED_Y * DIRECTION * update);

        if (entity.getY() > INITIAL_BOSS_Y + MOVE_RANGE) {
            entity.setY(INITIAL_BOSS_Y + MOVE_RANGE);
            DIRECTION *= -1;
        } else if (entity.getY() < INITIAL_BOSS_Y - MOVE_RANGE) {
            entity.setY(INITIAL_BOSS_Y - MOVE_RANGE);
            DIRECTION *= -1;
        }

        // Randomly change directions
        if (FXGLMath.randomBoolean(RANDOM_DIRECTION_CHANCE)) {
            DIRECTION *= -1;
        }
    }

    // IDLE logic, with the update parameter to constantly translateY
    private void onIdle(double update) {
        bossMovement(update);

        // Randomly charge or shoot
        if (FXGLMath.randomBoolean(CHARGE_CHANCE)) {
            chargeAttack();
        } else if (FXGLMath.randomBoolean(SHOOT_CHANCE)) {
            enterShootingState();
        }
    }

    // Maintains the boss's movement while in shooting phase while tracking the
    // player's position
    private void shoot(double update) {
        trackPlayer();
        bossMovement(update);
    }

    //Spawns laser
    protected void shootLaser() {
        FXGL.spawn(getLaser(), INITIAL_BOSS_X, CHARGE_TARGET_Y);
    }

    // Transitions boss into SHOOTING state, fires lasers on interval, then returns
    // to IDLE
    private void enterShootingState() {
        setState(State.SHOOTING);

        shootInterval = FXGL.getGameTimer().runAtInterval(() -> {
            double laserBounds = (double) (FXGL.getAppHeight() * 2) / 3;
            shootLaser();
        }, Duration.seconds(BOSS_SHOOTING_RATE));

        FXGL.getGameTimer().runOnceAfter(() -> {
            shootInterval.expire();
            setState(State.IDLE);
        }, Duration.seconds(SHOOT_DURATION));
    }

    /*
     * Charge attack logic
     * doesnt have the update parameter because this initializes the CHARGING state
     * gets the play current position
     */
    private void chargeAttack() {
        trackPlayer();
        setState(State.CHARGING);
    }

    /*
     * if state = CHARGING -> run this method
     * Point2D position: gets the current position of boss
     * Point2D target: gets the current position of player
     * Point2D direction: subtracts the target to postion
     * Essentially creates a vector from position to target or idk
     * I think of it like if boss X = 700 & boss Y = 160
     * then player X = 100, player Y = 200
     * subtracts (100, 200) to (700, 160) = (-600, 40) = 601.33... < vector
     * magnitude
     * .normalize() just divides by the magnitude so it gets the right distance to
     * player
     * (-600 / 601.33, 40 / 601.33) = (-0.998, 0.066) this is the distance to player
     * = direction
     */
    private void currentlyCharging(double update) {
        Point2D position = new Point2D(entity.getX(), entity.getY());
        Point2D target = new Point2D(CHARGE_TARGET_X, CHARGE_TARGET_Y);
        Point2D direction = target.subtract(position).normalize();

        // translate the direction multiplied by CHARGE_SPEED while onUpdate
        entity.translate(direction.multiply(CHARGE_SPEED * update));

        // if position reaches the target, set position to the CHARGE_TARGET
        if (position.distance(target) < CHARGE_SPEED * update) {
            // and then initialize RECOVERING state
            setState(State.RECOVERING);
        }
    }

    /*
     * RECOVERING logic
     * gets the initial boss position 
     * and goes back to it
     * returns to IDLE state
     * else keep moving back
     */
    private void currentlyRecovering(double update) {
        Point2D position = new Point2D(entity.getX(), entity.getY());
        Point2D target = new Point2D(INITIAL_BOSS_X, INITIAL_BOSS_Y);

        double distance = position.distance(target);
        double step = RECOVER_SPEED * update;

        if (distance <= step) {
            entity.setX(INITIAL_BOSS_X);
            entity.setY(INITIAL_BOSS_Y);
            setState(State.IDLE);
        } else {
            Point2D direction = target.subtract(position).normalize();
            entity.translate(direction.multiply(step));
        }
    }

    public void takeDamage(int damage) {
        // guarding runtime errors lel
        if (entity == null || !entity.isActive() || state == State.DEAD)
            return;

        CURRENT_HEALTH -= damage;

        // UPDATES HEALTH BAR
        var healthBar = BossLevelManager.getHealthBar();
        if (healthBar != null)
            healthBar.currentValueProperty().setValue(CURRENT_HEALTH);

        if (dead()) {
            triggerDeath();
            // actually win the game, removed from bossLevelManager
        }
    }

    public boolean dead() {
        return CURRENT_HEALTH <= 0;
    }

    private void triggerDeath() {
        // Cancel any active shoot interval so no more lasers are fired
        if (shootInterval != null && !shootInterval.isExpired()) {
            shootInterval.expire();
        }
 
        setState(State.DEAD);

        FXGL.getGameTimer().runOnceAfter(() -> {
            if (entity != null && entity.isActive()) {
                entity.removeFromWorld();
            }
            onDeathComplete();
        }, Duration.seconds(0.6));
    }
 
    protected void onDeathComplete() {
        GameOverComponent.winGame();
    }


    public void slowEffect() {
        SPEED_Y = IcePowerUpComponent.getSLOW_EFFECT();
        CHARGE_SPEED = IcePowerUpComponent.getDASH_SLOW();
        BOSS_SHOOTING_RATE = IcePowerUpComponent.getSLOW_SHOOTING_EFFECT();
        normalAnimationSpeed = IcePowerUpComponent.getSLOW_ANIMATION_EFFECT();
        normalIdleSpeed = IcePowerUpComponent.getSLOW_ANIMATION_EFFECT();

        // Slow shootInterval when already enterShootingState logic
        if (shootInterval != null && !shootInterval.isExpired()) {
            shootInterval.expire();
            shootInterval = FXGL.getGameTimer().runAtInterval(() -> {
                double y = CHARGE_TARGET_Y;
                FXGL.spawn(getLaser(), INITIAL_BOSS_X, y);
            }, Duration.seconds(BOSS_SHOOTING_RATE));
        }

        FXGL.getGameTimer().runOnceAfter(() -> {
            SPEED_Y = SLOW_SPEED_Y;
            CHARGE_SPEED = SLOW_CHARGE_SPEED;
            BOSS_SHOOTING_RATE = SLOW_SHOOTING_RATE;
            normalAnimationSpeed = slowAnimationSpeed;
            normalIdleSpeed = slowIdleSpeed;
        }, Duration.seconds(SLOW_DURATION));
    }

    public void burnEffect() {
        var burnTask = FXGL.getGameTimer().runAtInterval(() -> {
            // if theres no entity dont do this
            if (entity == null || !entity.isActive())
                return;
            takeDamage(FirePowerUpComponent.getFIRE_DAMAGE());
        }, Duration.seconds(BURN_RATE));

        FXGL.getGameTimer().runOnceAfter(burnTask::expire, Duration.seconds(BURN_DURATION));
    }

    private void triggerDamage() {
        var flashTask = FXGL.getGameTimer().runAtInterval(() -> {
            visible = !visible;
            entity.getViewComponent().setOpacity(visible ? 1.0 : 0.3);
        }, Duration.seconds(FLASH_INTERVAL));

        FXGL.getGameTimer().runOnceAfter(() -> {
            flashTask.expire();
            entity.getViewComponent().setOpacity(1.0);
        }, Duration.seconds(FLASH_DURATION));
    }

    public static int getBOSS_HEALTH() {
        return BOSS_HEALTH;
    }

    protected String getLaser() {
        return "lasers";
    }
}
