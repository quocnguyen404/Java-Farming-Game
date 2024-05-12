package game.plantable;

public class Farm {
    private static double productivityMultiplier = 1.0; 
    private static double waterRequirementMultiplier = 1.0; 
    private static double growthSpeedMultiplier = 1.0; 

    public static double getProductivityMultiplier() {
        return productivityMultiplier;
    }

    public static void setProductivityMultiplier(double multiplier) {
        productivityMultiplier = multiplier;
    }

    public static double getWaterRequirementMultiplier() {
        return waterRequirementMultiplier;
    }

    public static void setWaterRequirementMultiplier(double multiplier) {
        waterRequirementMultiplier = multiplier;
    }

    public static double getGrowthSpeedMultiplier() {
        return growthSpeedMultiplier;
    }

    public static void setGrowthSpeedMultiplier(double multiplier) {
        growthSpeedMultiplier = multiplier;
    }
}

