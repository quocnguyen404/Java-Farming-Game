package game.plantable;

import game.data.ItemData;

public class GoldBuff extends Item
{
    private double productivity;

    public GoldBuff(ItemData data, double productivity) {
        super(data);
        this.productivity = productivity;
    }

    @Override
    public void activate() {
        // Khi GoldBuff được kích hoạt, tăng hiệu suất sản xuất của cây
        Farm.setProductivity(Farm.getProductivity() * productivity);
        System.out.println("GoldBuff activated! Productivity increased by " + (productivity * 100) + "%.");
    }

}
