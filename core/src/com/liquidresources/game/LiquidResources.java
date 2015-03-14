package com.liquidresources.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.liquidresources.game.debug.DebugShader;
import com.liquidresources.game.debug.DebugStatistic;

public final class LiquidResources extends Game {
	@Override
	public void create() {
        statistic = new DebugStatistic();
        if (statistic.isWindows()){
            mainBatch = new SpriteBatch(5000, DebugShader.createDefaultShader());
        } else {
            mainBatch = new SpriteBatch();
        }
	}

	@Override
	public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        super.render();
        statistic.render(mainBatch);
    }

    @Override
    public void dispose() {
        mainBatch.dispose();
        statistic.dispose();
        super.dispose();
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


    private DebugStatistic statistic;
    private SpriteBatch mainBatch;
}
