package game.data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import engine.Sprite;
import engine.SpriteSheet;
import game.GameConstant;
import game.GameFrame;
import game.component.Region;
import game.data.Sprites.AnimationID;
import game.data.Sprites.SpriteID;
import game.plantable.crop.Crop;

//singleton
public final class ConfigDataHelper
{
    private static ConfigDataHelper instance;
    
    private PlayerData playerData;
    private HashMap<String, PlantableData> plantData;
    private Sprites sprites;
    private Region[] regions;

    public static ConfigDataHelper getInstance()
    {
        if (instance == null)
        {
            instance = new ConfigDataHelper();
            if (instance == null) System.out.println("ConfigDataHelper exception");
        }
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
            File data = new File(GameConstant.PLANTABLE_DATA_PATH);
            Scanner scanner = new Scanner(data);
            
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();

                if (!line.startsWith("//"))
                {
                    String[] split = line.split("-");
                    PlantableData plant;
                    if (split.length == 6) //crop
                    {
                        plant = new CropData(split[0],
                                              Integer.parseInt(split[1]), 
                                              Integer.parseInt(split[2]),
                                              Integer.parseInt(split[3]),
                                              Integer.parseInt(split[4]),
                                              Integer.parseInt(split[5]));
                    }
                    else if (split.length == 4) //item
                    {
                        plant = new ItemData(split[0],
                                            Integer.parseInt(split[1]),
                                            Integer.parseInt(split[2]),
                                            Integer.parseInt(split[3]));
                    }
                    else //plantable
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
        try (FileInputStream fin = new FileInputStream(PLAYERDATA_PATH)) 
        {
            ObjectInputStream ois = new ObjectInputStream(fin);
            playerData = (PlayerData) ois.readObject();
            ois.close();
        } 
        catch (IOException e) 
        {
            // e.printStackTrace();
            System.out.println("Load playerdata exception");
        }
        catch (ClassNotFoundException e)
        {
            System.out.println("Playerdata class exception");
        }

        if(playerData == null)
        {
            System.out.println("Player data not exist");
            System.out.println("Load default player data");
            playerData = new PlayerData();
            playerData.gold = 10;
            playerData.unlockPlantable.add("ONION");
            playerData.unlockPlantable.add("POTATO");
        }
    }

    public void savePlayerData()
    {
        try (FileOutputStream fos = new FileOutputStream(PLAYERDATA_PATH)) 
        {
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(playerData);
            oos.close();
        } 
        catch (Exception e) 
        {
            System.out.println("Save playerdata exception");
        }
    }

    private final String REGIONDATA_PATH = "Regions.data";
    private Region[] loadRegions()
    {
        ArrayList<Region> regions = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(REGIONDATA_PATH))) {
            while (true) {
                try {
                    Region region = (Region) ois.readObject();
                    regions.add(region);
                } catch (ClassNotFoundException e) {
                    // Handle class not found exception if necessary
                    e.printStackTrace();
                } catch (IOException e) {
                    // End of file reached
                    System.out.println("Region file empty");
                    break;
                }
            }
            ois.close();
            System.out.println("Regions loaded successfully.");
        } catch (IOException e) {
            System.out.println("Region.data file not exist");
            regions.add(new Region((GameConstant.WIN_HEIGHT/(2*GameConstant.TILE_WIDTH*GameFrame.X_ZOOM)),
                                   (GameConstant.WIN_HEIGHT/(2*GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM))));
        }
    
        for (Region region : regions)
            region.loadCropEvent();
        
        return regions.toArray(new Region[0]);
    }
    // private Region[] loadRegions() 
    // {
    //     ArrayList<Region> regions = new ArrayList<>();
    //     File file = new File(REGIONDATA_PATH);
    //     if (!file.exists()) 
    //     {
    //         System.out.println("Regions data file does not exist. Initializing default regions.");
    //         regions.add(new Region((GameConstant.WIN_HEIGHT / (2 * GameConstant.TILE_WIDTH * 3)),
    //                 (GameConstant.WIN_HEIGHT / (2 * GameConstant.TILE_HEIGHT * 3))));
    //         return regions.toArray(new Region[0]);
    //     }

    //     try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) 
    //     {
    //         while (true) 
    //         {
    //             try 
    //             {
    //                 Region region = (Region) ois.readObject();
    //                 regions.add(region);
    //             } 
    //             catch (EOFException e) 
    //             {
    //                 break; // End of file reached
    //             } catch (ClassNotFoundException e) 
    //             {
    //                 // Handle class not found exception if necessary
    //                 e.printStackTrace();
    //             }
    //         }
    //     } 
    //     catch (IOException e) 
    //     {
    //         // Handle IOException
    //         e.printStackTrace();
    //     }

    //     return regions.toArray(new Region[0]);
    // }

    public void saveRegions() 
    {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REGIONDATA_PATH))) 
        {
            for (Region region : regions) 
            {
                oos.writeObject(region);
            }
            oos.close();
            System.out.println("Regions saved successfully.");
        } 
        catch (IOException e) 
        {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }
    // public void saveRegion()
    // {
    //     try 
    //     {
    //         FileOutputStream fos = new FileOutputStream(REGIONDATA_PATH, true);
    //         try 
    //         {
    //             ObjectOutputStream oos = new ObjectOutputStream(fos);
    //             for (Region region : regions)
    //             {
    //                 try 
    //                 {
    //                     oos.writeObject(region);
    //                     System.out.println("saved");
    //                 } 
    //                 catch (NotSerializableException e) 
    //                 {
    //                     System.out.println("An object was not serializable, it has not been saved.");
    //                     e.printStackTrace();
    //                 }
    //             }
    //             oos.close();
    //         } 
    //         catch (IOException e) 
    //         {
    //             e.printStackTrace();
    //         }
    //     } 
    //     catch (FileNotFoundException e) 
    //     {
    //         e.printStackTrace();
    //     }
    // }

    public boolean buyPlantable(PlantableData plantable)
    {
        if (plantable.getBuyPrice() <= playerData.gold)
        {
            playerData.gold -= plantable.getBuyPrice();
            String name = plantable.getName().toLowerCase().replaceAll("_", " ");
            GameFrame.onSetMessage.accept("buying " + name);
            return true;
        }
        GameFrame.onSetMessage.accept("not enough money");
        return false;
    }

    public boolean sellingCrop(Crop crop)
    {
        if (crop == null) return false;
        playerData.gold += crop.getSellingPrice();
        GameFrame.defaultMessage.run();
        return true;
    }

    public void cancelBuy(PlantableData plantableData)
    {
        System.out.println("Cancel buy " + plantableData.getName() + ": " + plantableData.getBuyPrice());
        playerData.gold += plantableData.getBuyPrice();
    }

    public Region[] getRegion()
    {
        if (regions == null)
            regions = loadRegions();
        return regions;
    }

    public int getPlayerGold()
    {
        return playerData.gold;
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

    public SpriteSheet getGameSpriteSheet()
    {
        return sprites.getGameSpriteSheet();
    }
}
