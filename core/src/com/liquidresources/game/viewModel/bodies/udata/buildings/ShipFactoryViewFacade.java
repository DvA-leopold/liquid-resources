package com.liquidresources.game.viewModel.bodies.udata.buildings;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.liquidresources.game.model.Updatable;
import com.liquidresources.game.model.resource.manager.ResourceManager;
import com.liquidresources.game.view.DrawableBody;
import com.liquidresources.game.view.particles.SmokeParticles;

public class ShipFactoryViewFacade implements DrawableBody, Updatable {
    public ShipFactoryViewFacade(float xDefaultPosition, float yDefaultPosition, float width, float height) {
        Texture factoryTexture = (Texture) ResourceManager.getInstance().get("drawable/buildings/shipFactory.png");

        shipFactory = new Sprite(factoryTexture);
        shipFactory.setPosition(xDefaultPosition, yDefaultPosition);
        shipFactory.setSize(width, height);

        smokeParticles = new SmokeParticles(
                new Vector2(xDefaultPosition + 35, yDefaultPosition + shipFactory.getHeight() * 0.8f),
                true
        );

        bodyDef = new BodyDef();
        bodyDef.position.set(xDefaultPosition + width * 0.5f, yDefaultPosition + height * 0.5f);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(width * 0.5f, height * 0.5f);

        //TODO change to normal values later
        fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.density = 0.4f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.1f;
        fixtureDef.isSensor = true;
    }

    /**
     * this is drawable body method, so its draw textures for this object
     * @param batch batch which draw everything
     * @param position new position, can be <code>null</code> for this class
     * @param delta delta time
     */
    @Override
    public void draw(final Batch batch, Vector2 position, float delta) {
        smokeParticles.draw(batch, delta);
        shipFactory.draw(batch);
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

    public void startEffect() {
        smokeParticles.startEffect();
    }

    public void stopEffect() {
        smokeParticles.stopEffect();
    }

    public float getWidth() {
        return shipFactory.getWidth();
    }

    public float getHeight() {
        return shipFactory.getHeight();
    }

    public Vector2 getShipFactoryPosition() {
        return bodyDef.position;
    }

    public void dispose() {
        smokeParticles.dispose();
    }


    private BodyDef bodyDef;
    private FixtureDef fixtureDef;

    final private Sprite shipFactory;

    final private SmokeParticles smokeParticles;
}
