package com.liquidresources.game.viewModel.bodies.udata.ships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;

public class Bomber extends Ship {
    public Bomber(final Vector2 defaultPosition,
                  final Vector2 shipSize,
                  int defaultHealth,
                  RelationTypes relationType) {
        super(defaultPosition, shipSize, defaultHealth, false, 100, relationType);

        shipSprite = new Sprite((Texture) ResourceManager.getInstance().get("drawable/ships/bomber.png"));
        shipSprite.setPosition(defaultPosition.x - shipSize.x * 0.5f, defaultPosition.y - shipSize.y * 0.5f);
        shipSprite.setSize(shipSize.x, shipSize.y);
    }

    @Override
    protected void initBodyDefAndFixture(
            final Vector2 defaultPosition,
            final Vector2 shipSize) {

        bodyDef = new BodyDef();
        bodyDef.position.set(defaultPosition.x + shipSize.x, defaultPosition.y + shipSize.y);
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(shipSize.x * 0.5f, shipSize.y * 0.5f);

        //TODO change to normal values later
        fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.isSensor = true;
    }

    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {
        shipSprite.setPosition(
                position.x - shipSprite.getWidth() * 0.5f,
                position.y - shipSprite.getHeight() * 0.5f
        );
        shipSprite.draw(batch);
    }

    @Override
    public Vector2 getSize() {
        return new Vector2(shipSprite.getWidth(), shipSprite.getHeight());
    }

    @Override
    public void update(final Body body, float delta) {

    }

    @Override
    public void beginCollisionContact(final Body bodyA) {

    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.BOMBER_SHIP;
    }


    final private Sprite shipSprite;
}
