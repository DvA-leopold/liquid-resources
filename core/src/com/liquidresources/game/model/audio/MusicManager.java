package com.liquidresources.game.model.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.resource.manager.ResourceManager;

import java.util.HashMap;


public class MusicManager {
    private MusicManager() {
        musicEnable = false; // TODO enable music
        musicManagerStarted = false;
        currentMusicType = null;

        musicTable = new HashMap<>();
    }

    public static MusicManager inst() {
        return SingletonHolder.instance;
    }

    public void initialize() {
        mainSample = (Music) ResourceManager.inst().get("audio/music/The Complex.mp3");
        additionSample = (Music) ResourceManager.inst().get("audio/music/Undaunted.mp3");

        musicManagerStarted = true;
        Class<? extends Screen> startScreen = LiquidResources.inst().getScreen().getClass();

        mainSample.setLooping(true);
        additionSample.setLooping(true);

        if (musicTable.containsKey(startScreen)) {
            currentMusicType = musicTable.get(startScreen);
            play(currentMusicType);
        }
    }

    /**
     * every screen should register the <code>MusicType</code> which will playMusic
     *
     * @param sClass    class of the current screen
     * @param musicType type of music sample that will be played
     */
    public void registerMusic(Class<? extends Screen> sClass, MusicTypes musicType) {
        musicTable.put(sClass, musicType);
    }

    public void switchSample(Class<? extends Screen> newScreenClass) {
        if (!musicManagerStarted) return;

        if (musicTable.containsKey(newScreenClass)) {
            MusicTypes newMusicType = musicTable.get(newScreenClass);
            if (currentMusicType != newMusicType){
                stop(currentMusicType);
                play(newMusicType);
                currentMusicType = newMusicType;
            }
        }
    }

    public void pauseMusic() {
        MusicTypes pauseMusicType = musicTable.get(LiquidResources.inst().getScreen().getClass());
        pause(pauseMusicType);
    }

    public void resumeMusic() {
        MusicTypes resumeMusicType = musicTable.get(LiquidResources.inst().getScreen().getClass());
        play(resumeMusicType);
    }

    public void stopMusic() {
        MusicTypes stopMusicType = musicTable.get(LiquidResources.inst().getScreen().getClass());
        stop(stopMusicType);
    }

    private void play(MusicTypes playMusicType) {
        if (musicEnable && playMusicType != null) {
            switch (playMusicType) {
                case MAIN_MUSIC:
                    if (!mainSample.isPlaying()) {
                        mainSample.play();
                    }
                    break;
                case ADDITION_MUSIC:
                    if (!additionSample.isPlaying()) {
                        additionSample.play();
                    }
                    break;
                default:
                    Gdx.app.error("music", "this music sample are not registered");
                    break;
            }
        }
    }

    private void pause(MusicTypes pauseMusicType) {
        if (pauseMusicType != null) {
            switch (pauseMusicType) {
                case MAIN_MUSIC:
                    if (mainSample.isPlaying()) {
                        mainSample.pause();
                    }
                    break;
                case ADDITION_MUSIC:
                    if (additionSample.isPlaying()) {
                        additionSample.pause();
                    }
                    break;
                default:
                    Gdx.app.error("music", "this music sample are not registered");
                    break;
            }
        }
    }

    private void stop(MusicTypes stopMusicType) {
        if (stopMusicType != null) {
            switch (stopMusicType) {
                case MAIN_MUSIC:
                    if (mainSample.isPlaying()) {
                        mainSample.stop();
                    }
                    break;
                case ADDITION_MUSIC:
                    if (additionSample.isPlaying()) {
                        additionSample.stop();
                    }
                    break;
                default:
                    Gdx.app.error("music", "this music sample are not registered");
                    break;
            }
        }
    }

    public void dispose() {
        ResourceManager.inst().unloadSection("audio");
        mainSample.dispose();
        additionSample.dispose();
    }

    public boolean isMusicEnable() {
        return musicEnable;
    }

    public void onoff() {
        musicEnable = !musicEnable;
    }


    public enum MusicTypes {
        MAIN_MUSIC,
        ADDITION_MUSIC
    }

    static private class SingletonHolder {
        static final MusicManager instance = new MusicManager();
    }


    private MusicTypes currentMusicType;

    private boolean musicManagerStarted;

    private Music mainSample;
    private Music additionSample;

    final private HashMap<Class<? extends Screen>, MusicTypes> musicTable;

    private boolean musicEnable;
}
