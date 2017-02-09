package com.liquidresources.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.liquidresources.game.model.bodies.UpdatableBody;
import com.liquidresources.game.model.bodies.buildings.Capital;
import com.liquidresources.game.model.bodies.bullets.Meteor;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.model.bodies.bariers.IonShield;
import com.liquidresources.game.utils.GSObserver;
import com.liquidresources.game.utils.GameStateHolder;
import com.liquidresources.game.utils.SymbolsRenderer;


final public class GameWorldModel implements GSObserver {
    public GameWorldModel(final SpriteBatch batch) {
        this.batch = batch;
        entityInitializerSystem = new EntityInitializerSystem();
//        sceneLoader.world.setContactListener(new ContactListener() {
//            @Override
//            public void beginContact(Contact contact) {
//                try {
//                    ((UpdatableBody) contact.getFixtureA().getBody().getUserData())
//                            .collisionContact(contact.getFixtureB().getBody());
//
//                    ((UpdatableBody) contact.getFixtureB().getBody().getUserData())
//                            .collisionContact(contact.getFixtureA().getBody());
//                } catch (Exception err) {
//                    System.err.println("collision contact: " + err.getMessage());
//                }
//            }
//
//            @Override
//            public void endContact(Contact contact) { }
//
//            @Override
//            public void preSolve(Contact contact, Manifold oldManifold) { }
//
//            @Override
//            public void postSolve(Contact contact, ContactImpulse impulse) { }
//        });
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
                counter++;
                if (counter > 200) {
                    entityInitializerSystem.createEntityFromLibrary("meteor", new Meteor(RelationTypes.ENEMY), MathUtils.random(3, 10), 30);
                    entityInitializerSystem.createEntityFromLibrary("meteor", new Meteor(RelationTypes.ENEMY), MathUtils.random(10, 22), 30);
                    counter = 0;
                }
                batch.begin();
                Capital capital = (Capital) entityInitializerSystem.getBaseSceneElement("capital");
                symbolsRenderer.renderNumber(batch, capital.getOilBarrels());
                symbolsRenderer.renderNumber(batch, capital.getWaterBarrels(), 0, 1);
                batch.end();
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
                    ((IonShield) entityInitializerSystem.getBaseSceneElement("ion_shield")).switchShield();
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
                ((Capital) entityInitializerSystem.getBaseSceneElement("capital")).fireMissile();
            }
        };
    }

    public ClickListener getBulletFireListener() {
        return new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Capital) entityInitializerSystem.getBaseSceneElement("capital")).fireBullet();
            }
        };
    }


    private int counter = 0;

    final private EntityInitializerSystem entityInitializerSystem;
    final private SymbolsRenderer symbolsRenderer;
    final private SpriteBatch batch;

}
