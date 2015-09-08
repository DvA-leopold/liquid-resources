package com.liquidresources.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.liquidresources.game.viewModel.bodies.udata.UniversalBody;

import java.util.HashSet;
import java.util.Iterator;

public class BodyFactoryWrapper {
    public BodyFactoryWrapper(final Vector2 worldGravity) {
        bodyForDestroy = 0;
        physicsWorld = new World(worldGravity, true);
        dynamicObjects = new HashSet<>();
        staticConstructions = new HashSet<>();

        physicsWorld.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
//                WorldManifold manifold = contact.getWorldManifold();
//                if (contact.getFixtureA().getBody().getUserData() != null) {
//                    System.out.println(((UpdatableBody) contact.getFixtureA().getBody().getUserData()).getBodyType());
//                } else {
//                    System.out.println("null data A");
//                }
//
//                if (contact.getFixtureB().getBody().getUserData() != null) {
//                    System.out.println(((UpdatableBody) contact.getFixtureB().getBody().getUserData()).getBodyType());
//                } else {
//                    System.out.println("null data B");
//                }
                ((UpdatableBody) contact.getFixtureA().getBody().getUserData()).
                        beginCollisionContact(contact.getFixtureB().getBody());

                ((UpdatableBody) contact.getFixtureB().getBody().getUserData()).
                        beginCollisionContact(contact.getFixtureA().getBody());
            }

            @Override
            public void endContact(Contact contact) {
                //System.out.println("end");
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {
                //System.out.println("pre");
            }

            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
                //System.out.println("post");
            }
        });
    }

    public void createBody(final UniversalBody universalBody, boolean isBodyStatic) {
        Body bodyForCreate = physicsWorld.createBody(universalBody.getBodyDef());
        bodyForCreate.createFixture(universalBody.getFixtureDef());
        bodyForCreate.setUserData(universalBody);
        boolean debug; //TODO remove in release

        if (isBodyStatic) {
            debug = staticConstructions.add(bodyForCreate);
        } else {
            debug = dynamicObjects.add(bodyForCreate);
        }

        if (!debug) {
            System.err.println("Error, container already have such element " + isBodyStatic);
        }
    }

    public void destroyBody(Body bodyForDestroy, boolean isBodyStatic) {
        //bodyForDestroy.setActive(false);
        physicsWorld.destroyBody(bodyForDestroy);

        boolean debug;
        if (isBodyStatic) {
            debug = staticConstructions.remove(bodyForDestroy);
        } else {
            debug = dynamicObjects.remove(bodyForDestroy);
        }

        if (!debug) {
            System.err.println("no such body in a set! " + isBodyStatic);
        }
    }

    public void updateWorld() {
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

    public HashSet<Body> getDynamicBodies() {
        return dynamicObjects;
    }

    public HashSet<Body> getConstructionsBodies() {
        return staticConstructions;
    }

    public World getPhysicsWorld() {
        return physicsWorld;
    }

    public static void destroyBody() {
        bodyForDestroy++;
    }

    private void collectGarbage() {
        if (bodyForDestroy > 0) {
            Iterator<Body> bodyIterator = dynamicObjects.iterator(); // TODO добавить уничтожение тел другого типа
            while (bodyIterator.hasNext() && bodyForDestroy > 0) {
                Body tempBody = bodyIterator.next();
                if (!((UpdatableBody) tempBody.getUserData()).isActive()) {
                    physicsWorld.destroyBody(tempBody);
                    bodyIterator.remove();
                    bodyForDestroy--;
                }
            }
        }
    }


    private static short bodyForDestroy;

    final private World physicsWorld;
    final private HashSet<Body> dynamicObjects;
    final private HashSet<Body> staticConstructions;
}
