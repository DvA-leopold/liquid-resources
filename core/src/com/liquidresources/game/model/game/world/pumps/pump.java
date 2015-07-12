package com.liquidresources.game.model.game.world.pumps;

public abstract class Pump {
    public Pump(float resourcesObtainSpeed) {
        this.timer = 0f;
        this.resourcesObtainSpeed = resourcesObtainSpeed;
        stationOn = true;
    }

    public abstract void upgrade();

    public short getResources(float delta) {
        if (stationOn) {
            timer += delta;
            if (resourcesObtainSpeed < timer) {
                timer = 0f;
                return 1;
            }
        }
        return 0;
    }

    public boolean isStationOn() {
        return stationOn;
    }


    private float timer;
    private float resourcesObtainSpeed;
    protected boolean stationOn;
}
