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
        GREEN1_SQUARE, //0
        GREEN2_SQUARE, //1
        WHITE_SQUARE,  //2
        PLANT_HOLE,    //3
        GREEN1_CIRCLE, //4
        GREEN2_CIRCLE, //5
        GREEN3_CIRCLE, //6
        GOLD,          //7
        WATER_DROP,    //8
        POTATO,        //9
        ONION,         //10
        REGION,        //
    }

    public enum AnimationID
    {
        POTATO,
        ONION,
        DIRT,
    }

    private SpriteSheet gameSheet;
    private File spriteFile;
    private Map<SpriteID, Sprite> spriteMap;
    private Map<AnimationID, Sprite[]> animatedSpriteMap;

    public Sprites()
    {
        spriteMap = new HashMap<SpriteID, Sprite>();
        animatedSpriteMap = new HashMap<AnimationID, Sprite[]>();
        
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
                    Sprite sprite = new Sprite(gameSheet, spriteX*GameConstant.TILE_WIDTH, spriteY*GameConstant.TILE_HEIGHT, GameConstant.TILE_WIDTH, GameConstant.TILE_HEIGHT);
                    spriteMap.put(id, sprite);
                }
            }

            scanner.close();
        } catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        
        //load special sprite
        //region
        Sprite sprite = new Sprite(gameSheet, 
        0,
        4*GameConstant.TILE_HEIGHT,
        GameConstant.REGION_WIDTH_SIZE*GameConstant.TILE_WIDTH+4,
        GameConstant.REGION_HEIGHT_SIZE*GameConstant.TILE_HEIGHT+4);

        spriteMap.put(SpriteID.REGION, sprite);

        //load animated
        int x = 1, y = 8;
        for (AnimationID id : AnimationID.values())
        {
            Sprite[] animated = loadCustomSprites(x*GameConstant.TILE_WIDTH,
                                                  y*GameConstant.TILE_HEIGHT,
                                                  3,
                                                  GameConstant.TILE_WIDTH, 
                                                  GameConstant.TILE_HEIGHT);
            animatedSpriteMap.put(id, animated);
            y++;
        }
    }

    private Sprite[] loadCustomSprites(int startX, int startY, int size, int width, int height)
    {
        Sprite[] cusSprites = new Sprite[size];
        for (int i = 0; i < size; i++)
            cusSprites[i] = new Sprite(gameSheet, startX+i*width, startY+height, width, height);
        
        return cusSprites;
    }

    public Sprite getSprite(SpriteID id)
    {
        return spriteMap.get(id);
    }

    public Sprite[] getAnimatedSprite(AnimationID id)
    {
        return animatedSpriteMap.get(id);
    }

    public SpriteSheet getGameSpriteSheet() { return gameSheet; }
}
