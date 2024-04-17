package game.ui;
import engine.GUIButton;
import engine.Rectangle;
import engine.RenderHandler;
import game.data.Sprites.SpriteID;

public abstract class SDKButton extends GUIButton
{
    protected boolean genRect = false;
    public SDKButton(SpriteID spriteID, Rectangle rect, boolean genRect)
    {
        super(spriteID, rect, true);
        this.genRect = genRect;
        if (genRect)
            rect.generateGraphics(0xFFDB3D);
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        if (genRect)
            renderer.renderRectangle(rect, 1, 1, fixed);
        renderer.renderSprite(spriteID, rect.x, rect.y, xZoom, yZoom, fixed);
    }
}
