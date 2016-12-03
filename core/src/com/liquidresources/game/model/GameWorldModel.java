package com.liquidresources.game.model;

import com.badlogic.gdx.Gdx;
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
import com.uwsoft.editor.renderer.SceneLoader;


final public class GameWorldModel implements GSObserver {
    public GameWorldModel(final SceneLoader sceneLoader) {
        this.sceneLoader = sceneLoader;
        entityInitializer = new EntityInitializer(sceneLoader);
        entityInitializer.createEntityFromLibrary("meteor", new Meteor(RelationTypes.ENEMY), 10, 20);
        entityInitializer.createEntityFromLibrary("meteor", new Meteor(RelationTypes.ENEMY), 16, 20);
        sceneLoader.world.setContactListener(new ContactListener() {
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
        symbolsRenderer = new SymbolsRenderer(0, 21f, 1, 1);
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
                    entityInitializer.createEntityFromLibrary("meteor", new Meteor(RelationTypes.ENEMY), MathUtils.random(3, 10), 23);
                    entityInitializer.createEntityFromLibrary("meteor", new Meteor(RelationTypes.ENEMY), MathUtils.random(10, 22), 23);
                    counter = 0;
                }
                sceneLoader.engine.update(delta);
                entityInitializer.initSheduledBodies();
                sceneLoader.getBatch().begin();
                Capital capital = (Capital) entityInitializer.getBaseSceneElement("capital");
                symbolsRenderer.renderNumber(sceneLoader.getBatch(), capital.getOilBarrels());
                symbolsRenderer.renderNumber(sceneLoader.getBatch(), capital.getWaterBarrels(), 0, 1);
                sceneLoader.getBatch().end();
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


    private int counter = 0;

    final private SceneLoader sceneLoader;

    final private EntityInitializer entityInitializer;
    final private SymbolsRenderer symbolsRenderer;
}
