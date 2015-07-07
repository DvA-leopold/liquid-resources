package com.liquidresources.game.model.game.world.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.liquidresources.game.model.game.world.ships.Fighter;

public class FighterFactory extends Pool<Fighter> {
    public FighterFactory(Vector2 factoryPosition, int defaultFighterHealth) {
        this.factoryPosition = factoryPosition;
        this.defaultFighterHealth = defaultFighterHealth;
    }

    public FighterFactory(int capacity, Vector2 factoryPosition) {
        super(capacity);
        this.factoryPosition = factoryPosition;
    }

    @Override
    protected Fighter newObject() {
        return new Fighter(factoryPosition.x, factoryPosition.y, defaultFighterHealth);
    }


    private int defaultFighterHealth;
    private Vector2 factoryPosition;
}
