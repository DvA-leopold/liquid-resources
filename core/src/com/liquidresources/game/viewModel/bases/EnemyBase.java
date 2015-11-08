package com.liquidresources.game.viewModel.bases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.view.UConverter;
import com.liquidresources.game.viewModel.bodies.udata.buildings.Capital;
import com.liquidresources.game.viewModel.bodies.udata.buildings.IonShield;
import com.liquidresources.game.viewModel.bodies.udata.buildings.OilPumpFacade;
import com.liquidresources.game.viewModel.bodies.udata.buildings.ShipFactoryViewFacade;

public class EnemyBase extends BaseFacade {
    public EnemyBase(final Vector2 initCoords,
                     final Vector2 graphicSize,
                     final BodyFactoryWrapper bodyFactoryWrapper) {
        super(initCoords, graphicSize, bodyFactoryWrapper);
    }

    @Override
    protected void baseInit(final Vector2 initCoords,
                            final Vector2 graphicSize,
                            final BodyFactoryWrapper bodyFactoryWrapper) {
        final float buildingsPositionDelimiter = UConverter.M2P(Gdx.graphics.getWidth() * 0.005f);
        final Vector2 endPosition = new Vector2(initCoords);

        endPosition.x -= graphicSize.x;
        oilPompFacade = new OilPumpFacade(0.3f, endPosition, graphicSize, Animation.PlayMode.LOOP_PINGPONG, RelationTypes.ENEMY);
        bodyFactoryWrapper.createBody(oilPompFacade, true);

        endPosition.x -= oilPompFacade.getSize().x + buildingsPositionDelimiter;
        capital = new Capital(endPosition, graphicSize, RelationTypes.ENEMY);
        bodyFactoryWrapper.createBody(capital, true);

        endPosition.x -= capital.getSize().x + buildingsPositionDelimiter;
        shipFactoryFacade = new ShipFactoryViewFacade(endPosition, graphicSize, RelationTypes.ENEMY);
        bodyFactoryWrapper.createBody(shipFactoryFacade, true);

        initCoords.x += buildingsPositionDelimiter;
        endPosition.x += graphicSize.x * 1.5f; //TODO 1.5 ??
        Vector2 shieldEndPosition = new Vector2(endPosition.x + buildingsPositionDelimiter, endPosition.y);
        baseShield = new IonShield(initCoords, shieldEndPosition, graphicSize, RelationTypes.ENEMY);
        bodyFactoryWrapper.createBody(baseShield, true);
    }
}
