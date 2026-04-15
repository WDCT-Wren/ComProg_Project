package org.group1.GamePackage.Components.PowerUps;

import com.almasb.fxgl.entity.component.Component;

public class IcePowerUpComponent extends Component{
    private static final int ICE_DAMAGE = 2;
    private static final int SLOW_EFFECT = 100;
    private static final int DASH_SLOW = 950;

    public static int getDASH_SLOW() {
        return DASH_SLOW;
    }

    public static int getSLOW_EFFECT() {
        return SLOW_EFFECT;
    }

    public static int getICE_DAMAGE() {
        return ICE_DAMAGE;
    }
    
}
