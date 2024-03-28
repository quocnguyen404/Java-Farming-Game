package engine;

public abstract class GUIButton implements HandleMouseClick
{   
    protected Sprite sprite;
    protected Rectangle rect;
    protected boolean fixed;

    public GUIButton(Sprite sprite, Rectangle rect, boolean fixed)
    {
        this.sprite = sprite;
        this.rect = rect;
        this.fixed = fixed;
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom, Rectangle interfaceRect)
    {
        renderer.renderSprite(sprite, rect.x + interfaceRect.x, rect.y + interfaceRect.y, xZoom, yZoom, fixed);
    }

    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        if (rect.intersects(mouseRectangle))
        {
            activate();
            return true;
        }
        return false;
    }

    abstract public void activate();
}