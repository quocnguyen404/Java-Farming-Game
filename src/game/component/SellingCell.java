package game.component;

import java.util.function.Supplier;

import engine.GameObject;
import engine.HandleMouseEvent;
import engine.Rectangle;
import engine.RenderHandler;
import game.GameFrame;
import game.data.Sprites.SpriteID;

public class SellingCell implements GameObject, HandleMouseEvent
{
    public static Supplier<Boolean> onGetSellingModify;
    public static Runnable onSellingCrop;
    private SpriteID spriteID;
    private Rectangle rect;

    public SellingCell(Rectangle rect)
    {
        this.rect = rect;
        rect.generateGraphics(0xFFFFFF);
        this.spriteID = SpriteID.SHOPPING_CART;
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        if (rect.isGen()) renderer.renderRectangle(rect, 1, 1, true);
        if(spriteID != null) renderer.renderSprite(spriteID, rect.x, rect.y, xZoom, yZoom, true);
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