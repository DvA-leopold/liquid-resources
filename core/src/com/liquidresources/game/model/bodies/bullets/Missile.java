package com.liquidresources.game.model.bodies.bullets;

import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.ai.steer.behaviors.Arrive;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.model.bodies.UpdatableBody;


final public class Missile extends UpdatableBody implements Steerable<Vector2> {
    public Missile(final RelationTypes parentRelation) {
        super(parentRelation, 1);
        setMaxLinearSpeed(15);
        setMaxLinearAcceleration(30);
        boundingRadius = 10;
        independentFacing = true;
        this.steeringBehavior = new Arrive<>(this)
                .setTimeToTarget(4f)
                .setArrivalTolerance(0.001f)
                .setDecelerationRadius(0.1f);
    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.MISSILE;
    }

    @Override
    public void collisionContact(Body collidedEnemyBody) {
        UpdatableBody collidedUpdatableBody = (UpdatableBody) collidedEnemyBody.getUserData();
        if (this.equals(collidedUpdatableBody.getHunterUpdatableBody())) {
            switch (collidedUpdatableBody.getBodyType()) {
                case METEOR:
                    takeDamage(1);
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public void act(float delta) {
        if (!isInitialized) {
            return;
        }

        if (steeringBehavior != null) {
            if (((Arrive<Vector2>) steeringBehavior).getTarget() == null) {
                UpdatableBody targetUpdatableBody = entityInitializer.getTargetBody(RelationTypes.ENEMY);
                if (targetUpdatableBody != null) {
                    targetUpdatableBody.setHunterUpdatableBody(this);
                    this.setHunterUpdatableBody(targetUpdatableBody);
                    ((Arrive<Vector2>) steeringBehavior).setTarget(targetUpdatableBody);
                } else {
                    return;
                }
            }

            steeringBehavior.calculateSteering(STEERING_ACCELERATION_OUTPUT);

            if (!STEERING_ACCELERATION_OUTPUT.linear.isZero()) {
//                STEERING_ACCELERATION_OUTPUT.linear.sub(entityInitializer.getSceneLoader().world.getGravity());
                physicsBodyComponent.body.applyForceToCenter(STEERING_ACCELERATION_OUTPUT.linear, true);
            }

            if (independentFacing) {
                if (STEERING_ACCELERATION_OUTPUT.angular != 0) {
                    physicsBodyComponent.body.applyTorque(STEERING_ACCELERATION_OUTPUT.angular, true);
                }
            } else {
                Vector2 linVel = getLinearVelocity();
                if (!linVel.isZero(getZeroLinearSpeedThreshold())) {
                    float newOrientation = vectorToAngle(linVel);
                    physicsBodyComponent.body.setAngularVelocity((newOrientation - getAngularVelocity()) * delta);
                    physicsBodyComponent.body.setTransform(physicsBodyComponent.body.getPosition(), newOrientation);
                }
            }
        }
    }

    @Override
    public void dispose() {
        entityInitializer.destroyEntity(this);
    }

    @Override
    public Vector2 getLinearVelocity() {
        return physicsBodyComponent.body.getLinearVelocity();
    }

    @Override
    public float getAngularVelocity() {
        return physicsBodyComponent.body.getAngularVelocity();
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
        return 0.0001f;
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


    final private boolean independentFacing;
    private float boundingRadius;
    private boolean tagged;

    private float maxLinearSpeed;
    private float maxLinearAcceleration;
    private float maxAngularSpeed;
    private float maxAngularAcceleration;

    final private SteeringBehavior<Vector2> steeringBehavior;
    static final private SteeringAcceleration<Vector2> STEERING_ACCELERATION_OUTPUT = new SteeringAcceleration<>(new Vector2());
}
