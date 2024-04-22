package game.data;

public class CropData extends PlantableData
{
    private int waterDrop;
    private int growTime;
    private int sellPrice;

    public CropData(String name, int growTime, int sellPrice, int buyPrice, int waterDrop)
    {
        super(name, buyPrice);
        this.growTime = growTime;
        this.sellPrice = sellPrice;
        this.waterDrop = waterDrop;
    }

    public int getGrowTime() 
    {
        return growTime;
    }

    public int getSellPrice() 
    {
        return sellPrice;
    }

    public int getWaterDrop()
    {
        return waterDrop;
    }

    @Override
    public String toString() 
    {
        return String.format("Crop[%s]", super.toString());
    }
}
