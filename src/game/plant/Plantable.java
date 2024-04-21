package game.plant;

import engine.GameObject;
import engine.HandleMouseEvent;
import engine.Rectangle;
import engine.RenderHandler;
import game.GameFrame;

public abstract class Plantable implements HandleMouseEvent, GameObject
{
    public abstract boolean planted();
    public abstract boolean remove();

    @Override
    public void update(GameFrame game) 
    {
        
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        
    }

    @Override
    public boolean leftMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        // TODO Auto-generated method stub
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
    @Override
    public boolean rightMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        // TODO Auto-generated method stub
        return false;
    }
}
