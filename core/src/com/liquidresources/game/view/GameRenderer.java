package com.liquidresources.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.game.world.base.AMainBaseModel;
import com.liquidresources.game.model.i18n.manager.I18NBundleManager;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.viewModel.bases.AlliedBase;
import com.liquidresources.game.viewModel.bases.BaseFacade;
import com.liquidresources.game.viewModel.bases.EnemyBase;
import com.liquidresources.game.viewModel.bodies.udata.bariers.Ground;
import com.liquidresources.game.view.symbols.SymbolsRenderer;
import com.liquidresources.game.viewModel.GameStates;

import java.util.Observable;
import java.util.Observer;

public class GameRenderer implements Observer {
    public GameRenderer(final Vector2 initAllyCoords,
                        final Vector2 initEnemyCoords,
                        final BodyFactoryWrapper bodyFactoryWrapper) {
        this.batch = ((LiquidResources) Gdx.app.getApplicationListener()).getMainBatch();
        this.bodyFactoryWrapper = bodyFactoryWrapper;

        gameState = GameStates.GAME_PREPARING;

        final Vector2 graphicSize = new Vector2(Gdx.graphics.getWidth() * 0.08f, Gdx.graphics.getHeight() * 0.08f);
        alliedBase = new AlliedBase(initAllyCoords, graphicSize, bodyFactoryWrapper);
        enemyBase = new EnemyBase(initEnemyCoords, graphicSize, bodyFactoryWrapper);

        worldRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        desertBackground = (Texture) ResourceManager.getInstance().get("backgrounds/desert.jpg");
        blackFont = (BitmapFont) ResourceManager.getInstance().get("fonts/blackFont.fnt");

        symbolsRenderer = new SymbolsRenderer(0, Gdx.graphics.getHeight() - 60, 20, 45); // TODO dynamic size

        bodyFactoryWrapper.createBody(new Ground(initAllyCoords), true);
    }

    public void show() {
        //alliedBase.show();
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

    private void renderPreparingState(float delta) {
        batch.begin();

        batch.disableBlending();
        batch.draw(desertBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();

        blackFont.draw(batch, I18NBundleManager.getString("prepare"),
                Gdx.graphics.getWidth() * 0.2f, Gdx.graphics.getHeight() * 0.8f
        );

        batch.end();
    }

    private void renderRunState(float delta) {
        batch.begin();

        batch.disableBlending();
        batch.draw(desertBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();

        for (Body staticBody : bodyFactoryWrapper.getConstructionsBodies()) {
            ((DrawableBody) staticBody.getUserData()).draw(batch, null, delta);
        }

        symbolsRenderer.renderNumber(batch, AMainBaseModel.getOilBarrels());
        symbolsRenderer.renderNumber(batch, AMainBaseModel.getWaterBarrels(), 0, -40);

        for (Body dynamicBody : bodyFactoryWrapper.getDynamicBodies()) {
            ((DrawableBody) dynamicBody.getUserData()).draw(batch, dynamicBody.getPosition(), delta);
        }

        batch.end();

        worldRenderer.render(bodyFactoryWrapper.getPhysicsWorld(), camera.combined);
    }

    private void renderPauseState(float delta) {
        batch.begin();

        batch.disableBlending();
        batch.draw(desertBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();

        for (Body staticBody : bodyFactoryWrapper.getConstructionsBodies()) {
            ((DrawableBody) staticBody.getUserData()).draw(batch, null, 0f);
        }

        symbolsRenderer.renderNumber(batch, AMainBaseModel.getOilBarrels());
        symbolsRenderer.renderNumber(batch, AMainBaseModel.getWaterBarrels(), 0, -40);

        for (Body dynamicBody : bodyFactoryWrapper.getDynamicBodies()) {
            ((DrawableBody) dynamicBody.getUserData()).draw(batch, dynamicBody.getPosition(), delta);
        }

        batch.end();

        worldRenderer.render(bodyFactoryWrapper.getPhysicsWorld(), camera.combined);
    }

    private void renderExitState(float delta) {

    }

    private void renderOverState(float delta) {

    }

    public BaseFacade getBase(boolean alliedBaseFlag) {
        if (alliedBaseFlag) {
            return alliedBase;
        } else {
            return enemyBase;
        }
    }

    public void hide() {
        //alliedBase.hide();
        enemyBase.hide();
    }

    @Override
    public void update(Observable o, Object arg) {
        gameState = (GameStates) arg;
    }


    private GameStates gameState;
    final private Box2DDebugRenderer worldRenderer;

    final private OrthographicCamera camera;

    final private BitmapFont blackFont;
    final private BodyFactoryWrapper bodyFactoryWrapper;

    final private SymbolsRenderer symbolsRenderer;
    final AlliedBase alliedBase;

    final EnemyBase enemyBase;

    private Texture desertBackground;

    final private SpriteBatch batch;
}

