package org.group1.GamePackage.Factory;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class InstructionPageFactory {
    public VBox showFirstPage(Label titleLabel) {
        ImageView WASD_KEYS = new ImageView(
                new Image(getClass().getResource("/assets/GIF/WASD_KEYS.gif").toExternalForm())
        );
        WASD_KEYS.setFitWidth(200);
        WASD_KEYS.setFitHeight(200);
        WASD_KEYS.setPreserveRatio(true);

        ImageView ESC_KEY = new ImageView(
                new Image(getClass().getResource("/assets/GIF/ESC_KEY.gif").toExternalForm())
        );
        ESC_KEY.setFitHeight(200);
        ESC_KEY.setFitWidth(200);
        ESC_KEY.setPreserveRatio(true);

        HBox firstGifs = new HBox(50, WASD_KEYS, ESC_KEY);
        firstGifs.setAlignment(Pos.CENTER);

        titleLabel.setText("Controls - How To");
        Label controls = new Label(
                "Controls:\n\n" +
                        "• W — Move Up\t\t   • ESC\n" +
                        "• S — Move Down\t\t —Pause—\n" +
                        "• A — Move Left\n" +
                        "• D — Move Right\n"
        );
        controls.getStyleClass().add("popup-content");
        controls.setWrapText(true);
        controls.setMaxWidth(400);

        VBox page1 = new VBox(5, firstGifs, controls);
        page1.setAlignment(Pos.CENTER);

        return page1;
    }
    public VBox showSecondPage(Label titleLabel) {
        ImageView SPACE_KEY = new ImageView(
                new Image(getClass().getResource("/assets/GIF/SPACE_KEY.gif").toExternalForm())
        );

        ImageView NORMAL_BULLET = new ImageView(
                new Image(getClass().getResource("/assets/GIF/NORMAL_BULLET.gif").toExternalForm())
        );
        SPACE_KEY.setFitWidth(200);
        SPACE_KEY.setFitHeight(200);
        SPACE_KEY.setPreserveRatio(true);

        NORMAL_BULLET.setScaleX(2);
        NORMAL_BULLET.setScaleY(2);

        HBox secondGifs= new HBox(50, SPACE_KEY, NORMAL_BULLET);
        secondGifs.setAlignment(Pos.CENTER);

        Label shoot = new Label(
                "| SPACE | Normal Bullet:\n" +
                        "• Unlimited Ammo\n" +
                        "• More damage than Ice Bullet\n" +
                        "• Hold to Keep Firing"
        );
        shoot.getStyleClass().add("popup-content");
        shoot.setWrapText(true);
        shoot.setMaxWidth(400);



        titleLabel.setText("Normal Bullet - How To");

        VBox page2 = new VBox(10, secondGifs, shoot);
        page2.setAlignment(Pos.CENTER);
        return page2;
    }
    public VBox showThirdPage(Label titleLabel) {
        ImageView E_KEY = new ImageView(
                new Image(getClass().getResource("/assets/GIF/E_KEY.gif").toExternalForm())
        );

        ImageView ICE_BULLET = new ImageView(
                new Image(getClass().getResource("/assets/GIF/ICE_BULLET.gif").toExternalForm())
        );
        E_KEY.setFitWidth(200);
        E_KEY.setFitHeight(200);
        E_KEY.setPreserveRatio(true);

        ICE_BULLET.setScaleX(1);
        ICE_BULLET.setScaleY(1);

        HBox thirdGifs = new HBox(10, E_KEY, ICE_BULLET);
        thirdGifs.setAlignment(Pos.CENTER);

        Label switchIce = new Label(
                "| E | Switch to Ice Bullet:\n" +
                        "• Pierces enemies\n" +
                        "• Slows Enemies Down\n\n" +
                        " Requirement: Ice Ammo\n" +
                        " Maximum ammo: 60"
        );
        switchIce.getStyleClass().add("popup-content");
        switchIce.setWrapText(true);
        switchIce.setMaxWidth(400);

        titleLabel.setText("Ice Bullet - How To");

        VBox page3 = new VBox(5, thirdGifs, switchIce);
        page3.setAlignment(Pos.CENTER);
        return page3;
    }
    public VBox showFourthPage(Label titleLabel) {
        ImageView Q_KEY = new ImageView(
                new Image(getClass().getResource("/assets/GIF/Q_KEY.gif").toExternalForm())
        );

        ImageView FIRE_BULLET = new ImageView(
                new Image(getClass().getResource("/assets/GIF/FIRE_BULLET.gif").toExternalForm())
        );
        Q_KEY.setFitWidth(200);
        Q_KEY.setFitHeight(200);
        Q_KEY.setPreserveRatio(true);

        FIRE_BULLET.setScaleX(1);
        FIRE_BULLET.setScaleY(1);

        HBox fourthGifs = new HBox(10, Q_KEY, FIRE_BULLET);
        fourthGifs.setAlignment(Pos.CENTER);

        Label switchFire = new Label(
                "| Q | Switch to Fire Bullet:\n" +
                        "• Pierces enemies\n" +
                        "• Gives Damage over Time Effect\n\n" +
                        " Requirement: Fire Ammo\n" +
                        " Maximum ammo: 60"
        );
        switchFire.getStyleClass().add("popup-content");
        switchFire.setWrapText(true);
        switchFire.setMaxWidth(400);

        titleLabel.setText("Fire Bullet - How To");

        VBox page4 = new VBox(5, fourthGifs, switchFire);
        page4.setAlignment(Pos.CENTER);
        return page4;
    }
    public VBox showFifthPage(Label titleLabel) {
        ImageView normal_enemy = new ImageView(
                new Image(getClass().getResource("/assets/GIF/normal_enemy.gif").toExternalForm())
        );
        Label objective = new Label(
                "Normal Enemy:\n\n" +
                        "• Gives 1 point\n" +
                        "• Chance to Drop PowerUps"
        );
        objective.getStyleClass().add("popup-content");
        objective.setWrapText(true);
        objective.setMaxWidth(400);

        titleLabel.setText("Normal Enemy - How To");

        VBox page5 = new VBox(10, normal_enemy, objective);
        page5.setAlignment(Pos.CENTER);
        return page5;
    }
    public VBox showSixthPage(Label titleLabel) {

        ImageView heart = new ImageView(
                new Image(getClass().getResource("/assets/GIF/heart.gif").toExternalForm())
        );
        heart.setFitWidth(100);
        heart.setFitHeight(100);
        heart.setPreserveRatio(true);

        ImageView boost = new ImageView(
                new Image(getClass().getResource("/assets/GIF/boost.gif").toExternalForm())
        );
        boost.setFitHeight(100);
        boost.setFitWidth(100);
        boost.setPreserveRatio(true);

        ImageView ice_potion = new ImageView(
                new Image(getClass().getResource("/assets/GIF/ice_potion.gif").toExternalForm())
        );
        ice_potion.setFitWidth(50);
        ice_potion.setFitWidth(50);
        ice_potion.setPreserveRatio(true);


        ImageView fire_potion = new ImageView(
                new Image(getClass().getResource("/assets/GIF/fire_potion.gif").toExternalForm())
        );
        fire_potion.setFitWidth(50);
        fire_potion.setFitWidth(50);
        fire_potion.setPreserveRatio(true);

        HBox fifthGifs = new HBox(20, heart, boost, ice_potion, fire_potion);
        fifthGifs.setAlignment(Pos.CENTER);

        Label powerups = new Label(
                "Power Ups:\n\n" +
                        "• HEART: Adds One Life\n\n" +
                        "• BOOST: Gives 10 Second of Boost\n\n" +
                        "• ICE POTION: Gives 10 Ice Ammo\n\n" +
                        "• FIRE POTION: Gives 10 Fire Ammo\n\n"
        );
        powerups.getStyleClass().add("popup-content");
        powerups.setWrapText(true);
        powerups.setMaxWidth(400);

        titleLabel.setText("Power Ups - How To");

        VBox page6 = new VBox(fifthGifs, powerups);
        page6.setAlignment(Pos.CENTER);
        return page6;
    }
    public VBox showSeventhPage(Label titleLabel) {
        ImageView boss = new ImageView(
                new Image(getClass().getResource("/assets/GIF/boss.gif").toExternalForm())
        );
        boss.setFitWidth(200);
        boss.setFitHeight(200);
        boss.setPreserveRatio(true);

        Label bossLabel = new Label(
                "Boss:\n" +
                        "• Defeat to Win the Game\n\n"+
                        "Phases:\n" +
                        "• IDLE • CHARGING • SHOOTING •\n"
        );
        bossLabel.getStyleClass().add("popup-content");
        bossLabel.setWrapText(true);
        bossLabel.setMaxWidth(400);

        titleLabel.setText("Boss - How To");

        VBox page7 = new VBox(10, boss, bossLabel);
        page7.setAlignment(Pos.CENTER);
        return page7;
    }
}
