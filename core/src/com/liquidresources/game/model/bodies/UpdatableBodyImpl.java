package com.liquidresources.game.model.bodies;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.liquidresources.game.model.EntityInitializer;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.utils.SteeringUtils;
import com.uwsoft.editor.renderer.components.physics.PhysicsBodyComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;


public abstract class UpdatableBodyImpl implements UpdatableBody, IScript, Location<Vector2> {
    public UpdatableBodyImpl(RelationTypes relationType, int health) {
        this.relationType = relationType;
        this.health = health;
        this.isInitialized = false;
    }

    public static void setEntityInitializer(final EntityInitializer entityInitializer) {
        if (UpdatableBodyImpl.entityInitializer == null) {
            UpdatableBodyImpl.entityInitializer = entityInitializer;
        }
    }

    protected void takeDamage(int dmg) {
        health -= dmg;
        if (health < 1) {
            dispose();
        }
    }

    @Override
    public RelationTypes getRelation() {
        return relationType;
    }

    @Override
    public void init(Entity entity) {
        this.entity = entity;
        //need to make one render call to init physic body
        entityInitializer.getSceneLoader().getEngine().update(Gdx.graphics.getDeltaTime()); // FIXME related to ***1, cant notify here, result is NullPointerException
                                                                                            // FIXME cos user data does not attached yet, but collision already happened
        physicsBodyComponent = ComponentRetriever.get(entity, PhysicsBodyComponent.class);
        if (physicsBodyComponent != null && physicsBodyComponent.body != null) {
            physicsBodyComponent.body.setUserData(this);
            switch (getBodyType()) {
                case ION_SHIELD:
                    physicsBodyComponent.body.setActive(false);
                    break;
                default:
                    break;
            }
        }
        isInitialized = true;
    }

    @Override
    public Vector2 getPosition() {
        return physicsBodyComponent.body.getPosition();
    }

    @Override
    public float getOrientation() {
        return physicsBodyComponent.body.getTransform().getOrientation().angle();
    }

    @Override
    public void setOrientation(float orientation) {
        physicsBodyComponent.body.setTransform(getPosition(), orientation);
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

    public static void finalDispose() {
        entityInitializer = null;
    }


    protected boolean isInitialized;
    protected Entity entity;
    protected PhysicsBodyComponent physicsBodyComponent;

    private int health;
    final private RelationTypes relationType;

    static protected EntityInitializer entityInitializer;
}
