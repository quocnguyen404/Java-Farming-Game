package game.plantable.item;

import game.data.ItemData;
import game.plantable.crop.Crop;

public class GoldBuff extends Item
{
    public GoldBuff(ItemData data) 
    {
        super(data);
    }

    @Override
    public void activate(Crop crop)
    {
        // Khi GoldBuff được kích hoạt, tăng sellprice của cây
        crop.getGoldBuff(getItemData().getBuffAmount());;
    }

}
