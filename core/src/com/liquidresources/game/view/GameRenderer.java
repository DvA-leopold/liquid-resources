package com.liquidresources.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.i18n.manager.I18NBundleManager;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.view.symbols.SymbolsRenderer;
import com.liquidresources.game.viewModel.GameStates;
import com.liquidresources.game.viewModel.bases.AlliedBase;
import com.liquidresources.game.viewModel.bases.BaseFacade;
import com.liquidresources.game.viewModel.bases.EnemyBase;
import com.liquidresources.game.viewModel.bodies.udata.bariers.Ground;

import java.util.Observable;
import java.util.Observer;

import static com.liquidresources.game.view.UConverter.M2P;

public class GameRenderer implements Observer {
    public GameRenderer(final Vector2 initAllyCoords,
                        final Vector2 initEnemyCoords,
                        final BodyFactoryWrapper bodyFactoryWrapper) {
        this.batch = ((LiquidResources) Gdx.app.getApplicationListener()).getMainBatch();
        this.bodyFactoryWrapper = bodyFactoryWrapper;

        gameState = GameStates.GAME_PREPARING;

        final Vector2 graphicSize = M2P(Gdx.graphics.getWidth() * 0.08f, Gdx.graphics.getHeight() * 0.08f);
        alliedBase = new AlliedBase(M2P(initAllyCoords), graphicSize, bodyFactoryWrapper);
        enemyBase = new EnemyBase(M2P(initEnemyCoords), graphicSize, bodyFactoryWrapper);

        worldDebugRenderer = new Box2DDebugRenderer();
        camera = ((LiquidResources) Gdx.app.getApplicationListener()).getCamera();

        desertBackground = (Texture) ResourceManager.getInstance().get("backgrounds/desert.jpg");
        mainFonts = ((LiquidResources) Gdx.app.getApplicationListener()).getMainFonts();

        symbolsRenderer = new SymbolsRenderer(0, M2P(Gdx.graphics.getHeight() - 60), M2P(20), M2P(45));

        bodyFactoryWrapper.createBody(new Ground(initAllyCoords), true);
    }

    public void show() {
        alliedBase.show();
        enemyBase.show();
    }

    public void render(float delta) {
        switch (gameState) {
            case GAME_PREPARING:
                renderPreparingState(delta);
                break;
            case GAME_RUNNING:
                renderRunState(delta);
                break;
            case GAME_PAUSED:
                renderPauseState(delta);
                break;
            case GAME_EXIT:
                renderExitState(delta);
                break;
            case GAME_OVER:
                renderOverState(delta);
                break;
        }
    }

    public void renderStatistic(long oilStatistic, long waterStatistic) {
        batch.begin();

        symbolsRenderer.renderNumber(batch, oilStatistic);
        symbolsRenderer.renderNumber(batch, waterStatistic, 0, -40);

        batch.end();
    }

    private void renderPreparingState(float delta) {
        batch.begin();

        batch.disableBlending();
        batch.draw(
                desertBackground, 0, 0,
                M2P(Gdx.graphics.getWidth()),
                M2P(Gdx.graphics.getHeight())
        );
        batch.enableBlending();

        // TODO оптимизировать, не создавать новую копию камеры заново
        batch.setProjectionMatrix(camera.combined.cpy().scale(
                1 / UConverter.getPMCoefficient(),
                1 / UConverter.getPMCoefficient(),
                1)
        );
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
        batch.draw(
                desertBackground, 0, 0,
                M2P(Gdx.graphics.getWidth()),
                M2P(Gdx.graphics.getHeight())
        );
        batch.enableBlending();

        for (Body staticBody : bodyFactoryWrapper.getConstructionsBodies()) {
            ((DrawableBody) staticBody.getUserData()).draw(batch, null, delta);
        }

        for (Body dynamicBody : bodyFactoryWrapper.getDynamicBodies()) {
            ((DrawableBody) dynamicBody.getUserData()).draw(batch, dynamicBody.getPosition(), delta);
        }

        batch.end();

        worldDebugRenderer.render(bodyFactoryWrapper.getPhysicsWorld(), camera.combined);
    }

    private void renderPauseState(float delta) {
        batch.begin();

        batch.disableBlending();
        batch.draw(
                desertBackground, 0, 0,
                M2P(Gdx.graphics.getWidth()),
                M2P(Gdx.graphics.getHeight())
        );
        batch.enableBlending();

        for (Body staticBody : bodyFactoryWrapper.getConstructionsBodies()) {
            ((DrawableBody) staticBody.getUserData()).draw(batch, null, 0f);
        }

        for (Body dynamicBody : bodyFactoryWrapper.getDynamicBodies()) {
            ((DrawableBody) dynamicBody.getUserData()).draw(batch, dynamicBody.getPosition(), delta);
        }

        batch.end();

        worldDebugRenderer.render(bodyFactoryWrapper.getPhysicsWorld(), camera.combined);
    }

    private void renderExitState(float delta) {

    }

    private void renderOverState(float delta) {

    }

    public BaseFacade getBase(RelationTypes relationType) {
        switch (relationType) {
            case ALLY:
                return alliedBase;
            case ENEMY:
                return enemyBase;
            default:
                return null;
        }
    }

    public void hide() {
        alliedBase.hide();
        enemyBase.hide();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof GameStates) {
            gameState = (GameStates) arg;
        }
    }


    private GameStates gameState;
    final private Box2DDebugRenderer worldDebugRenderer;

    final private Camera camera;

    final private BitmapFont mainFonts;
    final private SymbolsRenderer symbolsRenderer;

    final private BodyFactoryWrapper bodyFactoryWrapper;
    final AlliedBase alliedBase;
    final EnemyBase enemyBase;

    final private Texture desertBackground;
    final private SpriteBatch batch;
}

