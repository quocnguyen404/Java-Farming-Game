package game.component;

import engine.GUI;
import engine.GameObject;
import engine.HandleMouseEvent;
import engine.Rectangle;
import engine.RenderHandler;
import game.GameFrame;
import game.ui.ButtonAct;

public abstract class Component implements HandleMouseEvent, GameObject
{
    protected GUI buttons;
    protected ButtonAct turnOnButton;

    abstract protected void generateUI();

    protected void setButtonsVisibility()
    {
        buttons.setVisibility(!buttons.getVisibility());
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        if (buttons != null)
            buttons.render(renderer, xZoom, yZoom);    
    }

    @Override
    public boolean leftMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom)
    {
        boolean clicked = false;

        if (turnOnButton != null)
            clicked =  turnOnButton.leftMouseClick(mouseRectangle, camera, xZoom, yZoom);

        if (buttons != null && buttons.getVisibility() && !clicked)
            clicked = buttons.leftMouseClick(mouseRectangle, camera, xZoom, yZoom);

        return clicked;
    }

    @Override
    public boolean rightMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        return false;
    }

    @Override
    public boolean mouseDragged(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        return false;
    }

    @Override
    public boolean mouseDraggedExit(Rectangle mousRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        return false;
    }


    @Override
    public void update(GameFrame game) 
    {
        
    }
}
