package com.liquidresources.game.utils;

import com.liquidresources.game.model.GameStates;

import java.util.Vector;


final public class GameStateHolder {
    static {
        dispose();
    }

    static public void addObserver(Observer observer) {
        OBSERVERS_LIST.add(observer);
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
        OBSERVERS_LIST = new Vector<>();
    }

    static private void notifyObservers() {
        for (Observer observer : OBSERVERS_LIST) {
            observer.notify(gameState);
        }
    }


    static private Vector<Observer> OBSERVERS_LIST;
    static private GameStates gameState;
}
