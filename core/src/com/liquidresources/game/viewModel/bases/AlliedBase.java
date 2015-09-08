package com.liquidresources.game.viewModel.bases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.buildings.IonShield;
import com.liquidresources.game.viewModel.bodies.udata.buildings.Capital;
import com.liquidresources.game.viewModel.bodies.udata.buildings.OilPumpFacade;
import com.liquidresources.game.viewModel.bodies.udata.buildings.ShipFactoryViewFacade;

public class AlliedBase extends BaseFacade {
    public AlliedBase(final Vector2 initCoords,
                      final Vector2 graphicSize,
                      final BodyFactoryWrapper bodyFactoryWrapper) {
        super(initCoords, graphicSize, bodyFactoryWrapper);
    }

    @Override
    protected void baseInit(final Vector2 initCoords,
                            final Vector2 graphicSize,
                            final BodyFactoryWrapper bodyFactoryWrapper) {
        final Vector2 endCoords = new Vector2(initCoords);
        final float buildingsPositionDelimiter = Gdx.graphics.getWidth() * 0.005f;

        oilPompFacade = new OilPumpFacade(0.3f, endCoords, graphicSize, Animation.PlayMode.LOOP_PINGPONG, RelationTypes.ALLY);
        bodyFactoryWrapper.createBody(oilPompFacade, true);

        endCoords.x += oilPompFacade.getSize().x + buildingsPositionDelimiter;
        capital = new Capital(endCoords, graphicSize, RelationTypes.ALLY);
        bodyFactoryWrapper.createBody(capital, true);

        endCoords.x += capital.getSize().x + buildingsPositionDelimiter;
        shipFactoryFacade = new ShipFactoryViewFacade(endCoords, graphicSize, RelationTypes.ALLY);
        bodyFactoryWrapper.createBody(shipFactoryFacade, true);

        initCoords.x -= buildingsPositionDelimiter;
        endCoords.x -= graphicSize.x * 0.5f; //TODO разобраться почему 0.5
        baseShield = new IonShield(initCoords, endCoords, graphicSize, RelationTypes.ALLY);
        bodyFactoryWrapper.createBody(baseShield, true);
    }

    public void setShieldActive(boolean isActive) {
        baseShield.setActive(isActive);
    }
}
