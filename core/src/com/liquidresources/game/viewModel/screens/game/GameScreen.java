package com.liquidresources.game.viewModel.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.GameWorld;
import com.liquidresources.game.view.GameRenderer;
import com.liquidresources.game.viewModel.screens.game.buttons.GameScreenWidgetsGroup;
import com.liquidresources.game.viewModel.screens.wg.WidgetsGroup;

public class GameScreen implements Screen {
    public GameScreen() {
        final SpriteBatch batch = ((LiquidResources) Gdx.app.getApplicationListener()).getMainBatch();
        gameScreenWidgetGroup = new GameScreenWidgetsGroup(batch);

        gameWorld = new GameWorld();
        gameRenderer = new GameRenderer(batch, gameWorld.getMainAI());
    }

    @Override
    public void show() {
        gameRenderer.show();
        gameScreenWidgetGroup.setListeners();
    }

    @Override
    public void render(float delta) {
        gameWorld.update(delta);

        gameRenderer.render();
        gameScreenWidgetGroup.render();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        gameWorld.pause();
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        gameRenderer.dispose();
        gameScreenWidgetGroup.dispose();
        gameWorld.dispose();
    }


    final private WidgetsGroup gameScreenWidgetGroup;
    final private GameRenderer gameRenderer;
    final private GameWorld gameWorld;
}
