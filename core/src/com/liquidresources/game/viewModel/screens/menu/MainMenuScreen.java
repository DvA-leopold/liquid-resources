package com.liquidresources.game.viewModel.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.liquidresources.game.LiquidResources;

public class MainMenuScreen implements Screen {
    public MainMenuScreen() {
        batch = ((LiquidResources) Gdx.app.getApplicationListener()).getMainBatch();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

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

    }


    final private SpriteBatch batch;
}
