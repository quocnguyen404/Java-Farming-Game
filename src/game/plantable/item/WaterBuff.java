package game.plantable.item;

import game.data.ItemData;
import game.plantable.crop.Crop;

public class WaterBuff extends Item
{
    public WaterBuff(ItemData data) 
    {
        super(data);
    }

    @Override
    public void activate(Crop crop) 
    {
        // Khi WaterBuff được kích hoạt, giảm nhu cầu nước của cây
        crop.getWaterBuff(getItemData().getBuffAmount());
    }
    
}
