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
import com.liquidresources.game.model.BodyType;
import com.liquidresources.game.model.UpdatableBody;
import com.liquidresources.game.model.resource.manager.ResourceManager;

public class Rocket extends Bullet {
    public Rocket(final Vector2 defaultPosition, final Vector2 rocketSize) {
        super(defaultPosition, rocketSize, BodyDef.BodyType.DynamicBody);

        rocketSprite = new Sprite((Texture) ResourceManager.getInstance().get("drawable/bullets/rocket.png"));
        rocketSprite.setPosition(defaultPosition.x - rocketSize.x * 0.5f, defaultPosition.y - rocketSize.y * 0.5f);
        rocketSprite.setSize(rocketSize.x, rocketSize.y);

        forceY = MathUtils.random(80, 90);
    }

    @Override
    protected void initBodyDefAndFixture(Vector2 defaultPosition, Vector2 bulletSize, BodyDef.BodyType bodyType) {
        bodyDef = new BodyDef();
        bodyDef.position.set(defaultPosition.x, defaultPosition.y);
        bodyDef.type = bodyType;

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(bulletSize.x * 0.5f, bulletSize.y * 0.5f);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
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
        body.applyForceToCenter(40, forceY, true);
    }

    @Override
    public void beginCollisionContact(final Body bodyA) {
        if (((UpdatableBody) bodyA.getUserData()).getBodyType() == BodyType.GROUND) {
            isActive = true;
            BodyFactoryWrapper.destroyBody();
        }

        if (((UpdatableBody) bodyA.getUserData()).getBodyType() == BodyType.ION_SHIELD) {
            isActive = true;
            BodyFactoryWrapper.destroyBody();
        }
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.ROCKET;
    }


    private float forceY;
    final private Sprite rocketSprite;
}
