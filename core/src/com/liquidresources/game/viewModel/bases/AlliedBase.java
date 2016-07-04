package com.liquidresources.game.viewModel.bases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.game.world.base.CapitalModel;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.buildings.Capital;
import com.liquidresources.game.viewModel.bodies.udata.buildings.IonShield;
import com.liquidresources.game.viewModel.bodies.udata.buildings.OilPumpFacade;
import com.liquidresources.game.viewModel.bodies.udata.buildings.ShipFactoryViewFacade;

import static com.liquidresources.game.model.common.utils.UConverter.m2p;

public class AlliedBase extends BaseFacade {
    public AlliedBase(final Vector2 initCoords, final BodyFactoryWrapper bodyFactoryWrapper) {
        super(RelationTypes.ALLY, initCoords, bodyFactoryWrapper);
    }

    @Override
    protected void baseInit(final Vector2 startPosition, final BodyFactoryWrapper bodyFactoryWrapper) {
        final float buildingsPositionDelimiter = m2p(Gdx.graphics.getWidth() * 0.005f);
        final Vector2 endPosition = new Vector2(startPosition);

        oilPompFacade = new OilPumpFacade(0.3f, startPosition, Animation.PlayMode.LOOP_PINGPONG, baseRelationType);
        bodyFactoryWrapper.createBody(oilPompFacade);

        endPosition.x += oilPompFacade.getSize().x + buildingsPositionDelimiter;
        capital = new Capital(endPosition, baseRelationType);
        bodyFactoryWrapper.createBody(capital);

        capitalModel = new CapitalModel(endPosition, bodyFactoryWrapper);

        endPosition.x += capital.getSize().x + buildingsPositionDelimiter;
        shipFactoryFacade = new ShipFactoryViewFacade(endPosition, baseRelationType);
        bodyFactoryWrapper.createBody(shipFactoryFacade);

        endPosition.x -= buildingsPositionDelimiter;
        endPosition.x -= shipFactoryFacade.getSize().x * 0.5f;
        Vector2 shieldStartPosition = new Vector2(startPosition.x - buildingsPositionDelimiter, startPosition.y);
        baseShield = new IonShield(shieldStartPosition, endPosition, baseRelationType);
        bodyFactoryWrapper.createBody(baseShield);
    }

    public CapitalModel getCapitalModel() {
        return capitalModel;
    }
}
