package game.plantable.item;

import game.data.ItemData;
import game.plantable.Farm;

public class TimeBuff extends Item
{

    private double growthSpeedMultiplier;

    public TimeBuff(ItemData data, double growthSpeedMultiplier) 
    {
        super(data);
        this.growthSpeedMultiplier = growthSpeedMultiplier;
    }

    @Override
    public void activate() 
    {
        // Khi TimeBuff được kích hoạt, giảm thời gian trồng cây
        Farm.setGrowthSpeedMultiplier(Farm.getGrowthSpeedMultiplier() * growthSpeedMultiplier);
        System.out.println("TimeBuff activated! Growth speed increased by " + (growthSpeedMultiplier * 100) + "%.");
    }
    
}
