package com.liquidresources.game.viewModel.bodies.udata.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.UpdatableBody;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.model.resource.manager.ResourceManager;

public class Missile extends Bullet {
    public Missile(final Vector2 defaultPosition,
                   final Vector2 rocketSize,
                   final RelationTypes parentRelation) {
        super(defaultPosition, rocketSize, BodyDef.BodyType.DynamicBody, parentRelation);

        rocketSprite = new Sprite((Texture) ResourceManager.getInstance().get("drawable/bullets/rocket.png"));
        rocketSprite.setPosition(defaultPosition.x - rocketSize.x * 0.5f, defaultPosition.y - rocketSize.y * 0.5f);
        rocketSprite.setSize(rocketSize.x, rocketSize.y);

        forceX = 75;
        forceY = MathUtils.random(100, 120);
    }

    @Override
    protected void initBodyDefAndFixture(final Vector2 defaultPosition,
                                         final Vector2 bulletSize,
                                         final BodyDef.BodyType bodyType) {
        bodyDef = new BodyDef();
        bodyDef.position.set(defaultPosition.x, defaultPosition.y);
        bodyDef.type = bodyType;
        bodyDef.bullet = true;

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(bulletSize.x * 0.5f, bulletSize.y * 0.5f);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.isSensor = true;
    }

    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {
        rocketSprite.setPosition(
                position.x - rocketSprite.getWidth() * 0.5f,
                position.y - rocketSprite.getHeight() * 0.5f
        );
        rocketSprite.draw(batch);
    }

    @Override
    public Vector2 getSize() {
        return new Vector2(rocketSprite.getWidth(), rocketSprite.getHeight());
    }

    @Override
    public void update(final Body body, float delta) {
        forceY -= delta * 25;
        if(forceY < -30) {
            forceY -= 2;
        }
        body.applyForceToCenter(forceX, forceY, true);
    }

    @Override
    public void beginCollisionContact(final Body bodyA) {
        if (((UpdatableBody) bodyA.getUserData()).getBodyType() == BodyTypes.GROUND) {
            isActive = false;
            BodyFactoryWrapper.destroyBody();
        }

        if (((UpdatableBody) bodyA.getUserData()).getBodyType() == BodyTypes.ION_SHIELD &&
                ((UpdatableBody) bodyA.getUserData()).getRelation() != this.getRelation() &&
                ((UpdatableBody) bodyA.getUserData()).isActive()) {

            isActive = false;
            BodyFactoryWrapper.destroyBody();
        }
    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.ROCKET;
    }


    private float forceX, forceY;

    final private Sprite rocketSprite;
}
