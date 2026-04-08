package org.group1.GamePackage.Components.UI;

import org.group1.GamePackage.Handlers.GameMechanics;
import com.almasb.fxgl.entity.component.Component;

public class GameOverComponent extends Component {

    // TODO: GUI OR LOGIC OF WHATEVER HAPPENS HERE

    public void gameOver() {
        GameMechanics.pauseGame();
    }

    public static void winGame() {
        GameMechanics.pauseGame();
    }
}
