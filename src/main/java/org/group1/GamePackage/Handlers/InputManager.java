package org.group1.GamePackage.Handlers;

import org.group1.GamePackage.Components.Player.PlayerComponent;
import org.group1.GamePackage.Factory.EntityFactory.EntityType;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;

import javafx.scene.input.KeyCode;

public class InputManager {
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean movingDown = false;
    private boolean movingUp = false;

    public void registerInputs() {
        moveLeft();
        moveRight();
        moveDown();
        moveUp();
        shoot();
        switchToFireBullet();
        switchToIceBullet();
    }

    public void shoot() {
        FXGL.getInput().addAction(
            new UserAction("Shoot") {
                @Override
                protected void onAction() {
                    try {
                        Entity player = FXGL.getGameWorld().getSingleton(EntityType.PLAYER);
                        GameMechanics.shoot(player);

                    } catch (Exception e) {
                        // Player isn't found yet, ignore input
                    }
                }
            },
            KeyCode.SPACE
        );
    }

    private void switchToFireBullet() {
        FXGL.getInput().addAction(new UserAction("Switch to Fire Bullet") {
            @Override
            protected void onActionBegin() {
                try {
                    Entity player = FXGL.getGameWorld().getSingleton(EntityType.PLAYER);
                    var playerComponent = player.getComponent(PlayerComponent.class);

                    if (GameMechanics.getCurrentBulletType().equals("fire_bullet")) {
                        GameMechanics.setDefaultBullet();
                        replaceHud("default_hud");
                    } else {
                        if (playerComponent.getFireBulletsStatus()) {
                            GameMechanics.setFireBullet();
                            replaceHud("fire_hud");
                        }
                    }
                } catch (Exception e) {}
            }
        },
        KeyCode.Q
        );
    }

    private void switchToIceBullet() {
        FXGL.getInput().addAction(new UserAction("Switch to Ice Bullet") {
            @Override
            protected void onActionBegin() {
                try {
                    Entity player = FXGL.getGameWorld().getSingleton(EntityType.PLAYER);
                    var playerComponent = player.getComponent(PlayerComponent.class);

                    if (GameMechanics.getCurrentBulletType().equals("ice_bullet")) {
                        GameMechanics.setDefaultBullet();
                        replaceHud("default_hud");
                    } else {
                        if (playerComponent.getIceBulletsStatus()) {
                            GameMechanics.setIceBullet();
                            replaceHud("ice_hud");
                        }
                    }
                } catch (Exception e) {}
            }
        },
        KeyCode.E
        );
    }

    public static void replaceHud(String hudName) {
        try {
            FXGL.getGameWorld().getSingleton(
                    e -> e.getComponents().stream()
                    .anyMatch(c -> c.getClass().getSimpleName().equals("SwitchBulletAnimationComponent"))
                    ).removeFromWorld();
        } catch (Exception e) {
            e.printStackTrace(); 
        }

        try {
            FXGL.spawn(hudName);
        } catch (Exception e) { 
            e.printStackTrace(); 
        }
    }   


    private void moveLeft() {
        FXGL.getInput().addAction(new UserAction("Move left") {
            @Override
            protected void onActionBegin() {
                movingLeft = true;
            }

            @Override
            protected void onActionEnd() {
                movingLeft = false;
            }
        },
        KeyCode.A
        );
    }

    private void moveRight() {
        FXGL.getInput().addAction(
                new UserAction("Move right") {

                    @Override
                    protected void onActionBegin() {
                        movingRight = true;
                    }

                    @Override
                    protected void onActionEnd() {
                        movingRight = false;
                    }
                },
                KeyCode.D
        );
    }

    private void moveUp() {
        FXGL.getInput().addAction(
            new UserAction("Move up") {

                @Override
                protected void onActionBegin() {
                    movingUp = true;

                }

                @Override
                protected void onActionEnd() {
                    movingUp = false;
                }
            },
            KeyCode.W
        );
    }

    private void moveDown() {
        FXGL.getInput().addAction(
            new UserAction("Move down") {

                @Override
                protected void onActionBegin() {
                    movingDown = true;
                }

                @Override
                protected void onActionEnd() {
                    movingDown = false;
                }
            },
            KeyCode.S
        );
    }

    public boolean isMovingUp() {
        return movingUp;
    }

    public boolean isMovingDown() {
        return movingDown;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }
}
