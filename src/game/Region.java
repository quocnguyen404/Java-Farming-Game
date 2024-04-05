package game;

import engine.HandleMouseClick;
import engine.Rectangle;
import engine.RenderHandler;
import engine.Sprite;
import game.Map.MappedTile;
import game.data.ConfigDataHelper;
import game.data.Tiles;
import game.data.Tiles.TileID;

public class Region implements HandleMouseClick
{
    //5x4
    private Rectangle rect;
    private Sprite bgSprite;
    private MappedTile[] mappedTiles;
    private Tiles tileSet = ConfigDataHelper.getInstance().getTiles();

    public Region(int x, int y)
    {
        bgSprite = tileSet.getTile(TileID.REGION).sprite;
        rect = new Rectangle();
        rect.w = bgSprite.getWidth()*GameFrame.X_ZOOM;
        rect.h = bgSprite.getHeight()*GameFrame.Y_ZOOM;
        rect.x = x - (rect.w/(2*GameConstant.TILE_WIDTH*GameFrame.X_ZOOM));
        rect.y = y - (rect.h/(2*GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM));
        mappedTiles = new MappedTile[GameConstant.REGION_WIDTH_SIZE*GameConstant.REGION_HEIGHT_SIZE];
    }

    int tileWidth;
    int tileHeight;
    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        if (mappedTiles == null) return;

        tileWidth = GameConstant.TILE_WIDTH * xZoom;
        tileHeight = GameConstant.TILE_HEIGHT * yZoom;

        renderer.renderSprite(bgSprite, rect.x*tileWidth, rect.y*tileHeight, xZoom, yZoom, false);

        for (MappedTile tile : mappedTiles) 
            if (tile != null) 
                renderer.renderSprite(tileSet.getTile(tile.id).sprite, (rect.x+tile.x)*tileWidth, (rect.y+tile.y)*tileHeight, xZoom, yZoom, false);    
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
}
