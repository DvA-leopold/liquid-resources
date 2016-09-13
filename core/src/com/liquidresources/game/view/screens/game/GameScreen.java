package com.liquidresources.game.view.screens.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.liquidresources.game.model.GameWorldModel;
import com.liquidresources.game.audio.MusicManager;
import com.liquidresources.game.model.bodies.UpdatableBodyImpl;
import com.liquidresources.game.utils.GameStateHolder;
import com.liquidresources.game.view.screens.game.widgets.GameScreenWidgetsGroup;
import com.uwsoft.editor.renderer.SceneLoader;


final public class GameScreen implements Screen {
    public GameScreen() {
        viewport = new FitViewport(35.0f, 22.5f);
        sceneLoader = new SceneLoader(); // TODO implement Iresourceretriever
        sceneLoader.loadScene("MainScene", viewport);

        gameScreenWidgetsGroup = new GameScreenWidgetsGroup(sceneLoader.getBatch());
        GameStateHolder.addObserver(gameScreenWidgetsGroup);

        gameWorldModel = new GameWorldModel(sceneLoader);
        GameStateHolder.addObserver(gameWorldModel);

        physicsDebugRenderer = new Box2DDebugRenderer();
    }

    @Override
    public void show() {
        MusicManager.instance().registerMusic(this.getClass(), MusicManager.MusicTypes.MAIN_MUSIC);
        gameScreenWidgetsGroup.setListeners(gameWorldModel.getIonShieldListener());

    }

    @Override
    public void render(float delta) {
        gameWorldModel.update(delta);

        physicsDebugRenderer.render(sceneLoader.world, viewport.getCamera().combined);
        gameScreenWidgetsGroup.render();
    }

    @Override
    public void resize(int width, int height) { }

    @Override
    public void pause() {
        MusicManager.instance().pauseMusic();
    }

    @Override
    public void resume() {
        MusicManager.instance().resumeMusic();
    }

    @Override
    public void hide() {
        MusicManager.instance().stopMusic();
        dispose();
    }

    @Override
    public void dispose() {
        physicsDebugRenderer.dispose();
        gameScreenWidgetsGroup.dispose();
        UpdatableBodyImpl.finalDispose();
        GameStateHolder.dispose();
    }


    final private Box2DDebugRenderer physicsDebugRenderer;

    final private Viewport viewport;

    final private GameScreenWidgetsGroup gameScreenWidgetsGroup;
    final private GameWorldModel gameWorldModel;
    final private SceneLoader sceneLoader;
}
