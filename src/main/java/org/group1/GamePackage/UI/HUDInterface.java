package org.group1.GamePackage.UI;

import org.group1.GamePackage.Components.CupHeadComponent;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class HUDInterface {

    public Text displayHealth (Entity player, Text hpText) {
        String healthMessage = "HP: " + player.getComponent(CupHeadComponent.class).getHealth();
        hpText = FXGL.getUIFactoryService().newText(healthMessage, Color.AQUAMARINE, 24);
        hpText.setTranslateX(50);
        hpText.setTranslateY(50);
        FXGL.addUINode(hpText);
        return hpText;
    }

    public Text displayBoostLevel (Entity player, Text boostText) {
        String boostMessage = "Boost: " + player.getComponent(CupHeadComponent.class).getBoostLevel();
        boostText = FXGL.getUIFactoryService().newText(boostMessage, Color.AQUAMARINE, 24);
        boostText.setTranslateX(50);
        boostText.setTranslateY(80);
        FXGL.addUINode(boostText);
        return boostText;
    }

    public Text displayLives (Entity player, Text livesText) {
        String livesMessage = "Lives: " + player.getComponent(CupHeadComponent.class).getLives();
        livesText = FXGL.getUIFactoryService().newText(livesMessage, Color.AQUAMARINE, 24);
        livesText.setTranslateX(50);
        livesText.setTranslateY(110);
        FXGL.addUINode(livesText);
        return livesText;
    }

}
