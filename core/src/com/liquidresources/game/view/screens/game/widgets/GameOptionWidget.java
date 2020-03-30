package com.liquidresources.game.view.screens.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.utils.GameStateHolder;

import static com.liquidresources.game.model.GameStates.GAME_PAUSED;


final class GameOptionWidget extends Table {
    GameOptionWidget() {
        final float buttonWidth = Gdx.graphics.getWidth() * 0.1f;
        final float buttonHeight = Gdx.graphics.getWidth() * 0.1f;

        final Skin skin = (Skin) ResourceManager.inst().get("ui_skin/gameSkin.json");

        final GameOptionWindow gameOptionWindow = new GameOptionWindow("Settings", skin);

        Button optionWindowButton = new Button(skin, "optionButton");
        optionWindowButton.setSize(buttonWidth, buttonHeight);
        optionWindowButton.setPosition(
                Gdx.graphics.getWidth() - optionWindowButton.getWidth(),
                Gdx.graphics.getHeight() - optionWindowButton.getHeight()
        );
        optionWindowButton.setVisible(false);

        optionWindowButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameStateHolder.changeGameState(GAME_PAUSED);
                gameOptionWindow.addAction(Actions.sequence(Actions.show()));
            }
        });

        add(optionWindowButton);
        add(gameOptionWindow);
    }



}
