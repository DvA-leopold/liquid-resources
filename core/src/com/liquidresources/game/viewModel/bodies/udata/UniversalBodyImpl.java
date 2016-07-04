package com.liquidresources.game.viewModel.bodies.udata;

import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.SteeringBehavior;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.types.RelationTypes;

public abstract class UniversalBodyImpl implements UniversalBody {
    public UniversalBodyImpl(RelationTypes relationType, int health) {
        this.relationType = relationType;
        this.health = health;
    }

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



    private int health;

    final private RelationTypes relationType;
    private Body thisBody;

    protected SteeringBehavior<Vector2> steeringBehavior;
    static final protected SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<>(new Vector2());
}
