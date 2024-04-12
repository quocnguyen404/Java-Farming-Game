package game.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

import engine.Sprite;
import game.GameConstant;
import game.data.Sprites.SpriteID;

//singleton
public final class ConfigDataHelper
{
    private static ConfigDataHelper instance;
    
    private int gold = 10;
    private HashMap<String, PlantData> plantData;
    private Sprites sprites;

    public static ConfigDataHelper getInstance()
    {
        if (instance == null)
            instance = new ConfigDataHelper();
        return instance;
    }

    //private ConfigDataHelper
    private ConfigDataHelper()
    {
        sprites = new Sprites();
        loadPlantData();
    }

    private void loadPlantData()
    {
        plantData = new HashMap<String, PlantData>();
        
        try 
        {
            File data = new File(GameConstant.PLANTDATA_PATH);
            Scanner scanner = new Scanner(data);
            
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();

                if (!line.startsWith("//"))
                {
                    String[] split = line.split("-");
                    PlantData plant = new PlantData(split[0],
                                                    Integer.parseInt(split[1]), 
                                                    Integer.parseInt(split[2]), 
                                                    Integer.parseInt(split[3]));
                    plantData.put(split[0], plant);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
    }

    public PlantData getPlantData(String name)
    {
        return plantData.get(name);
    }

    public Sprite getSprite(SpriteID id)
    {
        return sprites.getSprite(id);
    }
}
