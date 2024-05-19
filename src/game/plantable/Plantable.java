package game.plantable;

import java.io.Serializable;

import engine.GameObject;
import engine.HandleMouseEvent;
import engine.Rectangle;
import game.data.PlantableData;

public abstract class Plantable implements HandleMouseEvent, GameObject, Serializable
{
    private static final long serialVersionUID = 7L;
    private PlantableData plantableData;
    
    public Plantable(PlantableData data)
    {
        this.plantableData = data;
    }

    public PlantableData getPlantableData()
    {
        return this.plantableData;
    }

    public abstract boolean planted(Rectangle rectangle);
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
    public boolean mouseHover(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        return false;
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
