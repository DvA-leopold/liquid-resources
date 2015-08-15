package com.liquidresources.game.viewModel.bases;

import com.badlogic.gdx.math.Vector2;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.viewModel.bodies.udata.buildings.IonShield;
import com.liquidresources.game.viewModel.bodies.udata.buildings.MainAI;
import com.liquidresources.game.viewModel.bodies.udata.buildings.OilPumpFacade;
import com.liquidresources.game.viewModel.bodies.udata.buildings.ShipFactoryViewFacade;

public abstract class BaseFacade {
    public BaseFacade(final Vector2 initCoords,
                      final Vector2 graphicSize,
                      final BodyFactoryWrapper bodyFactoryWrapper) {
        baseInit(initCoords, graphicSize, bodyFactoryWrapper);
    }

    public void show() {
        //TODO разобраться почему не работают несколько эффектов сразу
        shipFactoryFacade.getSmokeParticles().startEffect();
    }

    public void hide() {
        shipFactoryFacade.getSmokeParticles().resetEffect();
        shipFactoryFacade.getSmokeParticles().stopEffect();
    }

    public Vector2 getShipFactoryPosition() {
        return shipFactoryFacade.getShipFactoryPosition();
    }

    public Vector2 getMainAIPosition() {
        return mainAI.getPosition();
    }

    protected abstract void baseInit(Vector2 initCoords,
                                     Vector2 graphicSize,
                                     BodyFactoryWrapper bodyFactoryWrapper);

    public enum BaseType {
        ALLIED_BASE,
        ENEMY_BASE
    }


    protected IonShield baseShield;
    protected OilPumpFacade oilPompFacade;
    protected ShipFactoryViewFacade shipFactoryFacade;
    protected MainAI mainAI;
}