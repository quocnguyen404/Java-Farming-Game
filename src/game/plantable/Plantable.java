package game.plantable;

import engine.GameObject;
import engine.HandleMouseEvent;
import engine.Rectangle;
import game.data.PlantableData;

public abstract class Plantable implements HandleMouseEvent, GameObject
{
    private PlantableData plantableData;
    
    public Plantable(PlantableData data)
    {
        this.plantableData = data;
    }

    public PlantableData getPlantableData()
    {
        return this.plantableData;
    }

    public abstract boolean planted();
    public abstract boolean remove();

    @Override
    public boolean leftMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean rightMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void mouseHover(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
    }

    @Override
    public boolean mouseDragged(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        // TODO Auto-generated method stub
        return false;
    }
    @Override
    public boolean mouseDraggedExit(Rectangle mousRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        // TODO Auto-generated method stub
        return false;
    }

}
