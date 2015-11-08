package com.liquidresources.game.viewModel.bases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.buildings.Capital;
import com.liquidresources.game.viewModel.bodies.udata.buildings.IonShield;
import com.liquidresources.game.viewModel.bodies.udata.buildings.OilPumpFacade;
import com.liquidresources.game.viewModel.bodies.udata.buildings.ShipFactoryViewFacade;

import static com.liquidresources.game.view.UConverter.M2P;

public class AlliedBase extends BaseFacade {
    public AlliedBase(final Vector2 initCoords,
                      final Vector2 graphicSize,
                      final BodyFactoryWrapper bodyFactoryWrapper) {
        super(initCoords, graphicSize, bodyFactoryWrapper);
    }

    @Override
    protected void baseInit(final Vector2 startPosition,
                            final Vector2 graphicSize,
                            final BodyFactoryWrapper bodyFactoryWrapper) {
        final float buildingsPositionDelimiter = M2P(Gdx.graphics.getWidth() * 0.005f);
        final Vector2 endPosition = new Vector2(startPosition);

        oilPompFacade = new OilPumpFacade(0.3f, startPosition, graphicSize, Animation.PlayMode.LOOP_PINGPONG, RelationTypes.ALLY);
        bodyFactoryWrapper.createBody(oilPompFacade, true);

        endPosition.x += oilPompFacade.getSize().x + buildingsPositionDelimiter;
        capital = new Capital(endPosition, graphicSize, RelationTypes.ALLY);
        bodyFactoryWrapper.createBody(capital, true);

        endPosition.x += capital.getSize().x + buildingsPositionDelimiter;
        shipFactoryFacade = new ShipFactoryViewFacade(endPosition, graphicSize, RelationTypes.ALLY);
        bodyFactoryWrapper.createBody(shipFactoryFacade, true);

        endPosition.x -= buildingsPositionDelimiter;
        endPosition.x -= graphicSize.x * 0.5f; //TODO 0.5 ??
        Vector2 shieldStartPosition = new Vector2(startPosition.x - buildingsPositionDelimiter, startPosition.y);
        baseShield = new IonShield(shieldStartPosition, endPosition, graphicSize, RelationTypes.ALLY);
        bodyFactoryWrapper.createBody(baseShield, true);
    }

    public void setShieldActive(boolean isActive) {
        baseShield.setActive(isActive);
    }
}
