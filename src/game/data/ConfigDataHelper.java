package game.data;

import engine.Tiles;

//singleton
public final class ConfigDataHelper 
{
    private static ConfigDataHelper instance;
    private GameData gameData;
    private Tiles tiles;

    public static ConfigDataHelper getInstance()
    {
        if (instance == null)
            instance = new ConfigDataHelper();
        return instance;
    }

    //private ConfigDataHelper
    private ConfigDataHelper()
    {
        //initialize game data
        // gameData = new GameData();
        //initialize tile set
        tiles = new Tiles();
    }

    public Tiles getTiles()
    {
        return tiles;
    }

    public PlayerData getPlayerData()
    {
        return gameData.playerData;
    }

    public PlantData getPlantData(String plantName)
    {
        return gameData.plantData.get(plantName);
    }
}
