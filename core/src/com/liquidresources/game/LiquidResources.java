package com.liquidresources.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.liquidresources.game.debug.DebugStatistic;
import com.liquidresources.game.model.music.manager.MusicManager;
import com.liquidresources.game.model.resource.manager.FreeTypeFontSkinLoader;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.version.VersionHandler;
import com.liquidresources.game.view.UConverter;
import com.liquidresources.game.viewModel.screens.load.LoadingScreen;

import java.util.HashMap;
import java.util.Map;

import static com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public final class LiquidResources extends Game {
	@Override
	public void create() {
        initFontGenerator();

        statistic = new DebugStatistic(mainFonts);
        versionHandler = new VersionHandler(mainFonts);

        camera = new OrthographicCamera(
                Gdx.graphics.getWidth() / UConverter.getPMCoefficient(),
                Gdx.graphics.getHeight() / UConverter.getPMCoefficient()
        );
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0f);

        mainBatch = new SpriteBatch();
        musicManager = new MusicManager();

        setScreen(new LoadingScreen());
	}

    @Override
	public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        mainBatch.setProjectionMatrix(camera.combined);

        super.render();
        versionHandler.render(mainBatch);
        statistic.render(mainBatch);
    }

    @Override
    public void dispose() {
        super.dispose();
        musicManager.dispose();
        mainBatch.dispose();
        // mainFonts.dispose(); already disposed
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

    @Override
    public void setScreen(Screen screen) {
        super.setScreen(screen);
        musicManager.switchSample(screen.getClass());
    }

    final public SpriteBatch getMainBatch() {
        return mainBatch;
    }

    final public MusicManager getMusicManager() {
        return musicManager;
    }

    final public Camera getCamera() {
        return camera;
    }

    final public BitmapFont getMainFonts() {
        return mainFonts;
    }

    private void initFontGenerator() {
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/AllerDisplay.ttf"));
        FreeTypeFontParameter fontParameter = new FreeTypeFontParameter();

        fontParameter.size = Gdx.graphics.getHeight() * 64 / Gdx.graphics.getWidth();
        mainFonts = fontGenerator.generateFont(fontParameter);

        fontParameter.color = Color.BLACK;
        BitmapFont additionFont = fontGenerator.generateFont(fontParameter);

        Map<String, BitmapFont> fontMap = new HashMap<>();
        fontMap.put("mainFonts", mainFonts);
        fontMap.put("addFonts", additionFont);

        ResourceManager.getInstance().setSkinLoader(new FreeTypeFontSkinLoader(new InternalFileHandleResolver(), fontMap));

        fontGenerator.dispose();
    }


    private DebugStatistic statistic;
    private VersionHandler versionHandler;

    private BitmapFont mainFonts;

    private MusicManager musicManager;

    private Camera camera;
    private SpriteBatch mainBatch;
}
