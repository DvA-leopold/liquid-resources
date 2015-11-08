package com.liquidresources.game.viewModel.bodies.udata.buildings;

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

public class Capital extends Building {
    public Capital(final Vector2 defaultPosition,
                   final Vector2 buildingSize,
                   final RelationTypes relationType) {
        super(defaultPosition, null, buildingSize, relationType);

        capitalSprite = new Sprite((Texture) ResourceManager.getInstance().get("drawable/buildings/capitalSprite.png"));
        capitalSprite.setPosition(defaultPosition.x, defaultPosition.y);
        capitalSprite.setSize(buildingSize.x, buildingSize.y * 2);
    }

    @Override
    protected void initBodyDefAndFixture(Vector2 startPosition, Vector2 endPosition, Vector2 buildingSize) {
        bodyDef = new BodyDef();
        bodyDef.position.set(startPosition.x + buildingSize.x * 0.5f, startPosition.y + buildingSize.y);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(buildingSize.x * 0.5f, buildingSize.y);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.isSensor = true;

//        bodyShape.dispose(); //TODO memory leakage bodies shape dispose
    }

    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {
        capitalSprite.draw(batch);
    }

    @Override
    public void update(final Body body, float delta) {

    }

    @Override
    public void beginCollisionContact(final Body bodyA) {

    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.MAIN_AI;
    }

    public Vector2 getPosition() {
        return new Vector2(capitalSprite.getX(), capitalSprite.getY());
    }

    @Override
    public Vector2 getSize() {
        return new Vector2(capitalSprite.getWidth(), capitalSprite.getHeight());
    }


    final private Sprite capitalSprite;
}
