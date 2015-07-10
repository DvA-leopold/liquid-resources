package com.liquidresources.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.liquidresources.game.model.GameWorld;
import com.liquidresources.game.model.i18N.manager.I18NBundleManager;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.animation.Animator;
import com.liquidresources.game.view.animation.oilpump.OilPumpAnimation;
import com.liquidresources.game.view.objects.ShipFactoryViewFacade;
import com.liquidresources.game.viewModel.GameStates;

public class GameRenderer {
    public GameRenderer(final SpriteBatch batch) {
        this.batch = batch;

        desertBackground = (Texture) ResourceManager.getInstance().get("backgrounds/desert.jpg");
        blackFont = (BitmapFont) ResourceManager.getInstance().get("fonts/blackFont.fnt");

        float floatingXPosition = 120;
        float floatingYPosition = 80;
        final float graphicsWidth = Gdx.graphics.getWidth() * 0.15f;
        final float graphicsHeight = Gdx.graphics.getHeight() * 0.15f;
        final float buildingsPositionDelimiter = Gdx.graphics.getWidth() * 0.005f;

        oilPompFacade = new OilPumpAnimation(0.3f,
                floatingXPosition, floatingYPosition,
                graphicsWidth, graphicsHeight
        );

        floatingXPosition += oilPompFacade.getWidth() + buildingsPositionDelimiter;
        mainAI = new Sprite((Texture) ResourceManager.getInstance().get("buildings/mainAI.png"));
        mainAI.setPosition(floatingXPosition, floatingYPosition);
        mainAI.setSize(graphicsWidth, graphicsHeight * 2);

        floatingXPosition += mainAI.getWidth() + buildingsPositionDelimiter;
        shipFactoryFacade = new ShipFactoryViewFacade(
                floatingXPosition, floatingYPosition,
                graphicsWidth, graphicsHeight
        );

        prepareText = I18NBundleManager.getString("prepare");
    }

    public void show() {
        oilPompFacade.create(Animation.PlayMode.LOOP_PINGPONG);
        shipFactoryFacade.startEffect();
    }

    public void render() {
        switch (GameWorld.getWorldState()) {
            case GAME_PREPARING:
                renderPreparingState();
                break;
            case GAME_RUNNING:
                renderRunState();
                break;
            case GAME_PAUSED:
                renderPauseState();
                break;
            case GAME_EXIT:
                renderExitState();
                break;
            case GAME_OVER:
                renderOverState();
                break;
        }
    }

    private void renderPreparingState() {
        batch.begin();

        batch.disableBlending();
        batch.draw(desertBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();

        blackFont.draw(batch, prepareText,
                (Gdx.graphics.getWidth() - prepareText.length()) / 2,
                Gdx.graphics.getHeight() * 0.8f);

        batch.end();

        if (Gdx.input.isTouched()) { //TODO make something better
            GameWorld.changeWorldState(GameStates.GAME_RUNNING);
        }
    }

    private void renderRunState() {
        batch.begin();

        batch.disableBlending();
        batch.draw(desertBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();

        oilPompFacade.draw(batch);
        shipFactoryFacade.draw(batch, Gdx.graphics.getDeltaTime());
        mainAI.draw(batch);
        //baseShield.draw(batch, 0.5f);

        batch.end();
    }

    private void renderPauseState() {
        batch.begin();

        batch.disableBlending();
        batch.draw(desertBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();

        oilPompFacade.draw(batch);
        shipFactoryFacade.draw(batch, 0f);
        mainAI.draw(batch);

        batch.end();
    }

    private void renderExitState() {

    }

    private void renderOverState() {

    }

    public void dispose() {
        shipFactoryFacade.dispose();
    }


    private Texture desertBackground;

    private Animator oilPompFacade;
    private ShipFactoryViewFacade shipFactoryFacade;
    private Sprite mainAI;
    private Sprite baseShield;

    private BitmapFont blackFont;
    final private String prepareText;

    final private SpriteBatch batch;
}

