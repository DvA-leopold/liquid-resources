package com.liquidresources.game.model.music.manager;

import com.badlogic.gdx.audio.Music;
import com.liquidresources.game.model.resource.manager.ResourceManager;

public class MusicManager {
    public MusicManager() {
        isMusicEnable = true;
    }

    public void play() {
        if (isMusicEnable) {

        }
    }

    public void pause() {

    }

    public void stop() {

    }

    public void initialize() {
        mainSample = (Music) ResourceManager.getInstance().get("audio/music/The Complex.mp3");
        additionSample = (Music) ResourceManager.getInstance().get("audio/music/Undaunted.mp3");
    }

    public void dispose() {
        ResourceManager.getInstance().unloadSection("audio/music");
        mainSample.dispose();
        additionSample.dispose();
    }


    private boolean isMusicEnable;

    private Music mainSample = null;
    private Music additionSample = null;
}
