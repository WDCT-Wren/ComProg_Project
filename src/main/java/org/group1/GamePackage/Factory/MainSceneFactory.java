package org.group1.GamePackage.Factory;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;

import org.group1.GamePackage.UI.MenuInterface;
import org.group1.GamePackage.UI.LoadingInterface;
import org.group1.GamePackage.UI.PauseInterface;

import java.io.IOException;


public class MainSceneFactory extends SceneFactory {

    @Override
    public FXGLMenu newMainMenu() {
        try {
            return new MenuInterface();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FXGLMenu newGameMenu() {
        try {
            return new PauseInterface();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoadingScene newLoadingScene() {
        return new LoadingInterface();
    }
}

