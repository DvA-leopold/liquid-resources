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
import com.liquidresources.game.view.UConverter;
import com.liquidresources.game.view.particles.SmokeParticles;

public class ShipFactoryViewFacade extends Building {
    public ShipFactoryViewFacade(final Vector2 defaultPosition,
                                 final Vector2 buildingSize,
                                 final RelationTypes relationType) {
        super(defaultPosition, null, buildingSize, relationType);

        Texture factoryTexture = (Texture) ResourceManager.getInstance().get("drawable/buildings/shipFactory.png");

        shipFactory = new Sprite(factoryTexture);
        shipFactory.setPosition(defaultPosition.x, defaultPosition.y);
        shipFactory.setSize(buildingSize.x, buildingSize.y);

        smokeParticles = new SmokeParticles( //TODO разобраться c положением дыма
                new Vector2(
                        defaultPosition.x + UConverter.M2P(35),
                        defaultPosition.y + shipFactory.getHeight() * 0.8f),
                true
        );
    }

    /**
     * this is drawable body method, so its draw textures for this object
     * @param batch batch which draw everything
     * @param position new position, can be <code>null</code> for this class
     * @param delta delta time
     */
    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {
        smokeParticles.draw(batch, delta);
        shipFactory.draw(batch);
    }

    @Override
    public void update(final Body body, float delta) {

    }

    @Override
    public void beginCollisionContact(final Body bodyA) {

    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.SHIP_FACTORY;
    }

    @Override
    protected void initBodyDefAndFixture(Vector2 startPosition, Vector2 endPosition, Vector2 buildingSize) {
        bodyDef = new BodyDef();
        bodyDef.position.set(startPosition.x + buildingSize.x * 0.5f, startPosition.y + buildingSize.y * 0.5f);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape bodyShape = new PolygonShape();
        bodyShape.setAsBox(buildingSize.x * 0.5f, buildingSize.y * 0.5f);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = bodyShape;
        fixtureDef.isSensor = true;
    }

    public SmokeParticles getSmokeParticles() {
        return smokeParticles;
    }

    @Override
    public Vector2 getSize() {
        return new Vector2(shipFactory.getWidth(), shipFactory.getHeight());
    }

    public Vector2 getShipFactoryPosition() {
        return bodyDef.position;
    }


    final private Sprite shipFactory;

    final private SmokeParticles smokeParticles;
}
