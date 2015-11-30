package com.liquidresources.game.viewModel.bodies.udata.bullets;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.UniversalBody;

import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Bullet implements UniversalBody {
    public Bullet(final Vector2 defaultPosition,
                  final Vector2 bulletSize,
                  final BodyDef.BodyType bodyType,
                  final RelationTypes parentRelation) {
        this.parentRelation = parentRelation;
        isActive = new AtomicBoolean(true);
        initBodyDefAndFixture(defaultPosition, bulletSize, bodyType);
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
        return isActive.get();
    }

    @Override
    public RelationTypes getRelation() {
        return parentRelation;
    }

    protected abstract void initBodyDefAndFixture(
            final Vector2 defaultPosition,
            final Vector2 bulletSize,
            BodyDef.BodyType bodyType
    );


    protected AtomicBoolean isActive;

    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;

    final private RelationTypes parentRelation;
}
