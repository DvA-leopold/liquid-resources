package com.liquidresources.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.liquidresources.game.debug.DebugShader;
import com.liquidresources.game.debug.DebugStatistic;
import com.liquidresources.game.model.music.manager.MusicManager;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.version.VersionHandler;
import com.liquidresources.game.viewModel.screens.load.LoadingScreen;

public final class LiquidResources extends Game {
	@Override
	public void create() {
        statistic = new DebugStatistic();
        if (statistic.isWindows()){
            mainBatch = new SpriteBatch(5000, DebugShader.createDefaultShader());
        } else {
            mainBatch = new SpriteBatch();
        }
        musicManager = new MusicManager();

        setScreen(new LoadingScreen());
	}

	@Override
	public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
        statistic.render(mainBatch);
        VersionHandler.render(mainBatch);
    }

    @Override
    public void dispose() {
        super.dispose();
        musicManager.dispose();
        mainBatch.dispose();
        statistic.dispose();
        ResourceManager.getInstance().dispose();
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

    final public SpriteBatch getMainBatch() {
        return mainBatch;
    }

    final public MusicManager getMusicManager() {
        return musicManager;
    }


    private MusicManager musicManager;

    private DebugStatistic statistic;
    private SpriteBatch mainBatch;
}
