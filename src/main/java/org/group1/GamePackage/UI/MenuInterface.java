package org.group1.GamePackage.UI;

import org.group1.GamePackage.Handlers.GameMechanics;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class MenuInterface extends FXGLMenu {

    public MenuInterface() {
        super(MenuType.MAIN_MENU);

        Button pressPlay = new Button("Play");
        pressPlay.setFont(Font.font(32));
        pressPlay.setPrefWidth(200);
        pressPlay.setPrefHeight(60);

        pressPlay.setOnAction(click ->
                GameMechanics.restartGame()
                );

        VBox menuLayout = new VBox(20, pressPlay);
        menuLayout.setAlignment(Pos.CENTER);
        menuLayout.setPrefWidth(FXGL.getAppWidth());
        menuLayout.setPrefHeight(FXGL.getAppHeight());

        getContentRoot().getChildren().add(menuLayout);
    }

}
