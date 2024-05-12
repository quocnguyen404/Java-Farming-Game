package game.plantable.item;

import game.data.ItemData;
import game.plantable.Farm;

public class WaterBuff extends Item
{

    private double waterRequirementMultiplier;

    public WaterBuff(ItemData data, double waterRequirementMultiplier) 
    {
        super(data);
        this.waterRequirementMultiplier = waterRequirementMultiplier;
    }

    @Override
    public void activate() 
    {
        // Khi WaterBuff được kích hoạt, giảm nhu cầu nước của cây
        Farm.setWaterRequirementMultiplier(Farm.getWaterRequirementMultiplier() * waterRequirementMultiplier);
        System.out.println("WaterBuff activated! Water requirement decreased by " + (waterRequirementMultiplier * 100) + "%.");
    }
    
}
