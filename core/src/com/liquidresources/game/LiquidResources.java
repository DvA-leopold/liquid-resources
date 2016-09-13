package com.liquidresources.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.liquidresources.game.audio.MusicManager;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.utils.DebugStatistic;
import com.liquidresources.game.view.screens.load.LoadingScreen;


public final class LiquidResources extends Game {
	@Override
	public void create() {
	    batch = new SpriteBatch();
        debugInfoRenderer = new DebugStatistic(true, true);

        setScreen(new LoadingScreen());
	}

    @Override
	public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        super.render();
        debugInfoRenderer.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        MusicManager.instance().dispose();
        ResourceManager.instance().dispose();
        batch.dispose();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
        MusicManager.instance().switchSample(screen.getClass());
    }

    public SpriteBatch getMainBatch() {
        return batch;
    }


    private DebugStatistic debugInfoRenderer;
    private SpriteBatch batch;
}
