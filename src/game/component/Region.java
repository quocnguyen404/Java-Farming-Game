package game.component;

import engine.HandleMouseEvent;
import engine.Rectangle;
import engine.RenderHandler;
import engine.Sprite;
import game.GameConstant;
import game.GameFrame;
import game.data.ConfigDataHelper;
import game.data.Sprites.SpriteID;
import game.plant.Plantable;

public class Region implements HandleMouseEvent
{
    //5x4
    private Rectangle rect;
    private Sprite bgSprite;
    private Plantable[] plantables = null;

    public Region(int x, int y)
    {
        bgSprite = ConfigDataHelper.getInstance().getSprite(SpriteID.REGION);
        rect = new Rectangle();
        rect.w = bgSprite.getWidth()*GameFrame.X_ZOOM;
        rect.h = bgSprite.getHeight()*GameFrame.Y_ZOOM;
        rect.x = x - (rect.w/(2*GameConstant.TILE_WIDTH*GameFrame.X_ZOOM));
        rect.y = y - (rect.h/(2*GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM));
        plantables = new Plantable[GameConstant.REGION_WIDTH_SIZE*GameConstant.REGION_HEIGHT_SIZE];
    }

    private int tileWidth;
    private int tileHeight;
    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        renderer.renderSprite(bgSprite, rect.x*tileWidth, rect.y*tileHeight, xZoom, yZoom, false);

        if (plantables == null) return;
        tileWidth = GameConstant.TILE_WIDTH * xZoom;
        tileHeight = GameConstant.TILE_HEIGHT * yZoom;

        for (Plantable plantable : plantables) 
            if (plantable != null) plantable.render(renderer, xZoom, yZoom);
    }

    public void update(GameFrame game)
    {
        for (Plantable plantable : plantables)
            if (plantable != null) plantable.update(game);
    }

    @Override
    public boolean leftMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {

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
}
