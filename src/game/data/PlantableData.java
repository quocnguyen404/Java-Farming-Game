package game.data;

public class PlantableData 
{
    private String name;
    private int buyPrice;

    public PlantableData(String name, int buyPrice)
    {
        this.name = name;
        this.buyPrice = buyPrice;
    }

    public String getName() 
    {
        return name;
    }

    public int getBuyPrice() 
    {
        return buyPrice;
    }

    @Override
    public String toString() {
        return String.format("Plantable[name=%s]", name);
    }
}
