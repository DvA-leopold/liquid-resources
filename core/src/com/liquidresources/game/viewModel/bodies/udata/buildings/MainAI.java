package com.liquidresources.game.viewModel.bodies.udata.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.liquidresources.game.model.BodyType;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.viewModel.bodies.udata.UniversalBody;

public class MainAI extends Building {
    public MainAI(final Vector2 defaultPosition, final Vector2 buildingSize) {
        super(defaultPosition, null, buildingSize);
        mainAI = new Sprite((Texture) ResourceManager.getInstance().get("drawable/buildings/mainAI.png"));
        mainAI.setPosition(defaultPosition.x, defaultPosition.y);
        mainAI.setSize(buildingSize.x, buildingSize.y * 2);
    }

    @Override
    protected void initBodyDefAndFixture(Vector2 startPosition, Vector2 endPosition, Vector2 buildingSize) {
        bodyDef = new BodyDef();
        bodyDef.position.set(startPosition.x + buildingSize.x * 0.5f, startPosition.y + buildingSize.y);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(buildingSize.x * 0.5f, buildingSize.y);

        //TODO change to normal values later
        fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.isSensor = true;

        //bodyShape.dispose(); //TODO memory leakage bodies shape dispose
    }

    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {
        mainAI.draw(batch);
    }

    @Override
    public Vector2 getSize() {
        return new Vector2(mainAI.getWidth(), mainAI.getHeight());
    }

    @Override
    public void update(final Body body, float delta) {

    }

    @Override
    public void beginCollisionContact(final Body bodyA) {

    }

    @Override
    public BodyType getBodyType() {
        return BodyType.MAIN_AI;
    }

    public Vector2 getPosition() {
        return new Vector2(mainAI.getX(), mainAI.getY());
    }


    private Sprite mainAI;
}
