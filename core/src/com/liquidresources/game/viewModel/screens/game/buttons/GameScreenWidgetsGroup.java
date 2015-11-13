package com.liquidresources.game.viewModel.screens.game.buttons;

import com.badlogic.gdx.Gdx;
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
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.model.types.ShipTypes;
import com.liquidresources.game.view.windows.GameOptionWindow;
import com.liquidresources.game.viewModel.GameStates;

import java.util.Observable;
import java.util.Observer;

public class GameScreenWidgetsGroup implements Observer {
    public GameScreenWidgetsGroup() {
        final float buttonWidth = Gdx.graphics.getWidth() * 0.1f;
        final float buttonHeight = Gdx.graphics.getWidth() * 0.1f;

        stage = new Stage(
                new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()),
                ((LiquidResources) Gdx.app.getApplicationListener()).getMainBatch()
        );
        Skin skin = (Skin) ResourceManager.getInstance().get("skins/game_screen/gameSkin.json");

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

    public void render() {
        stage.act();
        stage.draw();
    }

    public void initWorldListeners(final GameWorldModel gameWorldModel) {
        try {
            bomberButton.addListener(gameWorldModel.getShipsCreationEventListener(ShipTypes.BOMBER, RelationTypes.ALLY));
            fighterButton.addListener(gameWorldModel.getShipsCreationEventListener(ShipTypes.FIGHTER, RelationTypes.ALLY));
        } catch (TypeNotPresentException err) {
            Gdx.app.error("Ships creation listener error: ", err.getMessage());
        }

        ionShieldButton.addListener(gameWorldModel.getIONShieldListener());
        rocketFire.addListener(gameWorldModel.getRocketFireEventListener());
        optionWindowButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameOptionWindow.setVisible(true);
                gameWorldModel.changeWorldState(GameStates.GAME_PAUSED);
            }
        });
        gameOptionWindow.setListeners(gameWorldModel);

        Gdx.input.setInputProcessor(stage);
    }

    public void dispose() {
        stage.dispose();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Boolean) {
            ionShieldButton.setChecked(false);
        } else if (arg instanceof GameStates) {
            switch ((GameStates) arg) {
                case GAME_PREPARING:
                    setVisible(false);
                    break;
                case GAME_RUNNING:
                    setVisible(true);
                    break;
                case GAME_PAUSED:
                    setVisible(false);
                    break;
            }
        }
    }

    private void setVisible(boolean visible) {
        rocketFire.setVisible(visible);
        fighterButton.setVisible(visible);
        bomberButton.setVisible(visible);
        optionWindowButton.setVisible(visible);
        ionShieldButton.setVisible(visible);
    }


    final private Stage stage;

    final private CheckBox ionShieldButton;
    final private Button optionWindowButton;
    final private Button rocketFire;
    final private Button fighterButton;

    final private Button bomberButton;

    private GameOptionWindow gameOptionWindow;
}
