package com.liquidresources.game.viewModel.bodies.udata.buildings;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.liquidresources.game.model.BodyType;
import com.liquidresources.game.model.game.world.base.MainAIModel;

public class IonShield extends Building {
    public IonShield(final Vector2 startPosition, final Vector2 endPosition, final Vector2 buildingSize) {
        super(startPosition, endPosition, buildingSize);
    }

    @Override
    protected void initBodyDefAndFixture(
            final Vector2 startPosition,
            final Vector2 endPosition,
            final Vector2 buildingSize) {

        bodyDef = new BodyDef();
        bodyDef.position.set(endPosition.x - 10, startPosition.y + buildingSize.y - 10);
        bodyDef.type = BodyDef.BodyType.StaticBody;

        PolygonShape ionShieldShape = new PolygonShape();
        ionShieldShape.setAsBox(endPosition.x - startPosition.x, buildingSize.y + 30);

//        ChainShape ionShieldShape = new ChainShape();
//        Vector2[] shapeChain = new Vector2[50];
//        for (int i = 0; i < 50; ++i) {
//            shapeChain[i] = new Vector2(startPosition.x, startPosition.y + 100);
//        }
//        ionShieldShape.createChain(shapeChain);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = ionShieldShape;
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
    public void beginCollisionContact(final Body bodyA) {
        System.out.println("shield collision");
    }

    @Override
    public boolean isActive() {
        return MainAIModel.getShieldStatus();
    }

    @Override
    public BodyType getBodyType() {
        return BodyType.ION_SHIELD;
    }
}
