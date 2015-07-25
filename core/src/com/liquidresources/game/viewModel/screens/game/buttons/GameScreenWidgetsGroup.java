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
import com.liquidresources.game.model.game.world.base.MainAI;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.windows.GameOptionWindow;
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

        ionShieldButton = new CheckBox("", skin, "shieldAction");
        ionShieldButton.setChecked(MainAI.getShieldStatus());
        rocketFire = new Button(skin, "rocketAction");
        bomberButton = new Button(skin, "bombersAction");
        fighterButton = new Button(skin, "fightersAction");

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
    public void setListeners() {
        gameOptionWindow.setListeners();
        optionWindowButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameOptionWindow.setVisible(true);
                GameWorldModel.changeWorldState(GameStates.GAME_PAUSED);
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    public void addIonShieldListener(EventListener listener) {
        if (!ionShieldButton.addListener(listener)) {
            System.err.println("ion shield listener do not set");
        }
    }

    public void addRocketFireListener(EventListener listener) {
        if (!rocketFire.addListener(listener)) {
            System.err.println("rocketFire listener do not set");
        }
    }

    public void addBombersButtonListener(EventListener listener) {
        if (!bomberButton.addListener(listener)) {
            System.err.println("bombers listener do not set");
        }
    }

    public void addFightersButtonListener(EventListener listener) {
        if (!fighterButton.addListener(listener)) {
            System.err.println("fighters listener do not set");
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

    @Override
    public void setVisible(boolean visible) {
        optionWindowButton.setVisible(visible);
        gameOptionWindow.setVisible(visible);
    }


    final private Stage stage;

    private Button optionWindowButton;

    static private CheckBox ionShieldButton; // TODO сделать возможность нормального переключения состояния кнопки
    private Button rocketFire;
    private Button fighterButton;
    private Button bomberButton;

    private GameOptionWindow gameOptionWindow;
}
