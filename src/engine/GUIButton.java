package engine;

import game.data.Sprites.SpriteID;

public abstract class GUIButton implements HandleMouseEvent
{   
    // protected Sprite sprite;
    protected SpriteID spriteID;
    protected Rectangle rect;
    protected boolean fixed;

    public GUIButton(SpriteID spriteID, Rectangle rect, boolean fixed)
    {
        this.spriteID = spriteID;
        this.rect = rect;
        this.fixed = fixed;
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
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

    abstract public void activate();
}