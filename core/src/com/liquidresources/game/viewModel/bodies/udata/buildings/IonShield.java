package com.liquidresources.game.viewModel.bodies.udata.buildings;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.liquidresources.game.model.BodyType;

public class IonShield extends Building {
    public IonShield(final Vector2 defaultPosition, final Vector2 buildingSize, BodyDef.BodyType bodyType) {
        super(defaultPosition, buildingSize, bodyType);
    }


    @Override
    protected void initBodyDefAndFixture(Vector2 defaultPosition, Vector2 bulletSize, BodyDef.BodyType bodyType) {

    }

    @Override
    public void draw(final Batch batch, final Vector2 position, float delta) {

    }

    @Override
    public void update(final Body body, float delta) {

    }

    @Override
    public void beginCollisionContact(final Body bodyA) {

    }

    @Override
    public BodyType getBodyType() {
        return BodyType.ION_SHIELD;
    }
}
