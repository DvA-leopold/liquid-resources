package com.liquidresources.game.view.screens.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.audio.MusicManager;
import com.liquidresources.game.view.screens.game.GameScreen;


public class MainMenuScreen implements Screen {
    @Override
    public void show() {
        MusicManager.inst().registerMusic(this.getClass(), MusicManager.MusicTypes.ADDITION_MUSIC);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.justTouched()) {
            LiquidResources.inst().setScreen(new GameScreen());
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        MusicManager.inst().pauseMusic();
    }

    @Override
    public void resume() {
        MusicManager.inst().resumeMusic();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {

    }
}
