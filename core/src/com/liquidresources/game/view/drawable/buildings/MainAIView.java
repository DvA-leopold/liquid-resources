package com.liquidresources.game.view.drawable.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.drawable.DrawableBody;

public class MainAIView implements DrawableBody {
    public MainAIView(Vector2 initCoords, float graphicsWidth, float graphicsHeight) {
        mainAI = new Sprite((Texture) ResourceManager.getInstance().get("buildings/mainAI.png"));
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

        //bodyShape.dispose(); TODO dispose shape no longer needed, now its crash
    }

    @Override
    public void draw(Batch batch, Vector2 position, float delta) {
        mainAI.draw(batch);
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
    public void dispose() { }

    public float getWidth() {
        return mainAI.getWidth();
    }


    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

    private Sprite mainAI;
}
