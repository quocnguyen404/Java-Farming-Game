package game.data;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import engine.RenderHandler;
import engine.Sprite;
import engine.SpriteSheet;
import game.GameConstanst;
import game.Helper;

public class Tiles 
{
    public enum TileID
    {
        SAND1, // 0
        SAND2, //1
        FLATSTONE, //2
        DIRT1, //3
        WALL1, //4
        NONE,
    }
    private ArrayList<Tile> tilesList;

    public Tiles()
    {
        File tilesFile = new File(GameConstanst.TILES_PATH);
        tilesList = new ArrayList<Tile>();
        SpriteSheet spriteSheet = new SpriteSheet(Helper.loadImage(GameConstanst.GAME_SHEET_PATH));
        spriteSheet.loadSprite(GameConstanst.TILE_WIDTH, GameConstanst.TILE_HEIGHT);
        
        try 
        {
            Scanner scanner = new Scanner(tilesFile);
            int id = 0;
            while(scanner.hasNextLine())
            {
                //Read
                String line = scanner.nextLine();
                if (!line.startsWith("//"))
                {
                    String [] splitString = line.split("-");
                    String tileName = splitString[0];
                    int spriteX = Integer.parseInt(splitString[1]);
                    int spriteY = Integer.parseInt(splitString[2]);
                    Tile tile = new Tile(tileName, spriteSheet.getSprite(spriteX, spriteY), TileID.values()[id]);
                    tilesList.add(tile);
                    id++;
                }
            }
            scanner.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }   

    public void renderTile(TileID tileID, RenderHandler renderer, int xPosition, int yPosition, int xZoom, int yZoom)
    {
        if (tileID != TileID.NONE && tilesList.size() > tileID.ordinal())
            renderer.renderSprite(tilesList.get(tileID.ordinal()).sprite, xPosition, yPosition, xZoom, yZoom, false);
        else System.out.println("Invalid tile ID");
    }

    public int getSize()
    {
        return tilesList.size();
    }

    public ArrayList<Tile> getTileList()
    {
        return this.tilesList;
    }

    // public Sprite[] getSprites()
    // {
    //     Sprite[] sprites = new Sprite[getSize()];

    //     for (int i = 0; i < sprites.length; i++)
    //         sprites[i] = tilesList.get(i).sprite;
    //     return sprites;
    // }
    
    public class Tile
    {
        public TileID tileID;
        public String tileName;
        public Sprite sprite;

        public Tile(String tileName, Sprite tileSprite, TileID tileID)
        {
            this.tileName = tileName;
            this.sprite = tileSprite;
            this.tileID = tileID;
        }
    }
}
