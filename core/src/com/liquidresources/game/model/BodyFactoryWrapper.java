package com.liquidresources.game.model;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.liquidresources.game.view.drawable.DrawableBody;

import java.util.HashSet;

public class BodyFactoryWrapper {
    public BodyFactoryWrapper(final World physicsWorld) {
        this.physicsWorld = physicsWorld;
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
        //TODO dispose bodies shapes
        if (!debug) {
            System.err.println("no such body in a set! " + isBodyStatic);
        }
        physicsWorld.destroyBody(bodyForDestroy);
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
