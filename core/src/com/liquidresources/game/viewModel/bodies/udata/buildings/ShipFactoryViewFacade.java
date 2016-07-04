package com.liquidresources.game.viewModel.bodies.udata.buildings;

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
import com.liquidresources.game.view.particles.SmokeParticles;
import com.liquidresources.game.viewModel.bodies.udata.UniversalBodyImpl;

import static com.liquidresources.game.model.common.utils.UConverter.m2p;

public class ShipFactoryViewFacade extends UniversalBodyImpl {
    static {
        shipFactorySize = m2p(Gdx.graphics.getWidth() * 0.08f, Gdx.graphics.getHeight() * 0.08f);
    }

    public ShipFactoryViewFacade(final Vector2 defaultPosition, final RelationTypes relationType) {
        super(relationType, 100);
        Texture factoryTexture = (Texture) ResourceManager.instance().get("drawable/buildings/shipFactory.png");

        shipFactory = new Sprite(factoryTexture);
        shipFactory.setPosition(defaultPosition.x, defaultPosition.y);
        shipFactory.setSize(shipFactorySize.x, shipFactorySize.y);

        smokeParticles = new SmokeParticles( //TODO разобраться c положением дыма
                new Vector2(defaultPosition.x + m2p(35), defaultPosition.y + shipFactory.getHeight() * 0.8f),
                true
        );

        if (bodyDef == null) {
            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
        }
        bodyDef.position.set(defaultPosition.x + shipFactorySize.x * 0.5f, defaultPosition.y + shipFactorySize.y * 0.5f);

        if (shipFactoryShape == null) {
            shipFactoryShape = new PolygonShape();
            shipFactoryShape.setAsBox(shipFactorySize.x * 0.5f, shipFactorySize.y * 0.5f);
        }

        if (fixtureDef == null) {
            fixtureDef = new FixtureDef();
            fixtureDef.shape = shipFactoryShape;
        }
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
    public void beginCollisionContact(final Body bodyA, BodyFactoryWrapper bodyFactoryWrapper) {

    }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.SHIP_FACTORY;
    }

    @Override
    public BodyDef getBodyDef() {
        return bodyDef;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    public SmokeParticles getSmokeParticles() {
        return smokeParticles;
    }

    @Override
    public Vector2 getSize() {
        return shipFactorySize;
    }

    static public void dispose() {
        if (shipFactoryShape != null) {
            shipFactoryShape.dispose();
            shipFactoryShape = null;
        }
        fixtureDef = null;
        bodyDef = null;
    }


    final private Sprite shipFactory;

    final private SmokeParticles smokeParticles;

    static private BodyDef bodyDef;
    static private FixtureDef fixtureDef;
    static private PolygonShape shipFactoryShape;

    static final private Vector2 shipFactorySize;
}
