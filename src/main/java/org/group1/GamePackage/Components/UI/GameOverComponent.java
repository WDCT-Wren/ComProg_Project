package org.group1.GamePackage.Components.UI;

import org.group1.GamePackage.Components.Player.PlayerComponent;
import org.group1.GamePackage.Handlers.BossLevelManager;
import org.group1.GamePackage.Handlers.GameMechanics;
import com.almasb.fxgl.entity.component.Component;
import org.group1.GamePackage.Music.AudioManager;

public class GameOverComponent extends Component {
    private TimerComponent timerComponent;

    // TODO: GUI OR LOGIC OF WHATEVER HAPPENS HERE

    public void gameOver() {
        GameMechanics.pauseGame();
    }

    public static void winGame() {
        GameMechanics.pauseGame();
    }

    @Override
    public void onAdded() {
        timerComponent = entity.getComponent(TimerComponent.class);
        BossLevelManager bossLevelManager = entity.getComponent(BossLevelManager.class);
        timerComponent.initTimer();
        AudioManager audioManager = new AudioManager();
        PlayerComponent player = entity.getComponent(PlayerComponent.class);
    }
}
