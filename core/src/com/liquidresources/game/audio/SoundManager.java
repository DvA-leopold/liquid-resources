package com.liquidresources.game.audio;

public class SoundManager {
    private SoundManager() {
        isSoundEnable = true;
    }


    public static SoundManager instance() {
        return SingletonHolder.instance;
    }


    public boolean isSoundEnable() {
        return isSoundEnable;
    }


    static private class SingletonHolder {
        static final SoundManager instance = new SoundManager();
    }

    private boolean isSoundEnable;
}
