package com.liquidresources.game.viewModel.bodies.udata.ships;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.UniversalBody;

public abstract class Ship implements UniversalBody, Steerable<Vector2> {
    public Ship(final Vector2 defaultPosition,
                final Vector2 shipSize,
                int health,
                boolean tagged,
                float boundingRadius,
                final RelationTypes relationType) {
        this.relationType = relationType;
        this.health = health;
        isActive = true;
        this.tagged = tagged;
        this.boundingRadius = boundingRadius;

        initBodyDefAndFixture(defaultPosition, shipSize);
    }

    @Override
    public BodyDef getBodyDef() {
        return bodyDef;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public RelationTypes getRelation() {
        return relationType;
    }

    /**
     * this method must initialize <code>bodyDef</code>, <code>polygonShape</code> and
     * <code>fixtureDef</code>
     */
    protected abstract void initBodyDefAndFixture(
            final Vector2 defaultPosition,
            final Vector2 shipSize
    );

//---------------------Steerable--------------------------

    @Override
    public Vector2 getPosition() {
        return body.getPosition();
    }

    @Override
    public float getOrientation() {
        return body.getAngle();
    }

    @Override
    public Vector2 getLinearVelocity() {
        return body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return body.getAngularVelocity();
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public Vector2 newVector() {
        return new Vector2();
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return 0;
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = (float) Math.cos(angle);
        outVector.y = (float) Math.sin(angle);
        return outVector;
    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }


    private Body body;
    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;

    protected int health;

    private boolean isActive, tagged;
    protected float boundingRadius;
    protected float maxLinearSpeed, maxLinearAcceleration;
    protected float maxAngularSpeed, maxAngularAcceleration;

    final private RelationTypes relationType;

}
