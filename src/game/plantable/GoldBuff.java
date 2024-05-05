package game.plantable;

import game.data.ItemData;

public class GoldBuff extends Item
{
    private double productivityMultiplier;

    public GoldBuff(ItemData data, double productivityMultiplier) {
        super(data);
        this.productivityMultiplier = productivityMultiplier;
    }

    @Override
    public void activate() {
        // Khi GoldBuff được kích hoạt, tăng hiệu suất sản xuất của cây
        Farm.setProductivityMultiplier(Farm.getProductivityMultiplier() * productivityMultiplier);
        System.out.println("GoldBuff activated! Productivity increased by " + (productivityMultiplier * 100) + "%.");
    }

}
