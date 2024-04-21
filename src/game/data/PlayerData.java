package game.data;

import java.util.ArrayList;

public class PlayerData 
{
    public int gold;
    public ArrayList<String> unlockPlantable;
    public PlayerData()
    {
        gold = 0;
        unlockPlantable = new ArrayList<String>();
    }
}
