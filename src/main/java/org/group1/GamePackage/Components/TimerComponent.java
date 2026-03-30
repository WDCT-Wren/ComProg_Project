package org.group1.GamePackage.Components;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

public class TimerComponent extends Component {
    // Game Timer
    private final IntegerProperty timeLeft = new SimpleIntegerProperty(120); //2 minutes

    public void initTimer() {
        FXGL.getGameTimer().runAtInterval(() -> {
            timeLeft.set(timeLeft.get() - 1);

            if (timeLeft.get() <= 0) {
                onTimerEnds();
            }
        }, Duration.seconds(1)); // Counts down to 1
    }

    public IntegerProperty timeLeftProperty() {return timeLeft;}

    private void onTimerEnds() {
        //TODO: set timer event
    }
}
