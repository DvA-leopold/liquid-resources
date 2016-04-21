package com.liquidresources.game.viewModel.bodies.udata.bariers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.UniversalBody;

import static com.liquidresources.game.model.common.utils.UConverter.m2p;

public class Ground implements UniversalBody {
    public Ground(final Vector2 groundPosition) {
        bodyDef = new BodyDef();
        bodyDef.position.set(groundPosition);
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(m2p(Gdx.graphics.getWidth() * 5), m2p(1));

        fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;

        //TODO dispose bodyShape
    }

    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {

    }

    @Override
    public Vector2 getSize() {
        return null;
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
    public void update(final Body body, float delta) { }

    @Override
    public void beginCollisionContact(final Body bodyA, final BodyFactoryWrapper bodyFactoryWrapper) { }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.GROUND;
    }

    @Override
    public Vector2 getPosition() {
        return null;
    }

    @Override
    public boolean isActive() {
        return true;
    }

    @Override
    public RelationTypes getRelation() {
        return null;
    }

    @Override
    public void setBody(final Body body) { }


    final private BodyDef bodyDef;
    final private FixtureDef fixtureDef;
}
