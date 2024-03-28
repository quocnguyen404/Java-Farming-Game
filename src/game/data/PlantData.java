package game.data;

public class PlantData 
{
    private String name;
    private int growTime;
    private int sellPrice;
    private int buyPrice;

    public PlantData()
    {
        this("", 0, 0, 0);
    }

    public PlantData(String name, int growTime, int sellPrice, int buyPrice)
    {
        this.name = name;
        this.growTime = growTime;
        this.sellPrice = sellPrice;
        this.buyPrice = buyPrice;
    }

    public String getName() 
    {
        return name;
    }

    public int getGrowTime() 
    {
        return growTime;
    }

    public int getSellPrice() 
    {
        return sellPrice;
    }

    public int getBuyPrice()
    {
        return buyPrice;
    }
}
