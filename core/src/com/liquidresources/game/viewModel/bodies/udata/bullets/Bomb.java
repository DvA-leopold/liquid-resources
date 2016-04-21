package com.liquidresources.game.viewModel.bodies.udata.bullets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.SteerableBodyImpl;

public class Bomb extends SteerableBodyImpl {
    public Bomb(final Vector2 spawnPosition,
                final Vector2 targetPosition,
                final Vector2 bulletSize,
                final RelationTypes parentRelation) {
        super(parentRelation, 1);
    }

    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {

    }

    @Override
    public Vector2 getSize() {
        return null;
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
        return null;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return null;
    }


    final private Sprite bombSprite = new Sprite();

    private BodyDef bodyDef;
    static public FixtureDef fixtureDef;
    static public PolygonShape polygonShape;
}
