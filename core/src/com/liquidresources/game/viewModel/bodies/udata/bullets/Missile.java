package com.liquidresources.game.viewModel.bodies.udata.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.UpdatableBody;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;

import static com.liquidresources.game.model.common.utils.UConverter.m2p;

public class Missile extends Bullet {
    static {
        missileSize = m2p(Gdx.graphics.getWidth() * 0.004f, Gdx.graphics.getHeight() * 0.02f);
    }

    public Missile(final Vector2 spawnPosition,
                   final Vector2 targetPosition,
                   final RelationTypes parentRelation) {
        super(targetPosition, parentRelation);

        rocketSprite = new Sprite((Texture) ResourceManager.getInstance().get("drawable/bullets/rocket.png"));
        rocketSprite.setPosition(
                spawnPosition.x - missileSize.x * 0.5f,
                spawnPosition.y - missileSize.y * 0.5f
        );
        rocketSprite.setSize(missileSize.x, missileSize.y);
        rocketSprite.setOriginCenter();
        rocketSprite.setRotation(-30);

        // TODO расчет силы для любых размеров экрана
        if (parentRelation == RelationTypes.ALLY) {
            rocketForceX = MathUtils.random(Gdx.graphics.getWidth() / 50, Gdx.graphics.getWidth() / 40);
            rocketSprite.setRotation(-30);
            spriteRotationDegree = -0.7f;
        } else {
            rocketForceX = -MathUtils.random(Gdx.graphics.getWidth() / 50, Gdx.graphics.getWidth() / 40);
            rocketSprite.setRotation(30);
            spriteRotationDegree = 0.7f;
        }

        rocketForceY = MathUtils.random(Gdx.graphics.getHeight() / 20, Gdx.graphics.getHeight() / 15);
        this.rotateDegree = 135;

        bodyDef = new BodyDef();
        bodyDef.position.set(spawnPosition.x, spawnPosition.y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.bullet = true;

        if (bodyShape == null) {
            bodyShape = new PolygonShape();
            bodyShape.setAsBox(missileSize.x * 0.5f, missileSize.y * 0.5f);
        }

        if (fixtureDef == null) {
            fixtureDef = new FixtureDef();
            fixtureDef.shape = bodyShape;
            fixtureDef.isSensor = true;
        }
    }

    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {
        rocketSprite.setPosition(
                position.x - rocketSprite.getWidth() * 0.5f,
                position.y - rocketSprite.getHeight() * 0.5f
        );

        if (rocketSprite.getRotation() * MathUtils.degreesToRadians > -MathUtils.degreesToRadians * rotateDegree) {
            rocketSprite.rotate(spriteRotationDegree);
        }

        rocketSprite.draw(batch);
    }

    @Override
    public Vector2 getSize() {
        return new Vector2(rocketSprite.getWidth(), rocketSprite.getHeight());
    }

    @Override
    public void update(final Body body, float delta) {
        if (rocketFuel > 0) {
            body.applyForceToCenter(rocketForceX, rocketForceY, true);
            rocketFuel--;
        }

        if (body.getTransform().getRotation() > -MathUtils.degreesToRadians * rotateDegree) {
            body.setTransform(body.getPosition(), bodyRotationRadians);
            if (getRelation() == RelationTypes.ALLY) {
                bodyRotationRadians -= delta; // TODO radians to degree
            } else {
                bodyRotationRadians += delta;
            }
        }
    }

    @Override
    public void beginCollisionContact(final Body bodyA, final BodyFactoryWrapper bodyFactoryWrapper) {
        UpdatableBody externalBodyUserData = (UpdatableBody) bodyA.getUserData();

        if (BodyTypes.GROUND == externalBodyUserData.getBodyType()) {
            super.selfDestroy(bodyFactoryWrapper);
        }

        if (externalBodyUserData.isActive() &&
                externalBodyUserData.getRelation() != this.getRelation() &&
                BodyTypes.ION_SHIELD == externalBodyUserData.getBodyType()) {
            super.selfDestroy(bodyFactoryWrapper);
        }
    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.MISSILE;
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
        if (bodyShape != null) {
            bodyShape.dispose();
        }
    }


    final private int rocketForceX, rocketForceY;
    private int rocketFuel = 40; // TODO set fuel

    private float bodyRotationRadians = 0f;
    private float spriteRotationDegree;
    final private int rotateDegree;

    final private Sprite rocketSprite;

    static private Vector2 missileSize;

    private BodyDef bodyDef;
    static private FixtureDef fixtureDef;
    static private PolygonShape bodyShape;
}
