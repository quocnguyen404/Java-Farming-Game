package engine;

import game.GameFrame;
import game.Helper;
import game.data.Sprites.SpriteID;

public class GUI implements HandleMouseEvent, GameObject 
{
    private SpriteID bgSpriteID = null;
    private GUIButton[] buttons;
    private Rectangle rect = new Rectangle();
    private boolean fixed;
    private boolean visible;

    public GUI()
    {
        this (null, 0, 0, true, false);
    }

    public GUI(SpriteID bgSpriteID, int x, int y, boolean fixed, boolean visible)
    {
        this.bgSpriteID = bgSpriteID;
        this.fixed = fixed;
        this.visible = visible;
        rect.setPosition(x, y);

        if (bgSpriteID != null)
        {
            rect.w = Helper.getSpriteWidth(bgSpriteID);
            rect.h = Helper.getSpriteHeight(bgSpriteID);
        }
    }
    
    public void setButtons(GUIButton[] buttons)
    {
        this.buttons = buttons;

        for (GUIButton btn : buttons) 
        {
            btn.rect.setPosition(btn.rect.x + rect.x, btn.rect.y + rect.y);
        }
    }

    public void setVisibility(boolean value)
    {
        visible = value;
    }

    public boolean getVisibility()
    {
        return visible;
    }

    public Rectangle getRect()
    {
        return rect;
    }
   
    @Override
    public boolean rightMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        return false;
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        if (!visible)
            return;
            
        if (bgSpriteID != null)
            renderer.renderSprite(bgSpriteID, rect.x, rect.y, xZoom, yZoom, fixed);
     
        if (buttons != null)
        {
            for (int i = 0; i < buttons.length; i++)
                buttons[i].render(renderer, xZoom, yZoom);
        }

    }

    @Override
    public void update(GameFrame game) 
    {
    }

    @Override
    public boolean leftMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        if (!fixed) 
            mouseRectangle = new Rectangle(mouseRectangle.x + camera.x , mouseRectangle.y + camera.y, 1, 1);

        if (rect.w == 0 || rect.h == 0 || mouseRectangle.intersects(rect))
        {
            // mouseRectangle.x += rect.x;
            // mouseRectangle.y += rect.y;
            for (GUIButton guiButton : buttons)
                if (guiButton.leftMouseClick(mouseRectangle, camera, xZoom, yZoom))
                    return true;
        }
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


}
