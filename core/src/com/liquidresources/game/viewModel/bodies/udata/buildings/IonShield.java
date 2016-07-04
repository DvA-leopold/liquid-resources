package com.liquidresources.game.viewModel.bodies.udata.buildings;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.liquidresources.game.model.BodyFactoryWrapper;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.viewModel.bodies.udata.SteerableBody;

import static com.liquidresources.game.model.common.utils.UConverter.m2p;

public class IonShield extends SteerableBody {
    static {
        ionShieldSize = m2p(Gdx.graphics.getWidth() * 0.08f, Gdx.graphics.getHeight() * 0.08f);
    }

    public IonShield(final Vector2 startPosition,
                     final Vector2 endPosition,
                     final RelationTypes relationType) {
        super(relationType, 100);

        if (bodyDef == null) {
            bodyDef = new BodyDef();
            bodyDef.type = BodyDef.BodyType.StaticBody;
        }
        bodyDef.position.set(endPosition.x, startPosition.y + ionShieldSize.y - m2p(10)); // TODO remove magic numbers

        if (ionShieldShape == null) {
            ionShieldShape = new PolygonShape();
            ionShieldShape.setAsBox(endPosition.x - startPosition.x, ionShieldSize.y + m2p(20));
        }

//        ChainShape ionShieldShape = new ChainShape();
//        Vector2[] shapeChain = new Vector2[50];
//        for (int i = 0; i < 50; ++i) {
//            shapeChain[i] = new Vector2(startPosition.x, startPosition.y + 100);
//        }
//        ionShieldShape.createChain(shapeChain);

        if (fixtureDef == null) {
            fixtureDef = new FixtureDef();
            fixtureDef.shape = ionShieldShape;
            fixtureDef.isSensor = true;
        }
    }

    @Override
    public void blendSteeringInit(Array<SteerableBody> agents) {

    }

    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {

    }

    @Override
    public Vector2 getSize() {
        return ionShieldSize;
    }

    @Override
    public void update(final Body body, float delta) { }

    @Override
    public void beginCollisionContact(final Body bodyA, BodyFactoryWrapper bodyFactoryWrapper) { }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.ION_SHIELD;
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
        if (ionShieldShape != null) {
            ionShieldShape.dispose();
            ionShieldShape = null;
        }
        fixtureDef = null;
        bodyDef = null;
    }


    final private Sprite ionShield = new Sprite();

    static private BodyDef bodyDef;
    static private FixtureDef fixtureDef;
    static private PolygonShape ionShieldShape;

    static final private Vector2 ionShieldSize;
}
