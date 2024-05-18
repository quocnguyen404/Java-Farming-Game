package game.component;

import java.util.function.Supplier;

import engine.GameObject;
import engine.HandleMouseEvent;
import engine.Rectangle;
import engine.RenderHandler;
import game.GameFrame;

public class SellingCell implements GameObject, HandleMouseEvent
{
    public static Supplier<Boolean> onGetSellingModify;
    public static Runnable onSellingCrop;
    private Rectangle rect;

    public SellingCell(Rectangle rect)
    {
        this.rect = rect;
        this.rect.generateGraphics(0xFFFFFF);
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        if (rect.isGen()) renderer.renderRectangle(rect, xZoom, yZoom, true);
    }

    @Override
    public void update(GameFrame game) {}
    
    @Override
    public boolean mouseDraggedExit(Rectangle mousRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        //TODO implement selling cell mouse dragged exit
        boolean isSelling = onGetSellingModify.get();
        boolean isIntersect = rect.intersects(mousRectangle);
        if(isIntersect && isSelling) 
        {
            onSellingCrop.run();
            return true;
        }

        return false;
    }

    @Override
    public boolean leftMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }

    @Override
    public boolean rightMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }

    @Override
    public boolean mouseHover(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        return false;
    }

    @Override
    public boolean mouseDragged(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) { return false; }
}