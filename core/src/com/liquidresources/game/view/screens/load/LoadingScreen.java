package com.liquidresources.game.view.screens.load;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.audio.MusicManager;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.screens.menu.MainMenuScreen;

public class LoadingScreen implements Screen {
    @Override
    public void show() {
        batch = ((LiquidResources) Gdx.app.getApplicationListener()).getMainBatch();
        barHorizontalMid = new Texture("init/white_square_progress_bar.png");
        ResourceManager.instance().loadSection("audio", false);
        ResourceManager.instance().loadSection("symbols", false);
        ResourceManager.instance().loadSection("ui_skin", false);
        ResourceManager.instance().generateFonts("fonts/AllerDisplay.ttf");
    }

    @Override
    public void render(float delta) {
        int progress = (int) (ResourceManager.instance().updateAndGetProgress() * 100);
        int position = 100;
        int barHorizontalWidth = barHorizontalMid.getWidth() + 5;

        batch.begin();
        for (int i = 0; i < progress / 8; ++i) {
            batch.draw(barHorizontalMid,Gdx.graphics.getWidth() - 70, position);
            position += barHorizontalWidth;
        }
        batch.end();

        if (progress >= 100) {
            MusicManager.instance().initialize();

            System.out.println(ResourceManager.instance().getCurrentStorageSize());

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


    private Batch batch;
    private Texture barHorizontalMid;

}
