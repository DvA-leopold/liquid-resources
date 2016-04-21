package com.liquidresources.game.model.common.utils;

import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.math.Vector2;

final public class LocationC implements Location<Vector2> {
    public LocationC() {
        this.position = new Vector2();
        this.orientation = 0;
    }

    @Override
    public Vector2 getPosition () {
        return position;
    }

    @Override
    public float getOrientation () {
        return orientation;
    }

    @Override
    public void setOrientation (float orientation) {
        this.orientation = orientation;
    }

    @Override
    public Location<Vector2> newLocation () {
        return new LocationC();
    }

    @Override
    public float vectorToAngle (Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector (Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector, angle);
    }


    private Vector2 position;
    private float orientation;
}
