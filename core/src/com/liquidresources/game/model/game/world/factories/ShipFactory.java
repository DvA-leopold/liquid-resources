package com.liquidresources.game.model.game.world.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.game.world.base.MainBaseModel;
import com.liquidresources.game.model.game.world.base.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.ships.Bomber;
import com.liquidresources.game.viewModel.bodies.udata.ships.Fighter;

public class ShipFactory {
    public ShipFactory(final Vector2 basePosition,
                       final Vector2 shipSize,
                       int bombersDefaultHealth,
                       int fighterDefaultHealth,
                       final RelationTypes relationType) {
        this.relationType = relationType;
        this.basePosition = basePosition;
        this.bombersDefaultHealth = bombersDefaultHealth;
        this.fighterDefaultHealth = fighterDefaultHealth;
        this.shipSize = shipSize;
    }

    public EventListener getShipButtonListener(final BodyFactoryWrapper bodyFactoryWrapper,
                                               final MainBaseModel mainBaseModel,
                                               ShipType shipType) {
        switch (shipType) {
            case BOMBER:
                return new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(mainBaseModel.changeOil(-122)) {
                            bodyFactoryWrapper.createBody(
                                    new Bomber(basePosition, shipSize, bombersDefaultHealth, relationType), false
                            );
                        }
                    }
                };
            case FIGHTER:
                return new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        if(mainBaseModel.changeOil(-55)) {
                            bodyFactoryWrapper.createBody(
                                    new Fighter(basePosition, shipSize, fighterDefaultHealth, relationType), false
                            );
                        }
                    }
                };
            default:
                throw new TypeNotPresentException(" default ", new Throwable());
        }
    }

    public enum ShipType {
        BOMBER,
        FIGHTER
    }


    final private Vector2 basePosition, shipSize;
    private int bombersDefaultHealth, fighterDefaultHealth;

    final private RelationTypes relationType;
}
