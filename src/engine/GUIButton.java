package engine;

import game.data.Sprites.SpriteID;

public abstract class GUIButton implements HandleMouseEvent
{   
    protected SpriteID spriteID;
    protected Rectangle rect;
    protected boolean fixed;
    protected boolean genRect = false;


    public GUIButton(SpriteID spriteID, Rectangle rect, boolean fixed, boolean genRect)
    {
        this.spriteID = spriteID;
        this.rect = rect;
        this.fixed = fixed;
        this.genRect = genRect;
        if (genRect)
            rect.generateGraphics(2, 0x00000);
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        if (genRect)
            renderer.renderRectangle(rect, 1, 1, fixed);
        renderer.renderSprite(spriteID, rect.x, rect.y, xZoom, yZoom, fixed);
    }

    @Override
    public boolean leftMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        if (rect.intersects(mouseRectangle))
        {
            activate();
            return true;
        }
        return false;
    }

    @Override
    public boolean rightMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        return false;
    }


    //handle mouse hover
    private boolean isHover = false;
    @Override
    public boolean mouseHover(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        if (rect == null) return false;
        if (rect.intersects(mouseRectangle))
        {
            if (!isHover)
            {
                hover();
                isHover = true;
            }
        }
        else if(isHover)
        {
            existHover();
            isHover = false;
        }

        return isHover;
    }
    
    @Override
    public boolean mouseDragged(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        // if (rect.intersects(mouseRectangle)/*  && !isDrag*/)
        // {
        //     isDrag = true;
        //     // dragActivate();
        //     return true;
        // }
        return false;
    }

    @Override
    public boolean mouseDraggedExit(Rectangle mousRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        return false;
    }
    
    abstract public void activate();
    abstract public void hover();
    abstract public void existHover();
}