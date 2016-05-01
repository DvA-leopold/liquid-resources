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
import com.liquidresources.game.viewModel.bodies.udata.SteerableBodyImpl;

import static com.liquidresources.game.model.common.utils.UConverter.m2p;

public class Capital extends SteerableBodyImpl {
    static {
        capitalSize = m2p(Gdx.graphics.getWidth() * 0.08f, Gdx.graphics.getHeight() * 0.16f);
    }

    public Capital(final Vector2 defaultPosition, final RelationTypes relationType) {
        super(relationType, 100);
        capitalSprite = new Sprite((Texture) ResourceManager.getInstance().get("drawable/buildings/capitalSprite.png"));
        capitalSprite.setPosition(defaultPosition.x, defaultPosition.y);
        capitalSprite.setSize(capitalSize.x, capitalSize.y);

        if (bodyDef == null) {
            bodyDef = new BodyDef();
        }
        bodyDef.position.set(defaultPosition.x + capitalSize.x * 0.5f, defaultPosition.y + capitalSize.y);

        if (capitalShape == null) {
            capitalShape = new PolygonShape();
            capitalShape.setAsBox(capitalSize.x * 0.5f, capitalSize.y);
        }
        if (fixtureDef == null) {
            fixtureDef = new FixtureDef();
            fixtureDef.shape = capitalShape;
            fixtureDef.isSensor = true;
        }
    }

    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {
        capitalSprite.draw(batch);
    }

    @Override
    public Vector2 getSize() {
        return capitalSize;
    }

    @Override
    public void update(final Body body, float delta) { }

    @Override
    public void beginCollisionContact(final Body bodyA, final BodyFactoryWrapper bodyFactoryWrapper) { }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.CAPITAL;
    }

    @Override
    public BodyDef getBodyDef() {
        return bodyDef;
    }

    @Override
    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }

    public static void dispose() {
        if (capitalShape != null) {
            capitalShape.dispose();
            capitalShape = null;
        }
        fixtureDef = null;
        bodyDef = null;
    }


    final private Sprite capitalSprite;

    static private BodyDef bodyDef;
    static private FixtureDef fixtureDef;
    static private PolygonShape capitalShape;

    static final private Vector2 capitalSize;
}
