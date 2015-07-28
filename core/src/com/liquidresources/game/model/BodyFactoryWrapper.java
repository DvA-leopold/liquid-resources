package com.liquidresources.game.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.liquidresources.game.view.DrawableBody;

import java.util.HashSet;

public class BodyFactoryWrapper {
    public BodyFactoryWrapper() {
        physicsWorld = new World(new Vector2(0, 0), true);
        dynamicObjects = new HashSet<>();
        staticConstructions = new HashSet<>();
    }

    public Body createBody(DrawableBody drawableBody, boolean isBodyStatic) {
        Body bodyForCreate = physicsWorld.createBody(drawableBody.getBodyDef());
        bodyForCreate.createFixture(drawableBody.getFixtureDef());
        bodyForCreate.setUserData(drawableBody);
        boolean debug; //TODO remove in release

        if (isBodyStatic) {
            debug = staticConstructions.add(bodyForCreate);
        } else {
            debug = dynamicObjects.add(bodyForCreate);
        }

        if (!debug) {
            System.err.println("Error, container already have such element " + isBodyStatic);
        }
        return bodyForCreate;
    }

    public void destroyBody(Body bodyForDestroy, boolean isBodyStatic) {
        boolean debug;
        if (isBodyStatic) {
            debug = staticConstructions.remove(bodyForDestroy);
        } else {
            debug = dynamicObjects.remove(bodyForDestroy);
        }

        if (!debug) {
            System.err.println("no such body in a set! " + isBodyStatic);
        }
        physicsWorld.destroyBody(bodyForDestroy);
    }

    public void updateWorld() {
        physicsWorld.step(1 / 60f, 6, 2);
    }

    public void dispose() {
        Array<Body> worldBodies = new Array<>(physicsWorld.getBodyCount());
        physicsWorld.getBodies(worldBodies);
        for (Body body : worldBodies) {
            physicsWorld.destroyBody(body);
        }

        physicsWorld.dispose();
    }

    public HashSet<Body> getDynamicObjects() {
        return dynamicObjects;
    }

    public HashSet<Body> getStaticConstructions() {
        return staticConstructions;
    }

    public World getPhysicsWorld() {
        return physicsWorld;
    }


    final private World physicsWorld;

    final private HashSet<Body> dynamicObjects;
    final private HashSet<Body> staticConstructions;
}
