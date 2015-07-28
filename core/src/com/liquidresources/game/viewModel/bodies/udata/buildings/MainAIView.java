package com.liquidresources.game.viewModel.bodies.udata.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.liquidresources.game.model.Updatable;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.DrawableBody;

public class MainAIView implements DrawableBody, Updatable {
    public MainAIView(Vector2 initCoords, float graphicsWidth, float graphicsHeight) {
        mainAI = new Sprite((Texture) ResourceManager.getInstance().get("drawable/buildings/mainAI.png"));
        mainAI.setPosition(initCoords.x, initCoords.y);
        mainAI.setSize(graphicsWidth, graphicsHeight * 2);

        bodyDef = new BodyDef();
        bodyDef.position.set(initCoords.x + graphicsWidth * 0.5f, initCoords.y + graphicsHeight);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(graphicsWidth * 0.5f, graphicsHeight);

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
    public void draw(Batch batch, Vector2 position, float delta) {
        mainAI.draw(batch);
    }

    @Override
    public void update(Body body) {

    }

    @Override
    public BodyDef getBodyDef() {
        return bodyDef;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    public float getWidth() {
        return mainAI.getWidth();
    }

    public Vector2 getPosition() {
        return new Vector2(mainAI.getX(), mainAI.getY());
    }


    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

    private Sprite mainAI;
}
