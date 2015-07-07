package com.liquidresources.game.view.windows;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.liquidresources.game.model.GameWorld;
import com.liquidresources.game.model.i18N.manager.I18NBundleManager;
import com.liquidresources.game.viewModel.GameStates;

public class GameOptionWindow extends Window {
    public GameOptionWindow(String title, Skin skin) {
        super(title, skin);
        float   buttonHeight = Gdx.graphics.getHeight() * 0.1f,
                buttonWidth = Gdx.graphics.getWidth() * 0.3f;

        setSize(Gdx.graphics.getWidth() * 0.35f, Gdx.graphics.getHeight());
        setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() * 0.2f, 0);
        setModal(true);
        setVisible(false);

        musicButton = new CheckBox("", skin, "musicCheckBox");
        soundButton = new CheckBox("", skin, "soundCheckBox");

        exitButton = new TextButton(I18NBundleManager.getString("exit"), skin, "exitButton");
        resumeButton = new Button(skin, "resumeButton");

        this.add(musicButton).width(buttonWidth * 0.5f).height(buttonHeight).padTop(Gdx.graphics.getHeight() * 0.4f);
        this.add(soundButton).width(buttonWidth * 0.5f).height(buttonHeight).padTop(Gdx.graphics.getHeight() * 0.4f);
        this.row();
        this.add(exitButton).width(buttonWidth).height(buttonHeight).colspan(2);
        this.row();
        this.add(resumeButton).width(buttonWidth * 0.3f).height(buttonWidth * 0.3f).colspan(2).expand().bottom().right();
        this.debug();
    }

    public void setListeners() {
        musicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO switch music
            }
        });

        soundButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO switch sound
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                //TODO сохранить прогресс перед выходом
                //((LiquidResources) Gdx.app.getApplicationListener()).setScreen(new MainMenuScreen());
            }
        });

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
                GameWorld.changeWorldState(GameStates.GAME_RUNNING);
            }
        });
    }


    private TextButton exitButton;
    private Button resumeButton;
    private CheckBox musicButton, soundButton;
}
