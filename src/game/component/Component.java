package game.component;

import engine.GUI;
import engine.GameObject;
import engine.HandleMouseClick;
import engine.Rectangle;
import engine.RenderHandler;
import game.GameFrame;

public abstract class Component implements HandleMouseClick, GameObject
{
    protected GUI buttons;

    abstract protected void generateUI();

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        if (buttons != null)
            buttons.render(renderer, xZoom, yZoom);    
    }

    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        if (buttons != null)
            return buttons.handleMouseClick(mouseRectangle, camera, xZoom, yZoom);
        else 
            return false;
    }

    @Override
    public void update(GameFrame game) 
    {
        
    }
}
