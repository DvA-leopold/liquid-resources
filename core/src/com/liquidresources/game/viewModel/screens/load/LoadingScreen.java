package com.liquidresources.game.viewModel.screens.load;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.model.common.utils.UConverter;
import com.liquidresources.game.viewModel.screens.menu.MainMenuScreen;

public class LoadingScreen implements Screen {
    public LoadingScreen() {
        batch = ((LiquidResources) Gdx.app.getApplicationListener()).getMainBatch();
    }

    @Override
    public void show() {
        barHorizontalMid = new Texture("init/white_square_progress_bar.png");
        ResourceManager.getInstance().loadSection("drawable", false);
        ResourceManager.getInstance().loadSection("backgrounds", false);
        ResourceManager.getInstance().loadSection("audio", false);
        ResourceManager.getInstance().loadSection("symbols", false);
        ResourceManager.getInstance().loadSection("particles", false);
        ResourceManager.getInstance().loadSection("skins", false);
    }

    @Override
    public void render(float delta) {
        int progress = (int) (ResourceManager.getInstance().updateAndGetProgress() * 100);
        int position = 100;
        int barHorizontalWidth = barHorizontalMid.getWidth() + 5;

        batch.begin();
        for (int i = 0; i < progress / 8; ++i) {
            batch.draw(barHorizontalMid,
                    UConverter.m2p(Gdx.graphics.getWidth() - 70),
                    UConverter.m2p(position));
            position += barHorizontalWidth;
        }
        batch.end();

        if (progress >= 100) {
            ((LiquidResources) Gdx.app.getApplicationListener()).getMusicManager().initialize();
            ((LiquidResources) Gdx.app.getApplicationListener()).getMusicManager().startMusicManager();

            System.out.println(ResourceManager.getInstance().getCurrentStorageSize());

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
