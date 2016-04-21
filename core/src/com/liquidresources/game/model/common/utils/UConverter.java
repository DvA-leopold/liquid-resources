package com.liquidresources.game.model.common.utils;

import com.badlogic.gdx.math.Vector2;

final public class UConverter {  // TODO p2m and m2p change definitions and reformat code
    public static Vector2 p2m(final Vector2 pPosition) {
        pPosition.x *= PMCoefficient;
        pPosition.y *= PMCoefficient;
        return pPosition;
    }

    public static Vector2 m2p(final Vector2 mPosition) {
        mPosition.x /= PMCoefficient;
        mPosition.y /= PMCoefficient;
        return mPosition;
    }

    public static float m2p(float oneDimensionCoord) {
        return oneDimensionCoord / PMCoefficient;
    }

    public static float p2m(float oneDimensionCoord) {
        return oneDimensionCoord * PMCoefficient;
    }

    public static Vector2 m2p(float xCoord, float yCoord) {
        return new Vector2(xCoord / PMCoefficient, yCoord / PMCoefficient);
    }

    public static float getPMCoefficient() {
        return PMCoefficient;
    }


    final private static float PMCoefficient = 16;
}
