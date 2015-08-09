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
import com.liquidresources.game.model.game.world.base.MainAIModel;
import com.liquidresources.game.model.i18n.manager.I18NBundleManager;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.viewModel.bodies.udata.bariers.Ground;
import com.liquidresources.game.viewModel.bodies.udata.buildings.OilPumpFacade;
import com.liquidresources.game.viewModel.bodies.udata.buildings.IonShield;
import com.liquidresources.game.viewModel.bodies.udata.buildings.MainAI;
import com.liquidresources.game.viewModel.bodies.udata.buildings.ShipFactoryViewFacade;
import com.liquidresources.game.view.symbols.SymbolsRenderer;
import com.liquidresources.game.viewModel.GameStates;

public class GameRenderer {
    public GameRenderer(final Vector2 initCoords, final BodyFactoryWrapper bodyFactoryWrapper) {
        this.bodyFactoryWrapper = bodyFactoryWrapper;
        final Vector2 endCoords = new Vector2(initCoords);

        worldRenderer = new Box2DDebugRenderer();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        this.batch = ((LiquidResources) Gdx.app.getApplicationListener()).getMainBatch();

        final Vector2 graphicSize = new Vector2(Gdx.graphics.getWidth() * 0.08f, Gdx.graphics.getHeight() * 0.08f);
        final float buildingsPositionDelimiter = Gdx.graphics.getWidth() * 0.005f;

        desertBackground = (Texture) ResourceManager.getInstance().get("backgrounds/desert.jpg");
        blackFont = (BitmapFont) ResourceManager.getInstance().get("fonts/blackFont.fnt");

        symbolsRenderer = new SymbolsRenderer(0, Gdx.graphics.getHeight() - 60, 20, 45); // TODO dynamic size

        Ground ground = new Ground(initCoords);
        bodyFactoryWrapper.createBody(ground, true);

        oilPompFacade = new OilPumpFacade(0.3f, endCoords, graphicSize, Animation.PlayMode.LOOP_PINGPONG);
        bodyFactoryWrapper.createBody(oilPompFacade, true);

        endCoords.x += oilPompFacade.getSize().x + buildingsPositionDelimiter;
        mainAI = new MainAI(endCoords, graphicSize);
        bodyFactoryWrapper.createBody(mainAI, true);

        endCoords.x += mainAI.getSize().x + buildingsPositionDelimiter;
        shipFactoryFacade = new ShipFactoryViewFacade(endCoords, graphicSize);
        bodyFactoryWrapper.createBody(shipFactoryFacade, true);

        initCoords.x -= buildingsPositionDelimiter;
        endCoords.x -= mainAI.getSize().x * 0.5f;
        IonShield baseShield = new IonShield(initCoords, endCoords, graphicSize);
        bodyFactoryWrapper.createBody(baseShield, true);
    }

    public void show() {
        shipFactoryFacade.getSmokeParticles().startEffect();
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

        if (Gdx.input.justTouched()) {
            GameWorldModel.changeWorldState(GameStates.GAME_RUNNING);
        }
    }

    private void renderRunState(float delta) {
        batch.begin();

        batch.disableBlending();
        batch.draw(desertBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();

        for (Body staticBody : bodyFactoryWrapper.getConstructionsBodies()) {
            ((DrawableBody) staticBody.getUserData()).draw(batch, null, delta);
        }

        symbolsRenderer.renderNumber(batch, MainAIModel.getOilBarrels());
        symbolsRenderer.renderNumber(batch, MainAIModel.getWaterBarrels(), 0, -40);

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

        symbolsRenderer.renderNumber(batch, MainAIModel.getOilBarrels());
        symbolsRenderer.renderNumber(batch, MainAIModel.getWaterBarrels(), 0, -40);

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

    public Vector2 getShipFactoryPosition() {
        return shipFactoryFacade.getShipFactoryPosition();
    }

    public Vector2 getMainAIPosition() {
        return mainAI.getPosition();
    }

    public void hide() {
        shipFactoryFacade.getSmokeParticles().resetEffect();
        shipFactoryFacade.getSmokeParticles().stopEffect();
    }


    final private Box2DDebugRenderer worldRenderer;
    final private OrthographicCamera camera;

    final private BitmapFont blackFont;

    final private BodyFactoryWrapper bodyFactoryWrapper;
    final private SymbolsRenderer symbolsRenderer;
    final private OilPumpFacade oilPompFacade;
    final private ShipFactoryViewFacade shipFactoryFacade;
    final private MainAI mainAI;

    private Texture desertBackground;

    final private SpriteBatch batch;
}

