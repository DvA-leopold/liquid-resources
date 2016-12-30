package com.liquidresources.game.view.screens.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.liquidresources.game.model.GameStates;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.utils.GSObserver;
import com.liquidresources.game.utils.GameStateHolder;

import static com.liquidresources.game.model.GameStates.GAME_PAUSED;


public class GameScreenWidgetsGroup implements GSObserver {
    public GameScreenWidgetsGroup(final Batch batch) {
        final float buttonWidth = Gdx.graphics.getWidth() * 0.1f;
        final float buttonHeight = Gdx.graphics.getWidth() * 0.1f;

        Skin skin = (Skin) ResourceManager.instance().get("ui_skin/gameSkin.json");

        optionWindowButton = new Button(skin, "optionButton");
        optionWindowButton.setSize(buttonWidth, buttonHeight);
        optionWindowButton.setPosition(
                Gdx.graphics.getWidth() - optionWindowButton.getWidth(),
                Gdx.graphics.getHeight() - optionWindowButton.getHeight()
        );
        optionWindowButton.setVisible(false);

        ionShieldButton = new CheckBox("", skin, "shieldAction");
        ionShieldButton.setVisible(false);
        missileFire = new Button(skin, "rocketAction");
        missileFire.setVisible(false);
        bulletFire = new Button(skin, "fightersAction");
        bulletFire.setVisible(false);

        Table actionTable = new Table();
        actionTable.setPosition(Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.05f);
        actionTable.add(ionShieldButton).width(buttonWidth).height(buttonHeight).pad(10);
        actionTable.add(missileFire).width(buttonWidth).height(buttonHeight).pad(10);
        actionTable.add(bulletFire).width(buttonWidth).height(buttonHeight).pad(10);

        gameOptionWindow = new GameOptionWindow("", skin);

        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        stage.addActor(optionWindowButton);
        stage.addActor(gameOptionWindow);
        stage.addActor(actionTable);

        actionTable.debug();
        setVisible(true);
    }

    public void render() {
        stage.act();
        stage.draw();
    }

    public void setListeners(final ClickListener ionShieldClickListener,
                             final ClickListener fireMissileClickListener,
                             final ClickListener fireBulletClickListener) {
        bulletFire.addListener(fireBulletClickListener);

        ionShieldButton.addListener(ionShieldClickListener);
        missileFire.addListener(fireMissileClickListener);

        optionWindowButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameStateHolder.changeGameState(GAME_PAUSED);
                gameOptionWindow.addAction(Actions.sequence(Actions.show()));
            }
        });
        gameOptionWindow.setListeners();

        Gdx.input.setInputProcessor(stage);
    }

    public void dispose() {
        stage.dispose();
    }

    private void setVisible(boolean visible) {
        missileFire.setVisible(visible);
        bulletFire.setVisible(visible);
        ionShieldButton.setVisible(visible);
        optionWindowButton.setVisible(visible);
    }

    static public void setIonShieldChecked() { // TODO refactor this
        ionShieldButton.setChecked(false);
    }

    @Override
    public void notify(GameStates newGameState) {
        switch (newGameState) {
            case GAME_RUNNING:
                missileFire.setDisabled(false);
                bulletFire.setDisabled(false);
                ionShieldButton.setDisabled(false);
                break;
            case GAME_PAUSED:
                missileFire.setDisabled(true);
                bulletFire.setDisabled(true);
                ionShieldButton.setDisabled(true);
                break;
        }
    }


    final private Stage stage;
    static private CheckBox ionShieldButton; // TODO refactor this

    final private Button optionWindowButton;
    final private Button missileFire;
    final private Button bulletFire;

    final private GameOptionWindow gameOptionWindow;
}
