package com.liquidresources.game.view;

import com.badlogic.gdx.math.Vector2;

public class UConverter {
    public static Vector2 P2M(final Vector2 pPosition) {
        pPosition.x *= PMCoefficient;
        pPosition.y *= PMCoefficient;
        return pPosition;
    }

    public static Vector2 M2P(final Vector2 mPosition) {
        mPosition.x /= PMCoefficient;
        mPosition.y /= PMCoefficient;
        return mPosition;
    }

    public static float M2P(float oneDimensionCoord) {
        return oneDimensionCoord / PMCoefficient;
    }

    public static float P2M(float oneDemensionCoord) {
        return oneDemensionCoord * PMCoefficient;
    }

    public static Vector2 M2P(float xCoord, float yCoord) {
        return new Vector2(xCoord / PMCoefficient, yCoord / PMCoefficient);
    }

    public static float getPMCoefficient() {
        return PMCoefficient;
    }


    final private static float PMCoefficient = 16;
}
