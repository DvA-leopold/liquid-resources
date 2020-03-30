package com.liquidresources.game;

import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.liquidresources.game.model.audio.MusicManager;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.utils.DebugStatistic;
import com.liquidresources.game.view.screens.load.LoadingScreen;


public final class LiquidResources extends Game {
	@Override
	public void create() {
        Box2D.init();

        batch = new SpriteBatch();
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        debug = new DebugStatistic(true, true);

        entityEngine = new PooledEngine();

        Gdx.input.setInputProcessor(stage);
        setScreen(new LoadingScreen());
	}

    @Override
	public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        entityEngine.update(Gdx.graphics.getDeltaTime());

        batch.begin();
        super.render();
        batch.end();

        stage.act();
        stage.draw();

        debug.act();
    }

    @Override
    public void dispose() {
        super.dispose();
        MusicManager.inst().dispose();
        ResourceManager.inst().dispose();
        batch.dispose();
    }

    @Override
    public void pause() {
        super.pause();
//        MusicManager.instance().pauseMusic();
    }

    @Override
    public void resume() {
        super.resume();
//        MusicManager.instance().resumeMusic();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
        MusicManager.inst().switchSample(screen.getClass());
    }

    public SpriteBatch getMainBatch() {
        return batch;
    }

    public Stage getStage() {
	    return stage;
    }

    public static LiquidResources inst() {
        return (LiquidResources) Gdx.app.getApplicationListener();
    }


    private PooledEngine entityEngine;
    private DebugStatistic debug;
    private SpriteBatch batch;
    private Stage stage;

}
