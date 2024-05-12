package game.plantable.item;

import game.data.ItemData;
import game.plantable.crop.Crop;

public class WaterBuff extends Item
{

    private double waterRequirementMultiplier;

    public WaterBuff(ItemData data, double waterRequirementMultiplier) 
    {
        super(data);
        this.waterRequirementMultiplier = waterRequirementMultiplier;
    }

    @Override
    public void activate(Crop crop) 
    {
        // Khi WaterBuff được kích hoạt, giảm nhu cầu nước của cây
        //
        crop.getBuff();
        System.out.println("WaterBuff activated! Water requirement decreased by " + (waterRequirementMultiplier * 100) + "%.");
    }
    
}
