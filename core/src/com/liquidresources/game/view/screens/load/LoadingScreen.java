package com.liquidresources.game.view.screens.load;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.audio.MusicManager;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.screens.menu.MainMenuScreen;


public class LoadingScreen implements Screen {
    @Override
    public void show() {
        batch = LiquidResources.inst().getMainBatch();
        ResourceManager.inst().loadSection("audio", false);
        ResourceManager.inst().loadSection("symbols", false);
        ResourceManager.inst().loadSection("ui_skin", false);
        ResourceManager.inst().generateFonts("fonts/AllerDisplay.ttf");

        final Skin preloadSkin = (Skin) ResourceManager.inst().get("preload_skin/preload_skin.json");
        downloadProgressBar = new ProgressBar(0, 100, 1, true, preloadSkin);
        downloadProgressBar.setSize(downloadProgressBar.getWidth(), Gdx.graphics.getHeight() * 0.8f);

        final float x = Gdx.graphics.getWidth() - Gdx.graphics.getWidth() * 0.1f;
        final float y = Gdx.graphics.getHeight() * 0.1f;
        downloadProgressBar.setPosition(x, y);
    }

    @Override
    public void render(float delta) {
        downloadProgressBar.act(delta);
        int progress = (int) (ResourceManager.inst().updateAndGetProgress() * 100);
        downloadProgressBar.setValue(progress);

        downloadProgressBar.draw(batch, 1);

        if (progress == 100) {
            MusicManager.inst().initialize();
            LiquidResources.inst().setScreen(new MainMenuScreen());
        }
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
    public void dispose() { }


    private ProgressBar downloadProgressBar;
    private Batch batch;

}
