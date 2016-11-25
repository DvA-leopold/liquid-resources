package com.liquidresources.game.utils;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;


public final class LocationC implements Location<Vector2> {
    private Vector2 position = new Vector2(30.0f, 20.5f);
    private float orientation = 0.0F;

    public LocationC() { }

    public Vector2 getPosition() {
        return this.position;
    }

    public float getOrientation() {
        return this.orientation;
    }

    public void setOrientation(float orientation) {
        this.orientation = orientation;
    }

    public Location<Vector2> newLocation() {
        return new LocationC();
    }

    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector, angle);
    }
}
