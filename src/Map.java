import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Map 
{
    private Tiles tileSet;
    private Tiles.TileID fillTiledID = Tiles.TileID.NONE;
    private ArrayList<MappedTile> mappedTiles;
    private HashMap<Integer,String> comment = new HashMap<Integer,String>();
    private File mapFile;

    public Map(File mapFile, Tiles tileSet)
    {
        this.mapFile = mapFile;
        mappedTiles = new ArrayList<MappedTile>();
        this.tileSet = tileSet;
        
        try 
        {
            Scanner scanner = new Scanner(mapFile);
            int lineIndex = 0;
            while(scanner.hasNextLine())
            {
                //Read
                String line = scanner.nextLine();
                if (!line.startsWith("//"))
                {
                    if (line.contains(":"))
                    {
                        String[] splitString = line.split(":");
                        if (splitString[0].equalsIgnoreCase("Fill"))
                        {
                            fillTiledID = Tiles.TileID.values()[Integer.parseInt(splitString[1])];
                            continue;
                        }
                    }
                    
                    String [] splitString = line.split(",");
                    if (splitString.length >= 3)
                    {
                        MappedTile mappedTile = new MappedTile(Tiles.TileID.values()[Integer.parseInt(splitString[0])],
                                                               Integer.parseInt(splitString[1]),
                                                               Integer.parseInt(splitString[2]));
                        
                        mappedTiles.add(mappedTile);
                    }
                }
                else comment.put(lineIndex, line);

                lineIndex++;
            }
            scanner.close();
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        int tileWidth = Tiles.TILE_WIDTH * xZoom;
        int tileHeight = Tiles.TILE_HEIGHT * yZoom;
    
        if (fillTiledID != Tiles.TileID.NONE)
        {
            Rectangle camera = renderer.getCamera();
            for (int y = camera.y - tileHeight - (camera.y % tileHeight); y < camera.h + camera.y; y+= tileHeight)
                for (int x = camera.x - tileWidth - (camera.x % tileWidth); x < camera.w + camera.x; x+= tileWidth)
                    tileSet.renderTile(fillTiledID, renderer, x, y, xZoom, yZoom);
        }

        for (int tileIndex = 0; tileIndex < mappedTiles.size(); tileIndex++)
        {
            MappedTile mappedTile = mappedTiles.get(tileIndex);
            tileSet.renderTile(mappedTile.id, renderer, mappedTile.x * tileWidth, mappedTile.y * tileHeight, xZoom, yZoom);
        }
    }

    public void setTile(int tileX, int tileY, Tiles.TileID id)
    {
        boolean found = false;
        for (int i = 0; i < mappedTiles.size(); i++)
        {
            MappedTile mappedTile = mappedTiles.get(i);
            if (mappedTile.x == tileX && mappedTile.y == tileY)
            {
                mappedTile.id = id;
                found = true;
                break;
            }
        }

        if (!found)
            mappedTiles.add(new MappedTile(id, tileX, tileY));
    }
    
    public void saveMap()
    {
        try
        {
            if (mapFile.exists())
                mapFile.delete();
            int currentLine = 0;
            PrintWriter printWriter = new PrintWriter(mapFile);
            if (comment.containsKey(currentLine))
                    printWriter.println(comment.get(currentLine));
            printWriter.println("Fill:"+fillTiledID.ordinal());
            currentLine++;
            //print all map
            for (int i = 0; i < mappedTiles.size(); i++)
            {
                if (comment.containsKey(currentLine))
                    printWriter.println(comment.get(currentLine));

                MappedTile mappedTile = mappedTiles.get(i);
                printWriter.printf("%d,%d,%d%n", mappedTile.id.ordinal(), mappedTile.x, mappedTile.y);
                currentLine++;
            }

            printWriter.close();

            mapFile.createNewFile();
            System.out.println("Save map success: " + mapFile.getName());
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * MappedTile
     */
    public class MappedTile 
    {
        public Tiles.TileID id;
        public int x, y;
        public MappedTile(Tiles.TileID id, int x, int y)
        {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }
}
