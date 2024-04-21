package game.data;

public class PlantData extends PlantableData
{
    private int growTime;
    private int sellPrice;

    public PlantData(String name, int growTime, int sellPrice, int buyPrice)
    {
        super(name, buyPrice);
        this.growTime = growTime;
        this.sellPrice = sellPrice;
    }

    public int getGrowTime() 
    {
        return growTime;
    }

    public int getSellPrice() 
    {
        return sellPrice;
    }

    @Override
    public String toString() 
    {
        return String.format("Plant[%s]", super.toString());
    }
}
