package com.liquidresources.game.viewModel.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.GameWorldModel;
import com.liquidresources.game.model.music.manager.MusicManager;
import com.liquidresources.game.view.GameRenderer;
import com.liquidresources.game.viewModel.bases.AlliedBase;
import com.liquidresources.game.viewModel.bases.EnemyBase;
import com.liquidresources.game.viewModel.bodies.udata.bariers.Ground;
import com.liquidresources.game.viewModel.bodies.udata.buildings.Capital;
import com.liquidresources.game.viewModel.bodies.udata.buildings.IonShield;
import com.liquidresources.game.viewModel.bodies.udata.buildings.OilPumpFacade;
import com.liquidresources.game.viewModel.bodies.udata.buildings.ShipFactoryViewFacade;
import com.liquidresources.game.viewModel.bodies.udata.bullets.Laser;
import com.liquidresources.game.viewModel.bodies.udata.bullets.Missile;
import com.liquidresources.game.viewModel.bodies.udata.ships.Fighter;
import com.liquidresources.game.viewModel.screens.game.buttons.GameScreenWidgetsGroup;

import static com.liquidresources.game.model.common.utils.UConverter.m2p;


public class GameScreen implements Screen {
    public GameScreen() {
        bodyFactoryWrapper = new BodyFactoryWrapper(new Vector2(0, -20f));

        Vector2 initAllyCoords = m2p(new Vector2(Gdx.graphics.getWidth() * 0.05f, Gdx.graphics.getHeight() * 0.35f));
        alliedBase = new AlliedBase(initAllyCoords, bodyFactoryWrapper);

        Vector2 initEnemyCoords = m2p(new Vector2(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight() * 0.35f));
        enemyBase = new EnemyBase(initEnemyCoords, bodyFactoryWrapper);

        bodyFactoryWrapper.createBody(new Ground(initAllyCoords));

        gameWorldModel = new GameWorldModel(bodyFactoryWrapper, alliedBase.getCapitalModel()); // TODO remove capital model
        gameRenderer = new GameRenderer(bodyFactoryWrapper);
        gameScreenWidgetGroup = new GameScreenWidgetsGroup();

        gameWorldModel.addObserver(gameScreenWidgetGroup);
        gameWorldModel.addObserver(gameRenderer);
    }

    @Override
    public void show() {
        Box2D.init();

        alliedBase.show();
        enemyBase.show();
        enemyBase.initEnemyAI();

        gameScreenWidgetGroup.initGameButtonsListeners(alliedBase, gameWorldModel);

        MusicManager.instance().registerMusic(this.getClass(), MusicManager.MusicTypes.MAIN_MUSIC);
    }

    @Override
    public void render(float delta) {
        gameRenderer.render(delta);
        gameRenderer.renderStatistic(alliedBase.getOil(), alliedBase.getWater());
        gameScreenWidgetGroup.render();

        gameWorldModel.update(delta);
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() {
        gameWorldModel.pause();
        MusicManager.instance().pauseMusic();
    }

    @Override
    public void resume() {
        MusicManager.instance().resumeMusic();
    }

    @Override
    public void hide() {
        MusicManager.instance().stopMusic();
        alliedBase.hide();
        enemyBase.hide();
        dispose();
    }

    @Override
    public void dispose() {
        gameScreenWidgetGroup.dispose();
        gameWorldModel.deleteObservers();
        bodyFactoryWrapper.dispose();
        Capital.dispose();
        IonShield.dispose();
        OilPumpFacade.dispose();
        ShipFactoryViewFacade.dispose();
//        Bomb.dispose()
        Laser.dispose();
        Missile.dispose();
        Fighter.dispose();
    }


    final private AlliedBase alliedBase;
    final private EnemyBase enemyBase;

    final private GameScreenWidgetsGroup gameScreenWidgetGroup;

    final private BodyFactoryWrapper bodyFactoryWrapper;
    final private GameWorldModel gameWorldModel;
    final private GameRenderer gameRenderer;
}
