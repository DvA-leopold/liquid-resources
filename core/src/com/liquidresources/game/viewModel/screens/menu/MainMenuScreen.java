package com.liquidresources.game.viewModel.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.music.manager.MusicManager;
import com.liquidresources.game.viewModel.screens.game.GameScreen;

public class MainMenuScreen implements Screen {
    public MainMenuScreen() {
        batch = ((LiquidResources) Gdx.app.getApplicationListener()).getMainBatch();
    }

    @Override
    public void show() {
        ((LiquidResources) Gdx.app.getApplicationListener())
                .getMusicManager().registerMusic(this.getClass(), MusicManager.MusicTypes.ADDITION_MUSIC);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            ((LiquidResources) Gdx.app.getApplicationListener()).setScreen(new GameScreen());
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        ((LiquidResources) Gdx.app.getApplicationListener()).getMusicManager().pauseMusic();
    }

    @Override
    public void resume() {
        ((LiquidResources) Gdx.app.getApplicationListener()).getMusicManager().resumeMusic();
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
