package com.liquidresources.game.view.drawable.bullets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.drawable.DrawableBody;

public class Rocket implements DrawableBody {
    public Rocket(Vector2 defaultPosition, Vector2 rocketSize) {
        rocketSprite = new Sprite((Texture) ResourceManager.getInstance().get("drawable/bullets/rocket.png"));
        rocketSprite.setPosition(defaultPosition.x - rocketSize.x * 0.5f, defaultPosition.y - rocketSize.y * 0.5f);
        rocketSprite.setSize(rocketSize.x, rocketSize.y);

        bodyDef = new BodyDef();
        bodyDef.position.set(defaultPosition.x, defaultPosition.y);
        bodyDef.type = BodyDef.BodyType.KinematicBody;

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(rocketSize.x * 0.5f, rocketSize.y *0.5f);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
    }

    @Override
    public void draw(Batch batch, Vector2 position, float delta) {
        rocketSprite.setPosition(
                position.x - rocketSprite.getWidth() * 0.5f,
                position.y - rocketSprite.getHeight() * 0.5f
        );
        rocketSprite.draw(batch);
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
    public void dispose() {

    }


    private BodyDef bodyDef;
    private FixtureDef fixtureDef;
    private Sprite rocketSprite;
}
