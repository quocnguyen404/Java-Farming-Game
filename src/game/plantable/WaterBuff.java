package game.plantable;

import game.data.ItemData;

public class WaterBuff extends Item
{

    private double waterRequire;

    public WaterBuff(ItemData data, double waterRequire) {
        super(data);
        this.waterRequire = waterRequire;
    }

    @Override
    public void activate() {
        // Khi WaterBuff được kích hoạt, giảm nhu cầu nước của cây
        Farm.setWaterRequirement(Farm.getWaterRequirement() * waterRequire);
        System.out.println("WaterBuff activated! Water requirement decreased by " + (waterRequire * 100) + "%.");
    }
    
}
