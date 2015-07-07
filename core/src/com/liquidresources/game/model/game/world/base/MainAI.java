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
