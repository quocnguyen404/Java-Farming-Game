package game.plantable.item;

import game.data.ItemData;
import game.plantable.crop.Crop;

public class TimeBuff extends Item
{
    public TimeBuff(ItemData data) 
    {
        super(data);
    }

    @Override
    public void activate(Crop crop) 
    {
        // Khi TimeBuff được kích hoạt, giảm thời gian trồng cây
        //
        crop.getBuff();
    }
    
}
