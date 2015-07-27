package com.liquidresources.game.viewModel.screens.game.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.GameWorldModel;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.windows.GameOptionWindow;
import com.liquidresources.game.viewModel.ActionsListener;
import com.liquidresources.game.viewModel.GameStates;
import com.liquidresources.game.viewModel.WidgetsGroup;

public class GameScreenWidgetsGroup implements WidgetsGroup {
    public GameScreenWidgetsGroup() {
        final float buttonWidth = Gdx.graphics.getWidth() * 0.1f;
        final float buttonHeight = Gdx.graphics.getWidth() * 0.1f;

        stage = new Stage(
                new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()),
                ((LiquidResources) Gdx.app.getApplicationListener()).getMainBatch()
        );
        Skin skin = (Skin) ResourceManager.
                getInstance().get("skins/game_screen/gameSkin.json");

        optionWindowButton = new Button(skin, "optionButton");
        optionWindowButton.setSize(buttonWidth, buttonHeight);
        optionWindowButton.setPosition(
                Gdx.graphics.getWidth() - optionWindowButton.getWidth(),
                Gdx.graphics.getHeight() - optionWindowButton.getHeight()
        );
        optionWindowButton.setVisible(false);

        ionShieldButton = new CheckBox("", skin, "shieldAction");
        ionShieldButton.setVisible(false);
        rocketFire = new Button(skin, "rocketAction");
        rocketFire.setVisible(false);
        bomberButton = new Button(skin, "bombersAction");
        bomberButton.setVisible(false);
        fighterButton = new Button(skin, "fightersAction");
        fighterButton.setVisible(false);

        Table actionTable = new Table();
        actionTable.setPosition(Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.1f);
        actionTable.add(ionShieldButton).width(buttonWidth).height(buttonHeight).pad(10);
        actionTable.add(rocketFire).width(buttonWidth).height(buttonHeight).pad(10);
        actionTable.add(bomberButton).width(buttonWidth).height(buttonHeight).pad(10);
        actionTable.add(fighterButton).width(buttonWidth).height(buttonHeight).pad(10);

        gameOptionWindow = new GameOptionWindow("", skin);

        stage.addActor(optionWindowButton);
        stage.addActor(gameOptionWindow);
        stage.addActor(actionTable);

        actionTable.debug();
    }

    @Override
    public void render() {
        stage.act();
        stage.draw();
    }

    @Override
    public void addListener(EventListener listener, ActionsListener action) {
        switch (action) {
            case ION_SHIELD_ACTION:
                ionShieldButton.addListener(listener);
                break;
            case ROCKET_FIRE_ACTION:
                rocketFire.addListener(listener);
                break;
            case CREATE_BOMBER_ACTION:
                bomberButton.addListener(listener);
                break;
            case CREATE_FIGHTER_ACTION:
                fighterButton.addListener(listener);
                break;
            case ADDITION_INIT_ACTION:
                gameOptionWindow.setListeners();
                optionWindowButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        gameOptionWindow.setVisible(true);
                        setButtonVisible(false);
                        GameWorldModel.changeWorldState(GameStates.GAME_PAUSED);
                    }
                });

                Gdx.input.setInputProcessor(stage);
                break;
        }
    }

    public static void setIONChecked(boolean isChecked) {
        ionShieldButton.setChecked(isChecked);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }

    @Override
    public Stage getStage() {
        return stage;
    }

    public static void setButtonVisible(boolean visible) {
        rocketFire.setVisible(visible);
        fighterButton.setVisible(visible);
        bomberButton.setVisible(visible);
        optionWindowButton.setVisible(visible);
        ionShieldButton.setVisible(visible);
    }


    final private Stage stage;

    //TODO try to make non static buttons
    static private Button optionWindowButton;

    static private CheckBox ionShieldButton;
    static private Button rocketFire;
    static private Button fighterButton;
    static private Button bomberButton;

    private GameOptionWindow gameOptionWindow;
}
