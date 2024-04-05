package game.data;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import engine.RenderHandler;
import engine.Sprite;
import engine.SpriteSheet;
import game.GameConstant;
import game.Helper;

public class Tiles 
{
    public enum TileID
    {
        GREEN1, //0
        GREEN2, //1
        WHITE, //2
        HOLE, //3
        CIR_GREEN1, //4
        CIR_GREEN2, //5
        CIR_GREEN3, //6
        //
        REGION, //
        NONE,
    }
    private ArrayList<Tile> tilesList;

    public Tiles()
    {
        File tilesFile = new File(GameConstant.TILES_PATH);
        tilesList = new ArrayList<Tile>(TileID.values().length);
        SpriteSheet spriteSheet = new SpriteSheet(Helper.loadImage(GameConstant.GAME_SHEET_PATH));
        spriteSheet.loadSprite(GameConstant.TILE_WIDTH, GameConstant.TILE_HEIGHT);
        
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

        //special sprite
        //rect_white //7
        Sprite sprite = new Sprite(spriteSheet, 0,
        4*GameConstant.TILE_HEIGHT,
        GameConstant.REGION_WIDTH_SIZE*GameConstant.TILE_WIDTH+4,
        GameConstant.REGION_HEIGHT_SIZE*GameConstant.TILE_HEIGHT+4);
        Tile rectWhite = new Tile("rectWhite", sprite, TileID.REGION);
        tilesList.add(rectWhite);
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

    public Tile getTile(TileID tileID)
    {
        return tilesList.get(tileID.ordinal());
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
