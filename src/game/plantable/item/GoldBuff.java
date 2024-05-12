package game.plantable.item;

import game.data.ItemData;
import game.plantable.crop.Crop;

public class GoldBuff extends Item
{
    private double productivityMultiplier;

    public GoldBuff(ItemData data, double productivityMultiplier) 
    {
        super(data);
        this.productivityMultiplier = productivityMultiplier;
    }

    @Override
    public void activate(Crop crop)
    {
        // Khi GoldBuff được kích hoạt, tăng hiệu suất sản xuất của cây
        //
        crop.getBuff();
        System.out.println("GoldBuff activated! Productivity increased by " + (productivityMultiplier * 100) + "%.");
    }

}
