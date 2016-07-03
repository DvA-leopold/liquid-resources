package com.liquidresources.game.viewModel.bodies.udata.bullets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.SteerableBody;

public class Laser extends SteerableBody {
    static {
        laserSize = new Vector2(Gdx.graphics.getWidth() * 0.1f, Gdx.graphics.getHeight() * 0.1f);
        speed = new Vector2(100, 100);
    }

    public Laser(final Vector2 spawnPosition,
                 final Vector2 targetPosition,
                 final RelationTypes parentRelation) {
        super(parentRelation, 1);

        this.laserSprite = new Sprite((Texture) ResourceManager.getInstance().get(""));  // TODO laser sprite
        this.laserSprite.setPosition(spawnPosition.x - laserSize.x * 0.5f, spawnPosition.y - laserSize.y * 0.5f);
        this.laserSprite.setSize(laserSize.x, laserSize.y);

        if (bodyDef == null) {
            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.DynamicBody;
        }
        bodyDef.position.set(spawnPosition.x + laserSize.x, spawnPosition.y + laserSize.y);

        if (laserShape == null) {
            laserShape = new PolygonShape();
            laserShape.setAsBox(laserSize.x * 0.5f, laserSize.y * 0.5f);
        }
        if (fixtureDef == null) {
            fixtureDef = new FixtureDef();
            fixtureDef.shape = laserShape;
            fixtureDef.isSensor = true;
        }
    }

    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {
        laserSprite.setPosition(
                position.x - laserSprite.getWidth() * 0.5f,
                position.y - laserSprite.getHeight() * 0.5f
        );
        laserSprite.draw(batch);
    }

    @Override
    public Vector2 getSize() {
        return laserSize;
    }

    @Override
    public void update(final Body body, float delta) { // TODO remove body from args
    }

    @Override
    public void beginCollisionContact(final Body bodyA, BodyFactoryWrapper bodyFactoryWrapper) {

    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.LASER;
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
        if (laserShape != null) {
            laserShape.dispose();
            laserShape = null;
        }
        fixtureDef = null;
        bodyDef = null;
    }


    final private Sprite laserSprite;

    static private BodyDef bodyDef;
    static private FixtureDef fixtureDef;
    static private PolygonShape laserShape;

    static final private Vector2 laserSize, speed;
}
