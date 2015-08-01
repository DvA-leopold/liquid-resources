package com.liquidresources.game.viewModel.screens.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.GameWorldModel;
import com.liquidresources.game.model.game.world.factories.ShipFactory;
import com.liquidresources.game.view.GameRenderer;
import com.liquidresources.game.viewModel.Actions;
import com.liquidresources.game.viewModel.screens.game.buttons.GameScreenWidgetsGroup;

public class GameScreen implements Screen {
    public GameScreen() {
        bodyFactoryWrapper = new BodyFactoryWrapper(new Vector2(0, -10));
        gameRenderer = new GameRenderer(new Vector2(100f, 80f), bodyFactoryWrapper);

        gameWorldModel = new GameWorldModel(
                bodyFactoryWrapper,
                gameRenderer.getShipFactoryPosition(),
                gameRenderer.getMainAIPosition()
        );

        gameScreenWidgetGroup = new GameScreenWidgetsGroup();

        //camera = new OrthographicCamera();
        //camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        Box2D.init();

        gameRenderer.show();
        gameScreenWidgetGroup.addListener(null, Actions.ADDITION_INIT_ACTION);

        gameScreenWidgetGroup.addListener(
                gameWorldModel.getRocketFireEventListener(), Actions.ROCKET_FIRE_ACTION
        );

        gameScreenWidgetGroup.addListener(
                gameWorldModel.getShipFactoryListeners(ShipFactory.ShipType.BOMBER),
                Actions.CREATE_BOMBER_ACTION
        );

        gameScreenWidgetGroup.addListener(
                gameWorldModel.getShipFactoryListeners(ShipFactory.ShipType.FIGHTER),
                Actions.CREATE_FIGHTER_ACTION
        );

        gameScreenWidgetGroup.addListener(
                gameWorldModel.getMainAIListener(), Actions.ION_SHIELD_ACTION
        );
    }

    @Override
    public void render(float delta) {
        gameRenderer.render(delta);
        gameScreenWidgetGroup.render();

        gameWorldModel.update(delta);
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() {
        gameWorldModel.pause();
    }

    @Override
    public void resume() { }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameScreenWidgetGroup.dispose();
        gameRenderer.dispose();
        bodyFactoryWrapper.dispose();
    }


    final private GameScreenWidgetsGroup gameScreenWidgetGroup;

    final private BodyFactoryWrapper bodyFactoryWrapper;
    final private GameWorldModel gameWorldModel;
    final private GameRenderer gameRenderer;
}
