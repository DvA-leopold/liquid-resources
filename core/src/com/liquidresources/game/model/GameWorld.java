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
        oilPump = new OilPump[3];
        for (int i =0; i< oilPump.length; ++i) {
            oilPump[i] = new OilPump();
        }
        waterPump = new WaterPump();
    }

    public void update() {
        world.step(1 / 60f, 6, 2);
    }

    public void pause() {
        if (worldState != GameStates.GAME_PAUSED) {
            changeWorldState(GameStates.GAME_PAUSED);
        }
    }

    public void resume() { }

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
    final private OilPump[] oilPump;
    final private WaterPump waterPump;
    final private BombersFactory bombersFactory;
    final private FighterFactory fighterFactory;
}
