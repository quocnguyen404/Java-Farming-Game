package game;
import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import engine.HandleMouseClick;
import engine.Rectangle;
import engine.RenderHandler;
import game.data.ConfigDataHelper;
import game.data.Tiles;
import game.data.Tiles.TileID;

public class Map implements HandleMouseClick
{
    public enum EditMode
    {
        NONE,
        PLACING,
        REMOVING,
        SELECTING,
    }

    //#region Field
    private ArrayList<MappedTile> mappedTiles;
    private HashMap<Integer,String> comment;
    private TileID fillTiledID = TileID.NONE;
    private File mapFile;
    private Tiles tileSet;
    //#endregion

    //#endregion Edit
    private EditMode editMode = EditMode.NONE;
    private TileID editTileID = TileID.NONE;
    //#endregion
    
    public Map()
    {
        mapFile = new File(GameConstanst.MAP_PATH);
        mappedTiles = new ArrayList<MappedTile>();
        tileSet = ConfigDataHelper.getInstance().getTiles();
        comment = new HashMap<Integer, String>();
        
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
        int tileWidth = GameConstanst.TILE_WIDTH * xZoom;
        int tileHeight = GameConstanst.TILE_HEIGHT * yZoom;
    
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

    public void setTile(int tileX, int tileY, TileID id)
    {
        boolean found = false;

        if (id == fillTiledID)
        {
            removeTile(tileX, tileY);
            return;
        }

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

    public void removeTile(int tileX, int tileY)
    {
        for (int i = 0; i < mappedTiles.size(); i++)
        {
            MappedTile mappedTile = mappedTiles.get(i);
            if (mappedTile.x == tileX && mappedTile.y == tileY)
            {
                mappedTiles.remove(mappedTile);
                break;
            }
        }
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
    
    public void setEditMode(EditMode mode)
    {
        editMode = mode;
        System.out.println("On edit map mode to: " + mode);
    }

    public void setEditTileID(TileID tileID)
    {
        editMode = EditMode.PLACING;
        editTileID = tileID;
        System.out.println("On change edit tile ID to: " + tileID);
    }

    @Override
    public boolean leftMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        if (editMode == EditMode.NONE && editTileID == TileID.NONE)
            return false;
        
        //happen when enable to edit map, but have pick button yet
        // if (editTileID == TileID.NONE)
        //     editMode = EditMode.REMOVING;
        
        int x = Helper.handleMousePosition(mouseRectangle.x, camera.x, GameConstanst.TILE_WIDTH*GameFrame.X_ZOOM);
        int y = Helper.handleMousePosition(mouseRectangle.y, camera.y, GameConstanst.TILE_HEIGHT*GameFrame.Y_ZOOM);

        switch (editMode) {
            case PLACING:
            {
                setTile(x, y, editTileID);
                break;
            }
            case REMOVING:
            {
                removeTile(x, y);
                break;
            }
            case SELECTING:
            {
                break;
            }

            default:
                break;
        }

        return true;
    }

    @Override
    public boolean rightMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        return false;
    }

    /**
     * MappedTile
     */
    public class MappedTile 
    {
        public TileID id;
        public int x, y;
        public MappedTile(Tiles.TileID id, int x, int y)
        {
            this.id = id;
            this.x = x;
            this.y = y;
        }
    }


}
