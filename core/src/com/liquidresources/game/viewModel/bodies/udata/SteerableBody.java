package com.liquidresources.game.viewModel.bodies.udata;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.ai.steer.behaviors.BlendedSteering;
import com.badlogic.gdx.ai.steer.behaviors.CollisionAvoidance;
import com.badlogic.gdx.ai.steer.behaviors.Hide;
import com.badlogic.gdx.ai.steer.proximities.RadiusProximity;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.utils.Array;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.common.utils.LocationC;
import com.liquidresources.game.model.common.utils.SteeringUtils;
import com.liquidresources.game.model.types.RelationTypes;

import static com.badlogic.gdx.ai.steer.limiters.NullLimiter.NEUTRAL_LIMITER;

abstract public class SteerableBody implements UniversalBody, Steerable<Vector2> {
    public SteerableBody(RelationTypes relationType, int health) {
        this.relationType = relationType;
        this.health = health;
        this.boundingRadius = 300;
    }

    protected abstract void blendSteeringInit(Array<SteerableBody> agents);

    /**
     * make damage to this body
     * @param bodyFactoryWrapper global container of all bodies
     * @param dmg deal damage
     * @return <code>true</code> if body will be destroyed, <code>false</code> res
     */
    protected boolean makeDamage(final BodyFactoryWrapper bodyFactoryWrapper, int dmg) {
        health -= dmg;
        if (health <= 0) {
            bodyFactoryWrapper.destroyBody(getBodyType(), thisBody);
        }
        return health <= 0;
    }

    @Override
    public RelationTypes getRelation() {
        return relationType;
    }

    @Override
    public Vector2 getPosition() {
        return thisBody.getPosition();
    }

    @Override
    public void setBody(final Body body) {
        thisBody = body;
    }

    @Override
    public float getOrientation() {
        return thisBody.getAngle();
    }

    @Override
    public void setOrientation(float orientation) {
        thisBody.setTransform(getPosition(), orientation);
    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return new LocationC();
    }

    @Override
    public Vector2 getLinearVelocity() {
        return thisBody.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return thisBody.getAngularVelocity();
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
    public float getZeroLinearSpeedThreshold() {
        return 0.001f;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {
        throw new UnsupportedOperationException("this operation not implemented");
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


    private int health;

    final private RelationTypes relationType;
    protected Body thisBody;

    private float boundingRadius;
    private boolean tagged;

    private float maxLinearSpeed;
    private float maxLinearAcceleration;
    private float maxAngularSpeed;
    private float maxAngularAcceleration;

    protected SteeringBehavior<Vector2> steeringBehavior;
    static final protected SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<>(new Vector2());
}
