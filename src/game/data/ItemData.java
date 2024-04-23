package game.data;

public class ItemData extends PlantableData
{
    private int modify;
    private int buffAmount;

    public ItemData(String name, int buyPrice, int modify, int buffAmount) 
    {
        super(name, buyPrice);
        this.modify = modify;
        this.buffAmount = buffAmount;
    }

    public int getModify()
    {
        return modify;
    }

    public int getBuffAmount()
    {
        return buffAmount;
    }
}
