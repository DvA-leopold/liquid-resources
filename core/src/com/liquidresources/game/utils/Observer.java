package com.liquidresources.game.utils;

import com.liquidresources.game.model.GameStates;

public interface Observer {
    void notify(GameStates newGameState);
}
