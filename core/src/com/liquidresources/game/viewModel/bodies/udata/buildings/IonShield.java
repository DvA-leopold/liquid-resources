package com.liquidresources.game.viewModel.bodies.udata.buildings;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.liquidresources.game.model.types.BodyTypes;
import com.liquidresources.game.model.types.RelationTypes;
import com.liquidresources.game.view.UConverter;

public class IonShield extends Building {
    public IonShield(final Vector2 startPosition,
                     final Vector2 endPosition,
                     final Vector2 buildingSize,
                     final RelationTypes relationType) {
        super(startPosition, endPosition, buildingSize, relationType);
    }

    @Override
    protected void initBodyDefAndFixture(final Vector2 startPosition,
                                         final Vector2 endPosition,
                                         final Vector2 buildingSize) {
        bodyDef = new BodyDef();
        bodyDef.position.set(endPosition.x, startPosition.y + buildingSize.y - UConverter.M2P(10));
        bodyDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape ionShieldShape = new PolygonShape();
        ionShieldShape.setAsBox(endPosition.x - startPosition.x, buildingSize.y + UConverter.M2P(20));

//        ChainShape ionShieldShape = new ChainShape();
//        Vector2[] shapeChain = new Vector2[50];
//        for (int i = 0; i < 50; ++i) {
//            shapeChain[i] = new Vector2(startPosition.x, startPosition.y + 100);
//        }
//        ionShieldShape.createChain(shapeChain);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = ionShieldShape;
        fixtureDef.isSensor = true;
    }

    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {

    }

    @Override
    public Vector2 getSize() {
        return null;
    }

    @Override
    public void update(final Body body, float delta) {
    }

    @Override
    public void beginCollisionContact(final Body bodyA) { }

    @Override
    public BodyTypes getBodyType() {
        return BodyTypes.ION_SHIELD;
    }

    public void setActive(boolean isActive) {
        super.isActive = isActive;
    }
}
