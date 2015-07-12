package com.liquidresources.game.viewModel.screens.game.buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.liquidresources.game.model.GameWorld;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.windows.GameOptionWindow;
import com.liquidresources.game.viewModel.GameStates;
import com.liquidresources.game.viewModel.WidgetsGroup;

public class GameScreenWidgetsGroup implements WidgetsGroup {
    public GameScreenWidgetsGroup(final Batch batch) {
        stage = new Stage(new StretchViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()), batch);
        Skin skin = (Skin) ResourceManager.
                getInstance().get("skins/game_screen/gameSkin.json");

        optionWindowButton = new Button(skin, "optionButton");
        optionWindowButton.setSize(Gdx.graphics.getWidth() * 0.1f, Gdx.graphics.getWidth() * 0.1f);
        optionWindowButton.setPosition(
                Gdx.graphics.getWidth() - optionWindowButton.getWidth(),
                Gdx.graphics.getHeight() - optionWindowButton.getHeight()
        );

        gameOptionWindow = new GameOptionWindow("", skin);

        stage.addActor(optionWindowButton);
        stage.addActor(gameOptionWindow);
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
                GameWorld.changeWorldState(GameStates.GAME_PAUSED);
            }
        });

        Gdx.input.setInputProcessor(stage);
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

    private GameOptionWindow gameOptionWindow;
}
