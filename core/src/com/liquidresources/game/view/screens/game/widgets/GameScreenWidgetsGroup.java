package com.liquidresources.game.view.screens.game.widgets;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.liquidresources.game.LiquidResources;
import com.liquidresources.game.model.GameStates;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.utils.GameStateHolder;
import com.liquidresources.game.utils.Observer;


public class GameScreenWidgetsGroup extends Entity implements Observer {
    public GameScreenWidgetsGroup() {
        final float buttonWidth = Gdx.graphics.getWidth() * 0.1f;
        final float buttonHeight = Gdx.graphics.getWidth() * 0.1f;

        final Stage stage = LiquidResources.inst().getStage();
        final Skin skin = (Skin) ResourceManager.inst().get("ui_skin/gameSkin.json");

        ionShieldButton = new CheckBox("", skin, "shield_action");
        missileFire = new Button(skin, "missile_fire");
        bulletFire = new Button(skin, "bullet_fire");
        laserFire = new Button(skin, "laser_fire");

        final Table actionTable = new Table();
        actionTable.setPosition(Gdx.graphics.getWidth() * 0.5f, Gdx.graphics.getHeight() * 0.05f);
        actionTable.add(ionShieldButton).width(buttonWidth).height(buttonHeight).pad(10);
        actionTable.add(missileFire).width(buttonWidth).height(buttonHeight).pad(10);
        actionTable.add(bulletFire).width(buttonWidth).height(buttonHeight).pad(10);
        actionTable.add(laserFire).width(buttonWidth).height(buttonHeight).pad(10);

        stage.addActor(new GameOptionWidget());
        stage.addActor(actionTable);

        GameStateHolder.addObserver(this);
    }

    public void setListeners(final ClickListener ionShieldClickListener,
                             final ClickListener fireMissileClickListener,
                             final ClickListener fireBulletClickListener,
                             final ClickListener laserFireClickListener) {
        ionShieldButton.addListener(ionShieldClickListener);
        missileFire.addListener(fireMissileClickListener);
        bulletFire.addListener(fireBulletClickListener);
        laserFire.addListener(laserFireClickListener);
    }

    @Override
    public void notify(GameStates newGameState) {
        switch (newGameState) {
            case GAME_RUNNING:
                ionShieldButton.setDisabled(false);
                missileFire.setDisabled(false);
                bulletFire.setDisabled(false);
                laserFire.setDisabled(false);
                break;
            case GAME_PAUSED:
                ionShieldButton.setDisabled(true);
                missileFire.setDisabled(true);
                bulletFire.setDisabled(true);
                laserFire.setDisabled(true);
                break;
        }
    }


    final private CheckBox ionShieldButton;

    final private Button missileFire;
    final private Button bulletFire;
    final private Button laserFire;

}
