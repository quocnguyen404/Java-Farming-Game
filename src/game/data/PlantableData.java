package game.data;

import java.io.Serializable;

public class PlantableData implements Serializable
{
    private static final long serialVersionUID = 5L;
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
