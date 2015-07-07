package com.liquidresources.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
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

        shipFactoryFacade = new ShipFactoryViewFacade(300, 80);
        pompAnimation = new OilPumpAnimation(0.3f);
        desertBackground = (Texture) ResourceManager.getInstance().get("backgrounds/desert.jpg");
        blackFont = (BitmapFont) ResourceManager.getInstance().get("fonts/blackFont.fnt");

        prepareText = I18NBundleManager.getString("prepare");
    }

    public void show() {
        pompAnimation.create();
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
        if (Gdx.input.isTouched()) { //TODO make something better
            GameWorld.changeWorldState(GameStates.GAME_RUNNING);
        }
        batch.end();
    }

    private void renderRunState() {
        batch.begin();

        batch.disableBlending();
        batch.draw(desertBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();

        shipFactoryFacade.draw(batch, Gdx.graphics.getDeltaTime());
        pompAnimation.draw(batch, 120, 80, Gdx.graphics.getWidth() * 0.2f, Gdx.graphics.getHeight() * 0.2f);
        //mainAI.draw(batch);
        //baseShield.draw(batch, 0.5f);

        batch.end();
    }

    private void renderPauseState() {
        batch.begin();

        batch.disableBlending();
        batch.draw(desertBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.enableBlending();

        shipFactoryFacade.draw(batch, 0f);
        pompAnimation.draw(batch, 120, 80, Gdx.graphics.getWidth() * 0.15f, Gdx.graphics.getHeight() * 0.15f);

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

    private Animator pompAnimation;
    private ShipFactoryViewFacade shipFactoryFacade;
    private Sprite mainAI;
    private Sprite baseShield;

    private BitmapFont blackFont;
    final private String prepareText;

    final private SpriteBatch batch;
}

