package com.liquidresources.game.view.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.liquidresources.game.model.resource.manager.ResourceManager;

public class LoadingScreen implements Screen {
    public LoadingScreen(final SpriteBatch batch) {
        this.batch = batch;
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
    }

    @Override
    public void show() {
        ResourceManager.getInstance().loadSection("data", true);
        ResourceManager.getInstance().loadSection("wtf", false);

        textureAtlas = (TextureAtlas) ResourceManager.getInstance().get("data/loading.pack");

        loadingBarHidden = new Image(textureAtlas.findRegion("loading-bar-hidden"));
        loadingBg = new Image(textureAtlas.findRegion("loading-frame-bg"));

        stage.addActor(loadingBarHidden);
        stage.addActor(loadingBg);
    }

    @Override
    public void render(float delta) {
        percent = Interpolation.linear.apply(percent,
                ResourceManager.getInstance().updateAndGetProgress(), 0.1f);
        System.out.println(percent);
        loadingBarHidden.setX(startX + endX * percent);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setWidth(450 - 450 * percent);
        loadingBg.invalidate();

        if (percent >= 1 - 1e-6) {
            ((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(batch));
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // The start position and how far to move the hidden loading bar
        startX = loadingBarHidden.getX();
        endX = 440;

        // The rest of the hidden bar
        loadingBg.setSize(450, 50);
        loadingBg.setX(loadingBarHidden.getX() + 30);
        loadingBg.setY(loadingBarHidden.getY() + 3);
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
        stage.dispose();
    }

    private float percent, startX, endX;

    private Image loadingBarHidden;
    private Image loadingBg;

    private final Stage stage;
    private TextureAtlas textureAtlas;
    private final SpriteBatch batch;
}
