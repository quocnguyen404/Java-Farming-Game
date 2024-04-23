package game.data;

public class CropData extends PlantableData
{
    private int growTime;
    private int sellPrice;
    private int waterDrop;
    private int modify;
    
    public CropData(String name, int growTime, int sellPrice, int buyPrice, int waterDrop, int modify)
    {
        super(name, buyPrice);
        this.growTime = growTime;
        this.sellPrice = sellPrice;
        this.waterDrop = waterDrop;
        this.modify = modify;
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

    public int getModify()
    {
        return modify;
    }

    @Override
    public String toString() 
    {
        return String.format("Crop[%s]", super.toString());
    }
}
