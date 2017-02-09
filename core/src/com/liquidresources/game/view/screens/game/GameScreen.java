package com.liquidresources.game.view.screens.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.kotcrab.vis.runtime.scene.Scene;
import com.kotcrab.vis.runtime.scene.SceneLoader;
import com.kotcrab.vis.runtime.scene.VisAssetManager;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.GameWorldModel;
import com.liquidresources.game.audio.MusicManager;
import com.liquidresources.game.model.bodies.UpdatableBody;
import com.liquidresources.game.system.PhysicsSystem;
import com.liquidresources.game.utils.GameStateHolder;
import com.liquidresources.game.view.screens.game.widgets.GameScreenWidgetsGroup;


final public class GameScreen implements Screen {
    public GameScreen() {
        final SpriteBatch batch = ((LiquidResources) Gdx.app.getApplicationListener()).getMainBatch();
        visAssetManager = new VisAssetManager(batch);

        gameScreenWidgetsGroup = new GameScreenWidgetsGroup(batch);
        GameStateHolder.addObserver(gameScreenWidgetsGroup);

        gameWorldModel = new GameWorldModel(batch);
        GameStateHolder.addObserver(gameWorldModel);

        physicsDebugRenderer = new Box2DDebugRenderer();

        SceneLoader.SceneParameter parameter = new SceneLoader.SceneParameter();
        parameter.config.addSystem(PhysicsSystem.class);
        mainScene = visAssetManager.loadSceneNow("scene/main.scene", parameter);

    }

    @Override
    public void show() {
        MusicManager.instance().registerMusic(this.getClass(), MusicManager.MusicTypes.MAIN_MUSIC);
        gameScreenWidgetsGroup.setListeners(
                gameWorldModel.getIonShieldListener(),
                gameWorldModel.getFireMissileListener(),
                gameWorldModel.getBulletFireListener()
        );
    }

    @Override
    public void render(float delta) {
        gameWorldModel.update(delta);
        mainScene.render();
//        physicsDebugRenderer.render(, viewport.getCamera().combined);
        gameScreenWidgetsGroup.render();
    }

    @Override
    public void resize(int width, int height) {
        mainScene.resize(width, height);
    }

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
        UpdatableBody.finalDispose();
        GameStateHolder.dispose();
        visAssetManager.dispose();
    }


    final private Box2DDebugRenderer physicsDebugRenderer;

    final private GameScreenWidgetsGroup gameScreenWidgetsGroup;
    final private GameWorldModel gameWorldModel;
    final private VisAssetManager visAssetManager;
    final private Scene mainScene;
}
