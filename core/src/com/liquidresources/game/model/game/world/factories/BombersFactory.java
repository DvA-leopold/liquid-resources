package com.liquidresources.game.model.game.world.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Pool;
import com.liquidresources.game.model.game.world.ships.Bomber;

public class BombersFactory extends Pool<Bomber> {
    public BombersFactory(Vector2 factoryPosition, int bombersDefaultHealth) {
        this.factoryPosition = factoryPosition;
        this.bombersDefaultHealth = bombersDefaultHealth;
    }

    public BombersFactory(int capacity) {
        super(capacity);
    }

    @Override
    protected Bomber newObject() {
        return new Bomber(factoryPosition.x, factoryPosition.y, bombersDefaultHealth);
        //this.obtain(); достает свободный объект из пула
        //this.free(bomber); кладет объект в пул
    }


    private int bombersDefaultHealth;
    private Vector2 factoryPosition;
}
