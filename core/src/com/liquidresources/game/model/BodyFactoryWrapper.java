package com.liquidresources.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.viewModel.bodies.udata.SteerableBody;
import com.liquidresources.game.viewModel.bodies.udata.UniversalBody;

import java.util.ArrayList;
import java.util.HashSet;

public class BodyFactoryWrapper {
    public BodyFactoryWrapper(final Vector2 worldGravity) {
        bodiesForDestruction = new ArrayList<>(5);
        physicsWorld = new World(worldGravity, true);

        staticBodies = new Array<>();
        staticSteerableBodies = new Array<>();
        dynamicBodies = new HashSet<>();

        physicsWorld.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                ((UpdatableBody) contact.getFixtureA().getBody().getUserData())
                        .beginCollisionContact(contact.getFixtureB().getBody(), BodyFactoryWrapper.this);

                ((UpdatableBody) contact.getFixtureB().getBody().getUserData())
                        .beginCollisionContact(contact.getFixtureA().getBody(), BodyFactoryWrapper.this);
            }

            @Override
            public void endContact(Contact contact) { }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) { }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) { }
        });
    }

    public void createBody(final UniversalBody universalBodyUserData) {
        Body bodyForCreate = physicsWorld.createBody(universalBodyUserData.getBodyDef());
        bodyForCreate.createFixture(universalBodyUserData.getFixtureDef());
        bodyForCreate.setUserData(universalBodyUserData);
        universalBodyUserData.setBody(bodyForCreate);
        boolean success = false;

        switch(universalBodyUserData.getBodyType()) {
            case BOMB:
            case LASER:
            case MISSILE:
            case FIGHTER_SHIP:
                success = dynamicBodies.add(bodyForCreate);
                if(universalBodyUserData instanceof SteerableBody) {
                    ((SteerableBody) universalBodyUserData).blendSteeringInit(staticSteerableBodies);
                }
                break;
            case CAPITAL:
            case OIL_POMP:
            case SHIP_FACTORY:
            case ION_SHIELD:
            case GROUND:
                staticBodies.add(bodyForCreate);
                if (universalBodyUserData instanceof SteerableBody) {
                    staticSteerableBodies.add((SteerableBody) universalBodyUserData);
                }
                break;
            default:
                System.err.print("no such type");
        }

        if (!success) {
            System.err.println("Error, container already have such element ");
        }
    }

    void updateWorld() {
        physicsWorld.step(1 / 60f, 6, 2);
        collectGarbage();
    }

    public void dispose() {
        Array<Body> worldBodies = new Array<>(physicsWorld.getBodyCount());
        physicsWorld.getBodies(worldBodies);
        for (Body body : worldBodies) {
            physicsWorld.destroyBody(body);
        }

        physicsWorld.dispose();

        dynamicBodies.clear();
        staticBodies.clear();
    }

    public Array<Body> getStaticBodies() {
        return staticBodies;
    }

    public Array<SteerableBody> getSteerableBodies() {
        return staticSteerableBodies;
    }

    public HashSet<Body> getDynamicBodies() {
        return dynamicBodies;
    }

    public World getPhysicsWorld() {
        return physicsWorld;
    }

    public void destroyBody(BodyTypes destroyBodyType, final Body bodyForDestroy) {
        switch (destroyBodyType) {
            case BOMB:
            case LASER:
            case MISSILE:
            case FIGHTER_SHIP:
                dynamicBodies.remove(bodyForDestroy);
                break;
        }
        bodiesForDestruction.add(bodyForDestroy);
    }

    private void collectGarbage() {
        if (!bodiesForDestruction.isEmpty()) {
            for (Body body: bodiesForDestruction) {
                physicsWorld.destroyBody(body);
            }
            bodiesForDestruction.clear();
        }
    }


    private World physicsWorld;

    private ArrayList<Body> bodiesForDestruction;

    final private HashSet<Body> dynamicBodies;
    final private Array<Body> staticBodies;
    private Array<SteerableBody> staticSteerableBodies;
}
