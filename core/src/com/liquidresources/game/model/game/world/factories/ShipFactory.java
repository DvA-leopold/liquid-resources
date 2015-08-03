package com.liquidresources.game.model.game.world.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.game.world.base.MainAIModel;
import com.liquidresources.game.viewModel.bodies.udata.ships.Bomber;
import com.liquidresources.game.viewModel.bodies.udata.ships.Fighter;

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

    public EventListener getShipButtonListener(final BodyFactoryWrapper bodyFactoryWrapper, ShipType shipType) {
        switch (shipType) {
            case BOMBER:
                return new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(MainAIModel.changeOil(-122)) {
                            bodyFactoryWrapper.createBody(
                                    new Bomber(basePosition, shipSize, bombersDefaultHealth), false
                            );
                        }
                        System.out.println(bodyFactoryWrapper.getDynamicObjects().size());
                    }
                };

            case FIGHTER:
                return new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(MainAIModel.changeOil(-55)) {
                            bodyFactoryWrapper.createBody(
                                    new Fighter(basePosition, shipSize, fighterDefaultHealth), false
                            );
                            System.out.println(bodyFactoryWrapper.getDynamicObjects().size());
                        }
                    }
                };
        }
        System.err.println("such ship do not exist");
        return null;
    }

    public enum ShipType {
        BOMBER,
        FIGHTER
    }


    final private Vector2 basePosition, shipSize;
    private int bombersDefaultHealth, fighterDefaultHealth;
}
