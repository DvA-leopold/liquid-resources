package com.liquidresources.game.viewModel;

import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public interface WidgetsGroup {
    void render();
    void addListener(EventListener eventListener, Actions actions);
    void dispose();
    Stage getStage();
}
