package com.liquidresources.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.liquidresources.game.model.game.world.base.MainAI;
import com.liquidresources.game.model.game.world.factories.BombersFactory;
import com.liquidresources.game.model.game.world.factories.FighterFactory;
import com.liquidresources.game.model.game.world.pumps.OilPump;
import com.liquidresources.game.model.game.world.pumps.Pump;
import com.liquidresources.game.model.game.world.pumps.WaterPump;
import com.liquidresources.game.viewModel.GameStates;

public class GameWorld {
    public GameWorld() {
        Box2D.init();

        world = new World(new Vector2(0, 0), true);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        mainAI = new MainAI();
        bombersFactory = new BombersFactory(new Vector2(50, 70), 100);
        fighterFactory = new FighterFactory(new Vector2(50, 70), 20);

        oilPump1 = new OilPump(0.04f);
        oilPump2 = new OilPump(0.04f);
        waterPump = new WaterPump(0.09f);
    }

    public void update(float delta) {
        world.step(1 / 60f, 6, 2);

        switch (worldState) {
            case GAME_PREPARING:
                break;
            case GAME_RUNNING:
                mainAI.update(
                        oilPump1.getResources(delta) + oilPump2.getResources(delta),
                        waterPump.getResources(delta)
                );
                break;
            case GAME_PAUSED:
                break;
            case GAME_EXIT:
                break;
            case GAME_OVER:
                break;
        }

    }

    public void pause() {
        if (worldState != GameStates.GAME_PREPARING) {
            changeWorldState(GameStates.GAME_PREPARING);
        }
    }

    public MainAI getMainAI() {
        return mainAI;
    }

    public static GameStates getWorldState() {
        return worldState;
    }

    public static void changeWorldState(GameStates newWorldState) {
        worldState = newWorldState;
    }

    public void dispose() {
        Array<Body> worldBodies = new Array<>(world.getBodyCount());
        world.getBodies(worldBodies);
        for (Body body : worldBodies) {
            world.destroyBody(body);
        }
        world.dispose();
    }


    private static GameStates worldState = GameStates.GAME_PREPARING;

    final private World world;
    final private OrthographicCamera camera;

    final private MainAI mainAI;
    final private Pump oilPump1, oilPump2;
    final private Pump waterPump;
    final private BombersFactory bombersFactory;
    final private FighterFactory fighterFactory;
}
