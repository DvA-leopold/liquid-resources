package com.liquidresources.game.model.bodies;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.EntityInitializer;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.utils.SteeringUtils;
import com.uwsoft.editor.renderer.components.physics.PhysicsBodyComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;


public abstract class UpdatableBody implements Location<Vector2>, IScript {
    public UpdatableBody(RelationTypes relationType, int health) {
        this.relationType = relationType;
        this.health = health;
        this.isInitialized = false;
    }

    protected void takeDamage(int dmg) {
        health -= dmg;
        if (health < 1) {
            dispose();
        }
    }

    public RelationTypes getRelation() {
        return relationType;
    }

    public void init(Entity entity) {
        if (this.entity == null) {
            this.entity = entity;
            entityInitializer.sheduleForInitPhysicComponent(this);
        } else {
            physicsBodyComponent = ComponentRetriever.get(this.entity, PhysicsBodyComponent.class);
            if (physicsBodyComponent != null && physicsBodyComponent.body != null) {
                physicsBodyComponent.body.setUserData(this);
                if (getBodyType() == BodyTypes.ION_SHIELD) {
                    physicsBodyComponent.body.setActive(false);
                }
            }
            isInitialized = true;
        }
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

    public Entity getEntity() {
        return entity;
    }

    public boolean isInitialized() {
        return isInitialized;
    }

    public abstract BodyTypes getBodyType();

    public abstract void collisionContact(Body collidedEnemyBody);
    public abstract void dispose();


    protected boolean isInitialized;
    protected PhysicsBodyComponent physicsBodyComponent;
    protected static EntityInitializer entityInitializer;

    protected int health;
    private Entity entity;
    private UpdatableBody hunterUpdatableBody;
    final private RelationTypes relationType;
}
