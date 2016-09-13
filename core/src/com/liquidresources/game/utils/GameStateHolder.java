package com.liquidresources.game.utils;

import com.liquidresources.game.model.GameStates;

import java.util.Vector;


final public class GameStateHolder {
    static {
        gameState = GameStates.GAME_PREPARING;
        OBSERVERS_LIST = new Vector<>();
    }

    static public void addObserver(GSObserver GSObserver) {
        OBSERVERS_LIST.add(GSObserver);
    }

    static public void changeGameState(GameStates newGameState) {
        gameState = newGameState;
        notifyObservers();
    }

    static public GameStates getGameState() {
        return gameState;
    }

    static public void dispose() {
        gameState = GameStates.GAME_PREPARING;
        OBSERVERS_LIST.clear();
    }

    static private void notifyObservers() {
        for (GSObserver GSObserver : OBSERVERS_LIST) {
            GSObserver.notify(gameState);
        }
    }


    static final private Vector<GSObserver> OBSERVERS_LIST;
    static private GameStates gameState;
}
