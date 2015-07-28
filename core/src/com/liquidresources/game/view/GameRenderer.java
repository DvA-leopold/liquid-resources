package com.liquidresources.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.GameWorldModel;
import com.liquidresources.game.model.game.world.base.MainAI;
import com.liquidresources.game.model.i18n.manager.I18NBundleManager;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.animation.oilpump.OilPumpAnimation;
import com.liquidresources.game.viewModel.bodies.udata.buildings.BaseShieldView;
import com.liquidresources.game.viewModel.bodies.udata.buildings.MainAIView;
import com.liquidresources.game.viewModel.bodies.udata.buildings.ShipFactoryViewFacade;
import com.liquidresources.game.view.symbols.SymbolsRenderer;
import com.liquidresources.game.viewModel.GameStates;

public class GameRenderer {
    public GameRenderer(Vector2 initCoords, final BodyFactoryWrapper bodyFactoryWrapper) {
        this.bodyFactoryWrapper = bodyFactoryWrapper;

        worldRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.batch = ((LiquidResources) Gdx.app.getApplicationListener()).getMainBatch();

        final float graphicsWidth = Gdx.graphics.getWidth() * 0.15f;
        final float graphicsHeight = Gdx.graphics.getHeight() * 0.15f;
        final float buildingsPositionDelimiter = Gdx.graphics.getWidth() * 0.005f;

        desertBackground = (Texture) ResourceManager.getInstance().get("backgrounds/desert.jpg");
        blackFont = (BitmapFont) ResourceManager.getInstance().get("fonts/blackFont.fnt");

        symbolsRenderer = new SymbolsRenderer(0, Gdx.graphics.getHeight() - 60, 20, 45); // TODO dynamic size

        oilPompFacade = new OilPumpAnimation(0.3f,
                initCoords.x, initCoords.y,
                graphicsWidth, graphicsHeight,
                Animation.PlayMode.LOOP_PINGPONG
        );
        bodyFactoryWrapper.createBody(oilPompFacade, true);

        initCoords.x += oilPompFacade.getWidth() + buildingsPositionDelimiter;
        mainAIView = new MainAIView(initCoords, graphicsWidth, graphicsHeight);
        bodyFactoryWrapper.createBody(mainAIView, true);

        initCoords.x += mainAIView.getWidth() + buildingsPositionDelimiter;
        shipFactoryFacade = new ShipFactoryViewFacade(initCoords.x, initCoords.y, graphicsWidth, graphicsHeight);
        bodyFactoryWrapper.createBody(shipFactoryFacade, true);
    }

    public void show() {
        shipFactoryFacade.startEffect();
    }

    public void render(float delta) {
        switch (GameWorldModel.getWorldState()) {
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

        if (Gdx.input.isTouched()) { //TODO change world state to run state
            GameWorldModel.changeWorldState(GameStates.GAME_RUNNING);
        }
    }

    private void renderRunState(float delta) {
        batch.begin();

        batch.disableBlending();
        batch.draw(desertBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();

        for (Body staticBody : bodyFactoryWrapper.getStaticConstructions()) {
            ((DrawableBody) staticBody.getUserData()).draw(batch, null, delta);
        }

        symbolsRenderer.renderNumber(batch, MainAI.getOilBarrels());
        symbolsRenderer.renderNumber(batch, MainAI.getWaterBarrels(), 0, -40);

        for (Body dynamicBody : bodyFactoryWrapper.getDynamicObjects()) {
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

        for (Body staticBody : bodyFactoryWrapper.getStaticConstructions()) {
            ((DrawableBody) staticBody.getUserData()).draw(batch, null, 0f);
        }

        symbolsRenderer.renderNumber(batch, MainAI.getOilBarrels());
        symbolsRenderer.renderNumber(batch, MainAI.getWaterBarrels(), 0, -40);

        for (Body dynamicBody : bodyFactoryWrapper.getDynamicObjects()) {
            ((DrawableBody) dynamicBody.getUserData()).draw(batch, dynamicBody.getPosition(), delta);
        }

        batch.end();

        worldRenderer.render(bodyFactoryWrapper.getPhysicsWorld(), camera.combined);
    }

    private void renderExitState(float delta) {

    }

    private void renderOverState(float delta) {

    }

    public Vector2 getShipFactoryPosition() {
        return shipFactoryFacade.getShipFactoryPosition();
    }

    public Vector2 getMainAIPosition() {
        return mainAIView.getPosition();
    }

    public void dispose() {
        shipFactoryFacade.dispose();
    }


    private final BodyFactoryWrapper bodyFactoryWrapper;

    private SymbolsRenderer symbolsRenderer;
    final private Box2DDebugRenderer worldRenderer;
    final private OrthographicCamera camera;

    private Texture desertBackground;

    private OilPumpAnimation oilPompFacade;
    private ShipFactoryViewFacade shipFactoryFacade;

    private BaseShieldView baseShield;
    private MainAIView mainAIView;
    private BitmapFont blackFont;

    final private SpriteBatch batch;
}

