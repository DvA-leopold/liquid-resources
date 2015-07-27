package com.liquidresources.game.viewModel.screens.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.GameWorldModel;
import com.liquidresources.game.model.game.world.factories.ShipFactory;
import com.liquidresources.game.view.GameRenderer;
import com.liquidresources.game.viewModel.ActionsListener;
import com.liquidresources.game.viewModel.screens.game.buttons.GameScreenWidgetsGroup;

public class GameScreen implements Screen {
    public GameScreen() {
        physicsWorld = new World(new Vector2(0, 0), true);

        BodyFactoryWrapper bodyFactoryWrapper = new BodyFactoryWrapper(physicsWorld);
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
        gameScreenWidgetGroup.addListener(null, ActionsListener.ADDITION_INIT_ACTION);

        gameScreenWidgetGroup.addListener(
                gameWorldModel.getRocketFireEventListener(), ActionsListener.ROCKET_FIRE_ACTION
        );

        gameScreenWidgetGroup.addListener(
                gameWorldModel.getShipFactoryListeners(ShipFactory.ShipType.BOMBER),
                ActionsListener.CREATE_BOMBER_ACTION
        );

        gameScreenWidgetGroup.addListener(
                gameWorldModel.getShipFactoryListeners(ShipFactory.ShipType.FIGHTER),
                ActionsListener.CREATE_FIGHTER_ACTION
        );

        gameScreenWidgetGroup.addListener(
                gameWorldModel.getMainAIListener(), ActionsListener.ION_SHIELD_ACTION
        );
    }

    @Override
    public void render(float delta) {
        gameRenderer.render(delta);
        gameScreenWidgetGroup.render();

        gameWorldModel.update(delta);

        physicsWorld.step(1 / 60f, 6, 2);
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
        gameRenderer.dispose();
        gameScreenWidgetGroup.dispose();

        Array<Body> worldBodies = new Array<>(physicsWorld.getBodyCount());
        physicsWorld.getBodies(worldBodies);
        for (Body body : worldBodies) {
            physicsWorld.destroyBody(body);
        }

        physicsWorld.dispose();
    }


    final private World physicsWorld;
    final private GameRenderer gameRenderer;

    final private GameWorldModel gameWorldModel;

    final private GameScreenWidgetsGroup gameScreenWidgetGroup;
}
