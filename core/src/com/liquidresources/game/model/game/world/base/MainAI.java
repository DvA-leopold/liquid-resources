package com.liquidresources.game.model.game.world.base;

public class MainAI {
    public MainAI() {
        shieldPower = 0;
        oilBarrels = 0;
        waterBarrels = 0;
    }

    public boolean changeOil(int oilBarrels) {
        if (this.oilBarrels + oilBarrels >= 0) {
            this.oilBarrels += oilBarrels;
            return true;
        } else {
            return false;
        }
    }

    public boolean changeWater(int waterBarrels) {
        if (this.waterBarrels + waterBarrels >= 0) {
            this.waterBarrels += waterBarrels;
            return true;
        } else {
            return false;
        }
    }

    public boolean addShieldPower(int shieldPower, int oilShieldConversionCoefficient) {
        if (oilBarrels - shieldPower * oilShieldConversionCoefficient >= 0) {
            oilBarrels -= shieldPower * oilShieldConversionCoefficient;
            this.shieldPower += shieldPower;
            return true;
        } else {
            return false;
        }
    }

    /**
     * this mainAI is a storage for all useful resources and convert resources from one to another
     * update method just add obtained resources from different stations
     *
     * @param oilBarrels   coefficient passed from the oil station class
     * @param waterBarrels coefficient passed from the water station class
     */
    public void update(long oilBarrels, long waterBarrels) {
        this.oilBarrels = this.oilBarrels + oilBarrels < Long.MAX_VALUE
                ? this.oilBarrels + oilBarrels
                : Long.MAX_VALUE - 1;

        this.waterBarrels = this.waterBarrels + waterBarrels < Long.MAX_VALUE
                ? this.waterBarrels + waterBarrels
                : Long.MAX_VALUE - 1;
    }

    public long getOilBarrels() {
        return oilBarrels;
    }

    public long getWaterBarrels() {
        return waterBarrels;
    }

    public int getShieldPower() {
        return shieldPower;
    }


    private long oilBarrels, waterBarrels;
    private int shieldPower;
}
