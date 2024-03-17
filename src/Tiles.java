import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Tiles 
{
    public enum TileID
    {
        SAND1, // 0
        SAND2,
        FLATSTONE, //2
        DIRT1,
        NONE,
    }
    public static final int TILE_WIDTH = 16;
    public static final int TILE_HEIGHT = 16;

    private SpriteSheet spriteSheet;
    private ArrayList<Tile> tilesList;

    public Tiles(File tilesFile, SpriteSheet sheet)
    {
        tilesList = new ArrayList<Tile>();
        this.spriteSheet = sheet;
        
        try 
        {
            Scanner scanner = new Scanner(tilesFile);
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
                    Tile tile = new Tile(tileName, spriteSheet.getSprite(spriteX, spriteY));
                    tilesList.add(tile);
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
        if (tileID.ordinal() >= 0 && tilesList.size() > tileID.ordinal())
            renderer.renderSprite(tilesList.get(tileID.ordinal()).sprite, xPosition, yPosition, xZoom, yZoom);
        else System.out.println("Invalid tile ID");
    }
    
    class Tile
    {
        public String tileName;
        public Sprite sprite;

        public Tile(String tileName, Sprite tileSprite)
        {
            this.tileName = tileName;
            this.sprite = tileSprite;
        }
    }
}
