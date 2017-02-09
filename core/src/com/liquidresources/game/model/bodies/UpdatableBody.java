package com.liquidresources.game.model.bodies;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.EntityInitializerSystem;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.utils.SteeringUtils;


public abstract class UpdatableBody implements Location<Vector2> {
    public UpdatableBody(RelationTypes relationType, int health) {
        this.relationType = relationType;
        this.health = health;
        this.isInitialized = false;
    }

    public static void setEntityInitializerSystem(final EntityInitializerSystem entityInitializerSystem) {
        if (UpdatableBody.entityInitializerSystem == null) {
            UpdatableBody.entityInitializerSystem = entityInitializerSystem;
        }
    }

    protected void takeDamage(int dmg) {
        health -= dmg;
        if (health < 1) {
            // dispose
        }
    }

    public RelationTypes getRelation() {
        return relationType;
    }
//            physicsBodyComponent = ComponentRetriever.get(this.entity, PhysicsBodyComponent.class);
//            if (physicsBodyComponent != null && physicsBodyComponent.body != null) {
//                physicsBodyComponent.body.setUserData(this);
//                switch (getBodyType()) {
//                    case ION_SHIELD:
//                        physicsBodyComponent.body.setActive(false);
//                        break;
//                    default:
//                        break;
//                }
//            }
//            isInitialized = true;

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return body.getTransform().getOrientation().angle();
    }

    @Override
    public void setOrientation(float orientation) {
        body.setTransform(getPosition(), orientation);
    }

    public void setHunterUpdatableBody(UpdatableBody hunterUpdatableBody) {
        this.hunterUpdatableBody = hunterUpdatableBody;
    }

    public UpdatableBody getHunterUpdatableBody() {
        return hunterUpdatableBody;
    }

    @Override
    public Location<Vector2> newLocation() {
        throw new RuntimeException("newLocation not implemented");
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector, angle);
    }

    public void initialize(final Body body) {
        this.body = body;
        isInitialized = true;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public abstract BodyTypes getBodyType();

    public abstract void collisionContact(Body collidedEnemyBody);

    public static void finalDispose() {
        entityInitializerSystem = null;
    }


    protected Body body;
    protected boolean isInitialized;

    protected static EntityInitializerSystem entityInitializerSystem;

    protected int health;
    private UpdatableBody hunterUpdatableBody;
    final private RelationTypes relationType;
}
