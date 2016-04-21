package com.liquidresources.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.i18n.manager.I18NBundleManager;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.symbols.SymbolsRenderer;
import com.liquidresources.game.viewModel.GameStates;

import java.util.Observable;
import java.util.Observer;

import static com.liquidresources.game.model.common.utils.UConverter.m2p;
import static com.liquidresources.game.model.common.utils.UConverter.getPMCoefficient;

public class GameRenderer implements Observer {
    public GameRenderer(final BodyFactoryWrapper bodyFactoryWrapper) {
        this.batch = ((LiquidResources) Gdx.app.getApplicationListener()).getMainBatch();
        this.bodyFactoryWrapper = bodyFactoryWrapper;

        gameState = GameStates.GAME_PREPARING;


        worldDebugRenderer = new Box2DDebugRenderer();
        camera = ((LiquidResources) Gdx.app.getApplicationListener()).getCamera();

        desertBackground = (Texture) ResourceManager.getInstance().get("backgrounds/desert.jpg");
        mainFonts = ((LiquidResources) Gdx.app.getApplicationListener()).getMainFonts();

        symbolsRenderer = new SymbolsRenderer(0, m2p(Gdx.graphics.getHeight() - 60), m2p(20), m2p(45));

        scaledProjectionMatrix = camera.combined.cpy().scale(1 / getPMCoefficient(), 1 / getPMCoefficient(), 1);
    }

    public void render(float delta) {
        switch (gameState) {
            case GAME_PREPARING:
                renderPreparingState();
                break;
            case GAME_RUNNING:
                renderRunState(delta);
                break;
            case GAME_PAUSED:
                renderPauseState(delta);
                break;
            case GAME_EXIT:
                renderExitState();
                break;
            case GAME_OVER:
                renderOverState();
                break;
        }

        worldDebugRenderer.render(bodyFactoryWrapper.getPhysicsWorld(), camera.combined);
    }

    public void renderStatistic(long oilStatistic, long waterStatistic) {
        batch.begin();

        symbolsRenderer.renderNumber(batch, oilStatistic);
        symbolsRenderer.renderNumber(batch, waterStatistic, 0, -40);

        batch.end();
    }

    private void renderPreparingState() {
        batch.begin();

        batch.disableBlending();
        batch.draw(desertBackground, 0, 0, m2p(Gdx.graphics.getWidth()), m2p(Gdx.graphics.getHeight()));
        batch.enableBlending();

        batch.setProjectionMatrix(scaledProjectionMatrix);
        mainFonts.draw(batch,
                       I18NBundleManager.getString("prepare"),
                       Gdx.graphics.getWidth() * 0.2f,
                       Gdx.graphics.getHeight() * 0.8f
        );

        batch.end();
    }

    private void renderRunState(float delta) {
        batch.begin();

        batch.disableBlending();
        batch.draw(desertBackground, 0, 0, m2p(Gdx.graphics.getWidth()), m2p(Gdx.graphics.getHeight()));
        batch.enableBlending();

        for (Body building : bodyFactoryWrapper.getBuildingsBodies()) {
            ((DrawableBody) building.getUserData()).draw(batch, null, delta);
        }

        for (Body ship : bodyFactoryWrapper.getShipsBodies()) {
            ((DrawableBody) ship.getUserData()).draw(batch, ship.getPosition(), delta);
        }

        for (Body bullet : bodyFactoryWrapper.getBulletBodies()) {
            ((DrawableBody) bullet.getUserData()).draw(batch, bullet.getPosition(), delta);
        }

        batch.end();
    }

    private void renderPauseState(float delta) {
        batch.begin();

        batch.disableBlending();
        batch.draw(desertBackground, 0, 0, m2p(Gdx.graphics.getWidth()), m2p(Gdx.graphics.getHeight()));
        batch.enableBlending();

        for (Body staticBody : bodyFactoryWrapper.getBuildingsBodies()) {
            ((DrawableBody) staticBody.getUserData()).draw(batch, null, 0f);
        }

        for (Body dynamicBody : bodyFactoryWrapper.getShipsBodies()) {
            ((DrawableBody) dynamicBody.getUserData()).draw(batch, dynamicBody.getPosition(), delta);
        }

        batch.end();

        worldDebugRenderer.render(bodyFactoryWrapper.getPhysicsWorld(), camera.combined);
    }

    private void renderExitState() {

    }

    private void renderOverState() {

    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof GameStates) {
            gameState = (GameStates) arg;
        }
    }


    private Matrix4 scaledProjectionMatrix;

    private GameStates gameState;
    final private Box2DDebugRenderer worldDebugRenderer;

    final private Camera camera;

    final private BitmapFont mainFonts;
    final private SymbolsRenderer symbolsRenderer;

    final private BodyFactoryWrapper bodyFactoryWrapper;


    final private Texture desertBackground;
    final private SpriteBatch batch;
}

