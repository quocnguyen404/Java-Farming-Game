package game.ui;
import engine.GUIButton;
import engine.Rectangle;
import engine.RenderHandler;
import engine.Sprite;

public abstract class SDKButton extends GUIButton
{
    protected boolean genRect;
    public SDKButton(Sprite sprite, Rectangle rect, boolean genRect)
    {
        super(sprite, rect, true);
        this.genRect = genRect;
        if (genRect)
            rect.generateGraphics(0xFFDB3D);
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        if (genRect)
        {
            renderer.renderRectangle(rect, 1, 1, fixed);
        }
        else
            renderer.renderSprite(sprite, rect.x, rect.y, xZoom, yZoom, fixed);
    }
}
