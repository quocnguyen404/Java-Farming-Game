package game.plantable;

public class Farm {
    private static double productivity = 1.0; 
    private static double waterRequire = 1.0; 
    private static double growthSpeed = 1.0; 

    public static double getProductivity() {
        return productivity;
    }

    public static void setProductivity(double multiplier) {
        productivity = multiplier;
    }

    public static double getWaterRequirement() {
        return waterRequire;
    }

    public static void setWaterRequirement(double multiplier) {
        waterRequire = multiplier;
    }

    public static double getGrowthSpeed() {
        return growthSpeed;
    }

    public static void setGrowthSpeed(double multiplier) {
        growthSpeed = multiplier;
    }
}

