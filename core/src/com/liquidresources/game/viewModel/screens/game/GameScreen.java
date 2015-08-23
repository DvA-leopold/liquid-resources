package com.liquidresources.game.viewModel.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.GameWorldModel;
import com.liquidresources.game.model.music.manager.MusicManager;
import com.liquidresources.game.view.GameRenderer;
import com.liquidresources.game.viewModel.screens.game.buttons.GameScreenWidgetsGroup;

public class GameScreen implements Screen {
    public GameScreen() {
        bodyFactoryWrapper = new BodyFactoryWrapper(new Vector2(0, -9.8f));
        gameRenderer = new GameRenderer(
                new Vector2(Gdx.graphics.getWidth() * 0.05f, Gdx.graphics.getHeight() * 0.35f),
                new Vector2(Gdx.graphics.getWidth() * 0.95f, Gdx.graphics.getHeight() * 0.35f),
                bodyFactoryWrapper
        );

        gameWorldModel = new GameWorldModel(
                bodyFactoryWrapper,
                gameRenderer.getBase(true).getMainBasePosition(),
                gameRenderer.getBase(true).getShipFactoryPosition(),
                gameRenderer.getBase(false).getMainBasePosition(),
                gameRenderer.getBase(false).getShipFactoryPosition()
        );

        gameScreenWidgetGroup = new GameScreenWidgetsGroup();

        gameWorldModel.addObserver(gameScreenWidgetGroup);
        gameWorldModel.addObserver(gameRenderer);

        //TODO это временный dirty hack
//        gameRenderer.setMainBasePtr(gameWorldModel.getMainBaseModel());

        //camera = new OrthographicCamera();
        //camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        Box2D.init();

        gameRenderer.show();
        gameScreenWidgetGroup.initWorldListeners(gameWorldModel);

        ((LiquidResources) Gdx.app.getApplicationListener()).
                getMusicManager().registerMusic(this.getClass(), MusicManager.MusicTypes.MAIN_MUSIC);
    }

    @Override
    public void render(float delta) {
        gameRenderer.render(delta);
        gameRenderer.statisticRender(gameWorldModel.getOil(), gameWorldModel.getWater());
        gameScreenWidgetGroup.render();

        gameWorldModel.update(delta);
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() {
        gameWorldModel.pause();
        ((LiquidResources) Gdx.app.getApplicationListener()).getMusicManager().pauseMusic();
    }

    @Override
    public void resume() {
        ((LiquidResources) Gdx.app.getApplicationListener()).getMusicManager().resumeMusic();
    }

    @Override
    public void hide() {
        //((LiquidResources) Gdx.app.getApplicationListener()).getMusicManager().stopMusic();
        gameRenderer.hide();
        dispose();
    }

    @Override
    public void dispose() {
        gameScreenWidgetGroup.dispose();
        bodyFactoryWrapper.dispose();
        gameWorldModel.deleteObservers();
    }


    final private GameScreenWidgetsGroup gameScreenWidgetGroup;

    final private BodyFactoryWrapper bodyFactoryWrapper;
    final private GameWorldModel gameWorldModel;
    final private GameRenderer gameRenderer;
}
