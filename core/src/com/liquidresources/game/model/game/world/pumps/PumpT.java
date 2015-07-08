package com.liquidresources.game.model.game.world.pumps;

public abstract class PumpT {
    public PumpT() {
        isActive = false;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public abstract void update();

    private boolean isActive;
}

