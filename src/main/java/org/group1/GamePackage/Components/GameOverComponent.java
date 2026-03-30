package org.group1.GamePackage.Components;

import org.group1.GamePackage.Handlers.GameMechanics;
import com.almasb.fxgl.entity.component.Component;

public class GameOverComponent extends Component {

    GameMechanics gameMechanics = new GameMechanics();

    public void gameOver() {
        gameMechanics.pauseGame();
    } 
}
