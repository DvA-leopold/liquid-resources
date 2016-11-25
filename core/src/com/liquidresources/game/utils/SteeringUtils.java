package com.liquidresources.game.utils;

import com.badlogic.gdx.math.Vector2;


public class SteeringUtils {
    private SteeringUtils() { }

    public static float vectorToAngle(Vector2 vector) {
        return (float)Math.atan2((double)(-vector.x), (double)vector.y);
    }

    public static Vector2 angleToVector(Vector2 outVector, float angle) {
        outVector.x = -((float)Math.sin((double)angle));
        outVector.y = (float)Math.cos((double)angle);
        return outVector;
    }
}
