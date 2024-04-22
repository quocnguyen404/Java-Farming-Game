package game.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;

import engine.Sprite;
import game.GameConstant;
import game.data.Sprites.AnimationID;
import game.data.Sprites.SpriteID;

//singleton
public final class ConfigDataHelper
{
    private static ConfigDataHelper instance;
    
    private PlayerData playerData;
    private HashMap<String, PlantableData> plantData;
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
        loadPlantableData();
        loadPlayerData();
    }

    private void loadPlantableData()
    {
        plantData = new HashMap<String, PlantableData>();
        
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
                    PlantableData plant;
                    if (split.length > 2)
                    {
                        plant = new CropData(split[0],
                                              Integer.parseInt(split[1]), 
                                              Integer.parseInt(split[2]), 
                                              Integer.parseInt(split[3]));
                    }
                    else
                        plant = new PlantableData(split[0], Integer.parseInt(split[1]));

                    plantData.put(split[0], plant);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }
    
    private final String PLAYERDATA_PATH = "Player.data";
    private void loadPlayerData()
    {
        try 
        {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(PLAYERDATA_PATH)));
            playerData = (PlayerData) ois.readObject();
            ois.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Doesn't exist player data");
            System.out.println("Load default player data");
            playerData = new PlayerData();
            playerData.gold = 1;
            playerData.unlockPlantable.add("ONION");
            playerData.unlockPlantable.add("POTATO");
        }
    }

    public void savePlayerData()
    {
        try
        {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(PLAYERDATA_PATH)));
            oos.writeObject(playerData);
            oos.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Saving exception");
        }
    }

    public boolean buyPlantable(PlantableData plantable)
    {
        if (plantable.getBuyPrice() <= playerData.gold)
        {
            playerData.gold -= plantable.getBuyPrice();
            System.out.println("Buying " + plantable.getName() + ": " + plantable.getBuyPrice() + " gold");
            return true;
        }
        System.out.println("Not enough money");
        return false;
    }

    public void cancelBuy(PlantableData plantableData)
    {
        playerData.gold += plantableData.getBuyPrice();
    }

    public int getPlantableNumber()
    {
        return plantData.size();
    }

    public Object[] getPlantableNames()
    {
        return plantData.keySet().toArray();
    }

    public PlantableData getPlantData(String name)
    {
        return plantData.get(name);
    }

    public Sprite[] getAnimtedSprite(AnimationID id)
    {
        return sprites.getAnimatedSprite(id);
    }

    public Sprite getSprite(SpriteID id)
    {
        return sprites.getSprite(id);
    }
}
