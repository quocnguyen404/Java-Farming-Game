package game.data;

import java.util.HashMap;

public class GameData 
{
    protected PlayerData playerData;
    protected HashMap<String, PlantData> plantData;

    public GameData()
    {
        playerData = new PlayerData();
        plantData = new HashMap<String, PlantData>();
    }
}
