package game.plantable;

import game.data.ItemData;

public class TimeBuff extends Item
{

    private double growthSpeed;

    public TimeBuff(ItemData data, double growthSpeed) {
        super(data);
        this.growthSpeed = growthSpeed;
    }

    @Override
    public void activate() {
        // Khi TimeBuff được kích hoạt, giảm thời gian trồng cây
        Farm.setGrowthSpeed(Farm.getGrowthSpeed() * growthSpeed);
        System.out.println("TimeBuff activated! Growth speed increased by " + (growthSpeed * 100) + "%.");
    }
    
}
