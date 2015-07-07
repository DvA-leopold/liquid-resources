package com.liquidresources.game.model.game.world.ships;

public class Bomber extends Ship {
    public Bomber(float defaultX, float defaultY, int defaultHealth) {
        super(defaultX, defaultY, defaultHealth);
    }

    @Override
    public void reset() {
        position.set(defaultX, defaultY);
        health = defaultHealth;
    }

    @Override
    public int doDamage() {
        return 13;
    }
}
