package org.group1.GamePackage.Factory;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;

import org.group1.GamePackage.UI.MenuInterface;
import org.group1.GamePackage.UI.LoadingInterface;


public class MainSceneFactory extends SceneFactory {

    @Override
    public FXGLMenu newMainMenu() {
        return new MenuInterface();
    }

    @Override
    public LoadingScene newLoadingScene() {
        return new LoadingInterface();
    }
}

