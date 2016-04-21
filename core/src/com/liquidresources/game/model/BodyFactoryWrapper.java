package com.liquidresources.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.viewModel.bodies.udata.UniversalBody;

import java.util.ArrayList;
import java.util.HashSet;

public class BodyFactoryWrapper {
    public BodyFactoryWrapper(final Vector2 worldGravity) {
        bodiesForDestruction = new ArrayList<>(5);
        physicsWorld = new World(worldGravity, true);

        shipsBodies = new HashSet<>();
        buildingsBodies = new HashSet<>();
        bulletBodies = new HashSet<>();

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
                success = bulletBodies.add(bodyForCreate);
                break;
            case CAPITAL:
            case OIL_POMP:
            case SHIP_FACTORY:
            case ION_SHIELD:
            case GROUND:
                success = buildingsBodies.add(bodyForCreate);
                break;
            case FIGHTER_SHIP:
                success = shipsBodies.add(bodyForCreate);
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
    }

    public HashSet<Body> getShipsBodies() {
        return shipsBodies;
    }

    public HashSet<Body> getBuildingsBodies() {
        return buildingsBodies;
    }

    public HashSet<Body> getBulletBodies() {
        return bulletBodies;
    }

    public World getPhysicsWorld() {
        return physicsWorld;
    }

    public void destroyBody(BodyTypes destroyBodyType, final Body bodyForDestroy) {
        switch (destroyBodyType) { // TODO thread safe access to HashSets
            case BOMB:
            case LASER:
            case MISSILE:
                bulletBodies.remove(bodyForDestroy);
                break;
            case FIGHTER_SHIP:
                shipsBodies.remove(bodyForDestroy);
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


    final private World physicsWorld;

    private ArrayList<Body> bodiesForDestruction;

    final private HashSet<Body> shipsBodies;
    final private HashSet<Body> bulletBodies;
    final private HashSet<Body> buildingsBodies;
}
