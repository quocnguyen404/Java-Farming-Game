package game.data;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerData implements Serializable
{
    private static final long serialVersionUID = 6L;
    public int gold;
    public ArrayList<String> unlockPlantable;
    public PlayerData()
    {
        gold = 0;
        unlockPlantable = new ArrayList<>();
    }
}
