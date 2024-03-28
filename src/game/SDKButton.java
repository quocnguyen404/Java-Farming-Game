package game;
import engine.GUIButton;
import engine.Rectangle;
import engine.RenderHandler;
import engine.Sprite;

public abstract class SDKButton extends GUIButton
{
    public SDKButton(Sprite tileSprite, Rectangle rect)
    {
        super(tileSprite, rect, true);
        rect.generateGraphics(0xFFDB3D);
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom, Rectangle interfaceRect) 
    {
        // Rectangle rectangle = new Rectangle(rect.x + interfaceRect.x, rect.y + interfaceRect.y, rect.w, rect.h);
        renderer.renderRectangle(rect, interfaceRect, 1, 1, fixed);
        renderer.renderSprite(sprite,
                              rect.x + interfaceRect.x + (xZoom - (xZoom-1))*rect.w/2/xZoom,
                              rect.y + interfaceRect.y + (yZoom - (yZoom-1))*rect.h/2/yZoom, 
                              xZoom-1,
                              yZoom-1, 
                              fixed);
    }
}
