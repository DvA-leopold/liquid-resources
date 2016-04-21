package com.liquidresources.game.viewModel.bodies.udata.bullets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.UniversalBody;

import java.util.concurrent.atomic.AtomicBoolean;

abstract class Bullet implements UniversalBody {
    Bullet(final Vector2 targetPosition,
           final RelationTypes parentRelation) {
        this.parentRelation = parentRelation;
        this.targetPosition = targetPosition;
        isActive = new AtomicBoolean(true);
    }

    @Override
    public boolean isActive() {
        return isActive.get();
    }

    @Override
    public RelationTypes getRelation() {
        return parentRelation;
    }

    @Override
    public Vector2 getPosition() {
        return thisBody.getPosition();
    }

    @Override
    public void setBody(final Body body) {
        thisBody = body;
    }

    void selfDestroy(final BodyFactoryWrapper bodyFactoryWrapper) {
        if (isActive.getAndSet(false)) {
            bodyFactoryWrapper.destroyBody(getBodyType(), thisBody);
        }
    }


    private Body thisBody;
    private AtomicBoolean isActive;

    final Vector2 targetPosition;

    final private RelationTypes parentRelation;
}
