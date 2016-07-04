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

public class EnemyBase extends BaseFacade {
    public EnemyBase(final Vector2 initCoords, final BodyFactoryWrapper bodyFactoryWrapper) {
        super(RelationTypes.ENEMY, initCoords, bodyFactoryWrapper);
    }

    @Override
    protected void baseInit(final Vector2 initCoords, final BodyFactoryWrapper bodyFactoryWrapper) {
        final float buildingsPositionDelimiter = m2p(Gdx.graphics.getWidth() * 0.005f);
        final Vector2 endPosition = new Vector2(initCoords);

        endPosition.x -= m2p(Gdx.graphics.getHeight() * 0.08f);
        oilPompFacade = new OilPumpFacade(0.3f, endPosition, Animation.PlayMode.LOOP_PINGPONG, baseRelationType);
        bodyFactoryWrapper.createBody(oilPompFacade);

        endPosition.x -= oilPompFacade.getSize().x + buildingsPositionDelimiter;
        capital = new Capital(endPosition, baseRelationType);
        bodyFactoryWrapper.createBody(capital);
        capitalModel = new CapitalModel(endPosition, bodyFactoryWrapper);

        endPosition.x -= capital.getSize().x + buildingsPositionDelimiter;
        shipFactoryFacade = new ShipFactoryViewFacade(endPosition, baseRelationType);
        bodyFactoryWrapper.createBody(shipFactoryFacade);

        initCoords.x += buildingsPositionDelimiter;
        endPosition.x += shipFactoryFacade.getSize().x * 1.5f;
        Vector2 shieldEndPosition = new Vector2(endPosition.x + buildingsPositionDelimiter, endPosition.y);
        baseShield = new IonShield(initCoords, shieldEndPosition, baseRelationType);
        bodyFactoryWrapper.createBody(baseShield);
    }

    public void initEnemyAI() { // TODO testing purposes only
//        Timer.Task missileLaunchTask = new Timer.Task() {
//            @Override
//            public void run() {
//                missileLaunch(0);
//                missileLaunch(0);
//            }
//        };
//
//        Timer.Task spawnShipTask = new Timer.Task() {
//            @Override
//            public void run() {
//                createShip(0);
//            }
//        };
//
//        Timer.instance().scheduleTask(missileLaunchTask, 0, 3, 120);
//        Timer.instance().scheduleTask(spawnShipTask, 5);
//        Timer.instance().start();
    }
}
