package com.liquidresources.game.viewModel.bodies.udata.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.SteerableBody;

import static com.liquidresources.game.model.common.utils.UConverter.m2p;

public class Bomb extends SteerableBody {
    static {
        bombSize = m2p(Gdx.graphics.getWidth() * 0.001f, Gdx.graphics.getHeight() * 0.001f);
    }

    public Bomb(final Vector2 spawnPosition,
                final Vector2 targetPosition,
                final Vector2 bulletSize,
                final RelationTypes parentRelation) {
        super(parentRelation, 1);
    }

    @Override
    public void blendSteeringInit(Array<SteerableBody> agents) {

    }

    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {

    }

    @Override
    public Vector2 getSize() {
        return bombSize;
    }

    @Override
    public void update(final Body body, float delta) { }

    @Override
    public void beginCollisionContact(final Body bodyA, BodyFactoryWrapper bodyFactoryWrapper) {

    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.BOMB;
    }

    @Override
    public BodyDef getBodyDef() {
        return bodyDef;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    static public void dispose() {
        if (bombShape != null) {
            bombShape.dispose();
            bombShape = null;
        }
        fixtureDef = null;
        bodyDef = null;
    }


    final private Sprite bombSprite = new Sprite();

    static private BodyDef bodyDef;
    static private FixtureDef fixtureDef;
    static private PolygonShape bombShape;

    static final private Vector2 bombSize;
}
