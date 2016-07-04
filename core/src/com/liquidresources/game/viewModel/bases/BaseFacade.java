package com.liquidresources.game.viewModel.bases;

import com.badlogic.gdx.math.Vector2;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.game.world.base.CapitalModel;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.buildings.Capital;
import com.liquidresources.game.viewModel.bodies.udata.buildings.IonShield;
import com.liquidresources.game.viewModel.bodies.udata.buildings.OilPumpFacade;
import com.liquidresources.game.viewModel.bodies.udata.buildings.ShipFactoryViewFacade;

public abstract class BaseFacade {  // TODO make universal base facade
    BaseFacade(RelationTypes baseRelationType, final Vector2 initCoords, final BodyFactoryWrapper bodyFactoryWrapper) {
        this.baseRelationType = baseRelationType;
        baseInit(initCoords, bodyFactoryWrapper);
    }

    public void show() {
        shipFactoryFacade.getSmokeParticles().startEffect();
    }

    public void hide() {
        shipFactoryFacade.getSmokeParticles().resetEffect();
        shipFactoryFacade.getSmokeParticles().stopEffect();
    }

    public void missileLaunch(int missileCost) {
        if (capitalModel.changeOil(missileCost)) {
            capitalModel.missileLaunch(baseRelationType);
        }
    }

    public void switchIONShield() {
        capitalModel.switchIonShield();
    }

    public long getOil() {
        return capitalModel.getOilBarrels();
    }

    public long getWater() {
        return capitalModel.getWaterBarrels();
    }

    protected abstract void baseInit(final Vector2 initCoords, final BodyFactoryWrapper bodyFactoryWrapper);


    IonShield baseShield;
    OilPumpFacade oilPompFacade;
    ShipFactoryViewFacade shipFactoryFacade;
    Capital capital;
    CapitalModel capitalModel;

    RelationTypes baseRelationType;
}
