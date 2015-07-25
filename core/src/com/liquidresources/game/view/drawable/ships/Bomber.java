package com.liquidresources.game.view.drawable.ships;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.liquidresources.game.model.resource.manager.ResourceManager;

public class Bomber extends Ship {
    public Bomber(Vector2 defaultPosition, Vector2 shipSize, int defaultHealth) {
        super(defaultPosition, shipSize, defaultHealth);
        shipSprite = new Sprite((Texture) ResourceManager.getInstance().get("ships/bomber.png"));
        shipSprite.setPosition(defaultPosition.x, defaultPosition.y);
        shipSprite.setSize(shipSize.x, shipSize.y);
    }

    @Override
    protected void initBodyDefAndFixture() {
        bodyDef = new BodyDef();
        bodyDef.position.set(defaultPosition.x + shipSize.x * 0.5f, defaultPosition.y + shipSize.y * 0.5f);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(shipSize.x * 0.5f, shipSize.y * 0.5f);

        //TODO change to normal values later
        fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.density = 0.9f; // плотность
        fixtureDef.friction = 0.1f; // сцепление
        fixtureDef.restitution = 0.1f; // эластичность
        fixtureDef.isSensor = true;
    }

    @Override
    public int doDamage() {
        return 55;
    }

    @Override
    public void draw(Batch batch, Vector2 position, float delta) {
        shipSprite.setPosition(position.x, position.y);
        shipSprite.draw(batch);
    }

    @Override
    public void dispose() {

    }


    final private Sprite shipSprite;
}
