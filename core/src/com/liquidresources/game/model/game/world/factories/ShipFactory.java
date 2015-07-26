package com.liquidresources.game.model.game.world.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.game.world.base.MainAI;
import com.liquidresources.game.view.drawable.ships.Bomber;
import com.liquidresources.game.view.drawable.ships.Fighter;

public class ShipFactory {
    public ShipFactory(Vector2 basePosition,
                       Vector2 shipSize,
                       int bombersDefaultHealth,
                       int fighterDefaultHealth) {
        this.basePosition = basePosition;
        this.bombersDefaultHealth = bombersDefaultHealth;
        this.fighterDefaultHealth = fighterDefaultHealth;
        this.shipSize = shipSize;
    }

    public EventListener bomberButtonListener(final BodyFactoryWrapper bodyFactoryWrapper) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(MainAI.changeOil(-122)) {
                    bodyFactoryWrapper.createBody(
                            new Bomber(basePosition, shipSize, bombersDefaultHealth), false
                    );
                }
                System.out.println(bodyFactoryWrapper.getDynamicObjects().size());
            }
        };
    }

    public EventListener fighterButtonListener(final BodyFactoryWrapper bodyFactoryWrapper) {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if(MainAI.changeOil(-55)) {
                    bodyFactoryWrapper.createBody(
                            new Fighter(basePosition, shipSize, fighterDefaultHealth), false
                    );
                    System.out.println(bodyFactoryWrapper.getDynamicObjects().size());
                }
            }
        };
    }

    public enum ShipType {
        BOMBER,
        FIGHTER
    }


    final private Vector2 basePosition, shipSize;
    private int bombersDefaultHealth, fighterDefaultHealth;
}
