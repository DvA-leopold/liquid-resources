package com.liquidresources.game.utils;

import com.liquidresources.game.model.GameStates;

public interface GSObserver {
    void notify(GameStates newGameState);
}
