package com.liquidresources.game.viewModel.bodies.udata.bullets;

import com.badlogic.gdx.Gdx;
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
import com.liquidresources.game.model.UpdatableBody;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;

public class Missile extends Bullet {
    public Missile(final Vector2 defaultPosition,
                   final Vector2 missileSize,
                   final RelationTypes parentRelation) {
        super(defaultPosition, missileSize, BodyDef.BodyType.DynamicBody, parentRelation);

        rocketSprite = new Sprite((Texture) ResourceManager.getInstance().get("drawable/bullets/rocket.png"));
        rocketSprite.setPosition(
                defaultPosition.x - missileSize.x * 0.5f,
                defaultPosition.y - missileSize.y * 0.5f
        );
        rocketSprite.setSize(missileSize.x, missileSize.y);

        // TODO расчет силы для любых размеров экрана
        rocketForceX = MathUtils.random(Gdx.graphics.getWidth() / 50, Gdx.graphics.getWidth() / 40);
        rocketForceY = MathUtils.random(Gdx.graphics.getHeight() / 20, Gdx.graphics.getHeight() / 15);
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
        if (rocketFuel > 0) {
            body.applyForceToCenter(rocketForceX, rocketForceY, true);
            rocketFuel--;
        }
    }

    @Override
    public void beginCollisionContact(final Body bodyA) {
        if (((UpdatableBody) bodyA.getUserData()).getBodyType() == BodyTypes.GROUND) {
            isActive.set(false);
            BodyFactoryWrapper.destroyBody();
        }

        if (((UpdatableBody) bodyA.getUserData()).getBodyType() == BodyTypes.ION_SHIELD &&
                ((UpdatableBody) bodyA.getUserData()).getRelation() != this.getRelation() &&
                ((UpdatableBody) bodyA.getUserData()).isActive()) {
            isActive.set(false);
            BodyFactoryWrapper.destroyBody();
        }
    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.ROCKET;
    }


    final private int rocketForceX, rocketForceY;
    private int rocketFuel = 40; // TODO set fuel

    final private Sprite rocketSprite;
}
