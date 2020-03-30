package com.liquidresources.game.view.screens.game.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.i18n.manager.I18NBundleManager;
import com.liquidresources.game.model.audio.MusicManager;
import com.liquidresources.game.model.audio.SoundManager;
import com.liquidresources.game.model.GameStates;
import com.liquidresources.game.utils.GameStateHolder;
import com.liquidresources.game.view.screens.menu.MainMenuScreen;


final class GameOptionWindow extends Window {
    GameOptionWindow(String title, Skin skin) {
        super(title, skin);
        final float buttonHeight = Gdx.graphics.getHeight() * 0.1f;
        final float buttonWidth = Gdx.graphics.getWidth() * 0.3f;

        setSize(Gdx.graphics.getWidth() * 0.35f, Gdx.graphics.getHeight());
        setPosition(Gdx.graphics.getWidth() - Gdx.graphics.getWidth() * 0.2f, 0);
        setModal(true);
        setVisible(false);

        musicButton = new CheckBox("", skin, "musicCheckBox");
        musicButton.setChecked(!MusicManager.inst().isMusicEnable());
        soundButton = new CheckBox("", skin, "soundCheckBox");
        soundButton.setChecked(!SoundManager.instance().isSoundEnable());

        exitButton = new TextButton(I18NBundleManager.getString("exit"), skin, "exitButton");
        resumeButton = new Button(skin, "resumeButton");

        add(musicButton).width(buttonWidth * 0.5f).height(buttonHeight).padTop(Gdx.graphics.getHeight() * 0.4f);
        add(soundButton).width(buttonWidth * 0.5f).height(buttonHeight).padTop(Gdx.graphics.getHeight() * 0.4f);
        row();
        add(exitButton).width(buttonWidth).height(buttonHeight).colspan(2);
        row();
        add(resumeButton).width(buttonWidth * 0.3f).height(buttonWidth * 0.3f).colspan(2).expand().bottom().right();

        setListeners();
    }

    private void setListeners() {
        musicButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicManager.inst().onoff();
                if (MusicManager.inst().isMusicEnable()) {
                    MusicManager.inst().resumeMusic();
                } else {
                    MusicManager.inst().stopMusic();
                }
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
                GameStateHolder.changeGameState(GameStates.GAME_EXIT);
                LiquidResources.inst().setScreen(new MainMenuScreen());
            }
        });

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
                GameStateHolder.changeGameState(GameStates.GAME_RUNNING);
            }
        });
    }


    final private TextButton exitButton;
    final private Button resumeButton;
    final private CheckBox musicButton, soundButton;
}
