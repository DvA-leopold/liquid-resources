package com.liquidresources.game.model.game.world.ships;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;

public abstract class Ship implements Pool.Poolable {
    public Ship(float defaultX, float defaultY, int defaultHealth) {
        this.defaultX = defaultX;
        this.defaultY = defaultY;
        position = new Vector2(defaultX, defaultY);
        health = this.defaultHealth = defaultHealth;
    }

    public void moveShip(float x, float y) {
        position.add(x, y);
    }

    public abstract int doDamage();


    protected int health;
    protected Vector2 position;

    protected int defaultHealth;
    final protected float defaultX, defaultY;
}
