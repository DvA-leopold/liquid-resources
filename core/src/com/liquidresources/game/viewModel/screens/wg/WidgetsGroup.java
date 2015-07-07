package com.liquidresources.game.viewModel.screens.wg;

import com.badlogic.gdx.scenes.scene2d.Stage;

public interface WidgetsGroup {
    void render();
    void setListeners();
    void dispose();
    Stage getStage();
    void setVisible(boolean visible);
}
