package org.group1.GamePackage.UI;

import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.ui.ProgressBar;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class LoadingInterface extends LoadingScene {

    public LoadingInterface() {
        ProgressBar loading = new ProgressBar();
        loading.setMaxValue(100);
        loading.setCurrentValue(50);

        Text loadingText = new Text("Loading...");
        loadingText.setFill(Color.BLACK);
        loadingText.setFont(Font.font(36));


        VBox loadingLayout = new VBox(loadingText, loading);
        loadingLayout.setAlignment(Pos.CENTER);
        loadingLayout.setPrefWidth(FXGL.getAppWidth());
        loadingLayout.setPrefHeight(FXGL.getAppHeight());

        getContentRoot().getChildren().add(loadingLayout);
    }
}
