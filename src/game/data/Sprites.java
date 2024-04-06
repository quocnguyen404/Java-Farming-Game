package game.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import engine.Sprite;
import engine.SpriteSheet;
import game.GameConstant;
import game.Helper;

public class Sprites 
{
    public enum SpriteID
    {
        GREEN1,
        GREEN2,
        WHITE,
        HOLE,
        CIR_GREEN1,
        CIR_GREEN2,
        CIR_GREEN3,
        REGION,
    }

    private SpriteSheet gameSheet;
    private File spriteFile;
    private GameSprite[] sprites;

    public Sprites()
    {
        gameSheet = new SpriteSheet(Helper.loadImage(GameConstant.GAME_SHEET_PATH));
        gameSheet.loadSprite(GameConstant.TILE_WIDTH, GameConstant.TILE_HEIGHT);
        sprites = new GameSprite[SpriteID.values().length];
        int id = 0;
        //load normal sprite from spritesheet
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
                    String spriteName = splitString[0];
                    int spriteX = Integer.parseInt(splitString[1]);
                    int spriteY = Integer.parseInt(splitString[2]);
                    GameSprite gameSprite = new GameSprite(gameSheet.getSprite(spriteX, spriteY), SpriteID.values()[id], spriteName);
                    sprites[id] = gameSprite;
                    id++;
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
        GameSprite regionSprite = new GameSprite(sprite, SpriteID.values()[id], "Region");
        sprites[id] = regionSprite;
        id++;
    }

    public Sprite getSprite(SpriteID id)
    {
        return sprites[id.ordinal()].sprite;
    }

    public SpriteSheet getGameSpriteSheet() { return gameSheet; }
    
    public class GameSprite
    {
        public Sprite sprite;
        public SpriteID id;
        public String name;
    
        public GameSprite(Sprite sprite, SpriteID id, String name)
        {
            this.sprite = sprite;
            this.id = id;
            this.name = name;
        }
    }
}
