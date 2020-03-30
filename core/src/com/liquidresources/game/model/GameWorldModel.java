package com.liquidresources.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.liquidresources.game.model.bodies.UpdatableBody;
import com.liquidresources.game.model.bodies.buildings.Capital;
import com.liquidresources.game.model.bodies.bariers.IonShield;
import com.liquidresources.game.utils.Observer;
import com.liquidresources.game.utils.GameStateHolder;
import com.liquidresources.game.utils.SymbolsRenderer;
import com.uwsoft.editor.renderer.SceneLoader;


final public class GameWorldModel implements Observer {
    public GameWorldModel() {
        world = new World(new Vector2(0, 9.8f), true);
        sceneLoader = new SceneLoader(null, world, null); // FIXME
        entityInitializer = new EntityInitializer(sceneLoader);
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                try {
                    ((UpdatableBody) contact.getFixtureA().getBody().getUserData())
                            .collisionContact(contact.getFixtureB().getBody());

                    ((UpdatableBody) contact.getFixtureB().getBody().getUserData())
                            .collisionContact(contact.getFixtureA().getBody());
                } catch (Exception err) {  // FIXME problem with objects, spawned by timer, p.s - stop and start timer ***1 related too UpdatebleBodyImpl init
                    System.err.println("collision contact: " + err.getMessage());
                }
            }

            @Override
            public void endContact(Contact contact) {

            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });
        GameStateHolder.addObserver(this);
        symbolsRenderer = new SymbolsRenderer(0, 28f, 1, 1);
    }

    public void update(float delta) {
        switch (GameStateHolder.getGameState()) {
            case GAME_PREPARING:
                if (Gdx.input.justTouched()) {
                    GameStateHolder.changeGameState(GameStates.GAME_RUNNING);
                }
                break;
            case GAME_RUNNING:
                entityInitializer.initSheduledBodies();
                Capital capital = (Capital) entityInitializer.getBaseSceneElement("capital");
                symbolsRenderer.renderNumber(sceneLoader.getBatch(), capital.getOilBarrels());
                symbolsRenderer.renderNumber(sceneLoader.getBatch(), capital.getWaterBarrels(), 0, 1);
                break;
            case GAME_PAUSED:
                break;
            case GAME_EXIT:
                break;
            case GAME_OVER:
                break;
        }
    }

    @Override
    public void notify(GameStates newGameState) {
        switch (newGameState) {
            case GAME_PREPARING:
                break;
            case GAME_RUNNING:
                break;
            case GAME_PAUSED:
                break;
            case GAME_EXIT:
            case GAME_OVER:
                break;
        }
    }

    public ClickListener getIonShieldListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    ((IonShield) entityInitializer.getBaseSceneElement("ion_shield")).switchShield();
                } catch (NullPointerException err) {
                    System.err.println(err.getMessage());
                }
            }
        };
    }

    public ClickListener getFireMissileListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Capital) entityInitializer.getBaseSceneElement("capital")).fireMissile();
            }
        };
    }

    public ClickListener getBulletFireListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Capital) entityInitializer.getBaseSceneElement("capital")).fireBullet();
            }
        };
    }

    public ClickListener getLaserFireListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Capital) entityInitializer.getBaseSceneElement("capital")).fireBullet();
            }
        };
    }

    public World getWorld() {
        return world;
    }


    final private SceneLoader sceneLoader;
    final private World world;
    final private EntityInitializer entityInitializer;
    final private SymbolsRenderer symbolsRenderer;

}
