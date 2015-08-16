package com.liquidresources.game.viewModel.bases;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.viewModel.bodies.udata.buildings.IonShield;
import com.liquidresources.game.viewModel.bodies.udata.buildings.MainAI;
import com.liquidresources.game.viewModel.bodies.udata.buildings.OilPumpFacade;
import com.liquidresources.game.viewModel.bodies.udata.buildings.ShipFactoryViewFacade;

public class AlliedBase extends BaseFacade {
    public AlliedBase(final Vector2 initCoords,
                      final Vector2 graphicSize,
                      final BodyFactoryWrapper bodyFactoryWrapper) {
        super(initCoords, graphicSize, bodyFactoryWrapper);
    }

    @Override
    protected void baseInit(Vector2 initCoords, Vector2 graphicSize, BodyFactoryWrapper bodyFactoryWrapper) {
        final Vector2 endCoords = new Vector2(initCoords);
        final float buildingsPositionDelimiter = Gdx.graphics.getWidth() * 0.005f;

        oilPompFacade = new OilPumpFacade(0.3f, endCoords, graphicSize, Animation.PlayMode.LOOP_PINGPONG);
        bodyFactoryWrapper.createBody(oilPompFacade, true);

        endCoords.x += oilPompFacade.getSize().x + buildingsPositionDelimiter;
        mainAI = new MainAI(endCoords, graphicSize);
        bodyFactoryWrapper.createBody(mainAI, true);

        endCoords.x += mainAI.getSize().x + buildingsPositionDelimiter;
        shipFactoryFacade = new ShipFactoryViewFacade(endCoords, graphicSize);
        bodyFactoryWrapper.createBody(shipFactoryFacade, true);

        initCoords.x -= buildingsPositionDelimiter;
        endCoords.x -= graphicSize.x * 0.5f; //TODO разобраться почему 0.5
        baseShield = new IonShield(initCoords, endCoords, graphicSize);
        bodyFactoryWrapper.createBody(baseShield, true);
    }


    final private BaseType baseType = BaseType.ALLIED_BASE;
}
