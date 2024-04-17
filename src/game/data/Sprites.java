package game.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import engine.Sprite;
import engine.SpriteSheet;
import game.GameConstant;
import game.Helper;

public class Sprites 
{
    public enum SpriteID
    {
        GREEN1_SQUARE,
        GREEN2_SQUARE,
        WHITE_SQUARE,
        PLANT_HOLE,
        GREEN1_CIRCLE,
        GREEN2_CIRCLE,
        GREEN3_CIRCLE,
        ONION,
        POTATO,
        REGION,
    }

    private SpriteSheet gameSheet;
    private File spriteFile;
    private Map<SpriteID, Sprite> spriteMap;
    // private GameSprite[] spriteMap;

    public Sprites()
    {
        spriteMap = new HashMap<SpriteID, Sprite>();
        
        gameSheet = new SpriteSheet(Helper.loadImage(GameConstant.GAME_SHEET_PATH));
        gameSheet.loadSprite(GameConstant.TILE_WIDTH, GameConstant.TILE_HEIGHT);
        try 
        {
            spriteFile = new File(GameConstant.SPRITES_PATH);
            Scanner scanner = new Scanner(spriteFile);

            while(scanner.hasNextLine())
            {
                //Read
                String line = scanner.nextLine();
                if (!line.startsWith("//"))
                {
                    String [] splitString = line.split("-");
                    // String spriteName = splitString[0];
                    int spriteX = Integer.parseInt(splitString[1]);
                    int spriteY = Integer.parseInt(splitString[2]);
                    SpriteID id = SpriteID.values()[Integer.parseInt(splitString[3])];
                    Sprite sprite = new Sprite(gameSheet, spriteX, spriteY, GameConstant.TILE_WIDTH, GameConstant.TILE_HEIGHT);
                    spriteMap.put(id, sprite);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        
        //load special sprite
        Sprite sprite = new Sprite(gameSheet, 
        0,
        4*GameConstant.TILE_HEIGHT,
        GameConstant.REGION_WIDTH_SIZE*GameConstant.TILE_WIDTH+4,
        GameConstant.REGION_HEIGHT_SIZE*GameConstant.TILE_HEIGHT+4);

        spriteMap.put(SpriteID.REGION, sprite);
    }

    private Sprite[] loadCustomSprites(int startX, int startY, int endX, int endY, int width, int height)
    {
        int size = ((endX - startX)/width)*((endY-startY)/height);
        Sprite[] cusSprites = new Sprite[size];
        for (int i = 0; i < size; i++)
            cusSprites[i] = new Sprite(gameSheet, startX, startY, width, height);
        
        return cusSprites;
    }

    public Sprite getSprite(SpriteID id)
    {
        return spriteMap.get(id);
    }

    public SpriteSheet getGameSpriteSheet() { return gameSheet; }
}
