package com.liquidresources.game.viewModel.screens.load;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.viewModel.screens.game.GameScreen;
import com.liquidresources.game.viewModel.screens.menu.MainMenuScreen;

public class LoadingScreen implements Screen {
    public LoadingScreen() {
        batch = ((LiquidResources) Gdx.app.getApplicationListener()).getMainBatch();
    }

    @Override
    public void show() {
        barHorizontalMid = new Texture("init/white_square_progress_bar.png");
        ResourceManager.getInstance().loadSection("drawable", false);
        ResourceManager.getInstance().loadSection("skins", false);
        ResourceManager.getInstance().loadSection("backgrounds", false);
        ResourceManager.getInstance().loadSection("audio", false);
        ResourceManager.getInstance().loadSection("symbols", false);
        ResourceManager.getInstance().loadSection("fonts", false);
    }

    @Override
    public void render(float delta) {
        int progress = (int) (ResourceManager.getInstance().updateAndGetProgress() * 100);
        int position = 100;
        int barHorizontalWidth = barHorizontalMid.getWidth() + 5;

        batch.begin();
        for (int i = 0; i < progress / 8; ++i) {
            batch.draw(barHorizontalMid, Gdx.graphics.getWidth() - 70, position);
            position += barHorizontalWidth;
        }
        batch.end();

        if (progress >= 100) {
            ((LiquidResources) Gdx.app.getApplicationListener()).getMusicManager().initialize();
            ((LiquidResources) Gdx.app.getApplicationListener()).getMusicManager().startMusicManager();
            ((Game) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
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
    public void dispose() {
        barHorizontalMid.dispose();
    }


    private Texture barHorizontalMid;
    final private SpriteBatch batch;
}
