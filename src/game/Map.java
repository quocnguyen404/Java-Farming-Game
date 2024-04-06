package game;
import java.io.File;
import java.util.HashMap;

import engine.HandleMouseClick;
import engine.Rectangle;
import engine.RenderHandler;

public class Map implements HandleMouseClick
{
    private Region[] regions;
    private HashMap<Integer,String> comment;
    private File mapFile;

    
    public Map()
    {
        regions = new Region[1];
        int xPos = (GameConstant.WIN_HEIGHT/(2*GameConstant.TILE_WIDTH*GameFrame.X_ZOOM));
        int yPos = (GameConstant.WIN_HEIGHT/(2*GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM));
        regions[0] = new Region(xPos, yPos);
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        
        for (Region region : regions) {
            region.render(renderer, xZoom, yZoom);
        }
    }


    @Override
    public boolean leftMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        return false;
    }

    @Override
    public boolean rightMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        return false;
    }
}
