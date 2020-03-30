package com.liquidresources.game.view.screens.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.liquidresources.game.model.GameWorldModel;
import com.liquidresources.game.model.audio.MusicManager;
import com.liquidresources.game.utils.GameStateHolder;
import com.liquidresources.game.view.screens.game.widgets.GameScreenWidgetsGroup;


final public class GameScreen implements Screen {
    public GameScreen() {
        viewport = new StretchViewport(21.25f, 30f);

        gameScreenWidgetsGroup = new GameScreenWidgetsGroup();
        gameWorldModel = new GameWorldModel();

        physicsDebugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {
        MusicManager.inst().registerMusic(this.getClass(), MusicManager.MusicTypes.MAIN_MUSIC);
        gameScreenWidgetsGroup.setListeners(
                gameWorldModel.getIonShieldListener(),
                gameWorldModel.getFireMissileListener(),
                gameWorldModel.getBulletFireListener(),
                gameWorldModel.getLaserFireListener()
        );
    }

    @Override
    public void render(float delta) {
        gameWorldModel.update(delta);
        physicsDebugRenderer.render(gameWorldModel.getWorld(), viewport.getCamera().combined);
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() { }

    @Override
    public void resume() { }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        physicsDebugRenderer.dispose();
        GameStateHolder.dispose();
    }


    final private Box2DDebugRenderer physicsDebugRenderer;

    final private Viewport viewport;

    final private GameScreenWidgetsGroup gameScreenWidgetsGroup;
    final private GameWorldModel gameWorldModel;

}
