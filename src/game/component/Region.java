package game.component;

import java.io.Serializable;

import engine.HandleMouseEvent;
import engine.Rectangle;
import engine.RenderHandler;
import engine.Sprite;
import game.GameConstant;
import game.GameFrame;
import game.Helper;
import game.data.Sprites.SpriteID;
import game.plantable.Dirt;
import game.plantable.Plantable;
import game.plantable.crop.Crop;
import game.plantable.item.Item;

public class Region implements HandleMouseEvent, Serializable
{
    //5x4s
    private static final long serialVersionUID = 1L;
    private transient Sprite bgSprite;

    private Rectangle rect;
    private RegionCell[] cells = null;

    public Region(int x, int y)
    {
        bgSprite = Helper.getSprite(SpriteID.REGION);
        rect = new Rectangle();
        rect.w = bgSprite.getWidth()*GameFrame.X_ZOOM;
        rect.h = bgSprite.getHeight()*GameFrame.Y_ZOOM;
        rect.x = (x - (rect.w/(2*GameConstant.TILE_WIDTH*GameFrame.X_ZOOM)))*GameConstant.TILE_WIDTH*GameFrame.X_ZOOM;
        rect.y = (y - (rect.h/(2*GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM)))*GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM;
        cells = new RegionCell[GameConstant.REGION_WIDTH_SIZE*GameConstant.REGION_HEIGHT_SIZE];
        
        for(int i = 0; i < GameConstant.REGION_WIDTH_SIZE; i++)
        {
            for(int j = 0; j < GameConstant.REGION_HEIGHT_SIZE; j++)
            {
                int w = GameConstant.TILE_WIDTH*GameFrame.X_ZOOM;
                int h = GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM;
                int xPos = rect.x + i*w+GameConstant.REGION_OFFSET;
                int yPos = rect.y + j*h+GameConstant.REGION_OFFSET;
                Rectangle rect = new Rectangle(xPos, yPos, w, h);
                // rect.generateGraphics(1, 0xFFFFF);
                int index = i*GameConstant.REGION_HEIGHT_SIZE+j;
                cells[index] = new RegionCell(rect);
            }
        }
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        renderer.renderSprite(getSprite(), rect.x, rect.y, xZoom, yZoom, false);

        if (cells == null) return;

        for (RegionCell cell : cells)
            if (cell != null) cell.render(renderer, xZoom, yZoom);
    }

    private Sprite getSprite()
    {
        if (bgSprite == null)
            bgSprite = Helper.getSprite(SpriteID.REGION);
        return bgSprite;
    }

    public Rectangle getRect()
    {
        return rect;
    }

    public RegionCell[] getRegionCells()
    {
        return cells;
    }

    public void update(GameFrame game)
    {
        for (RegionCell cell : cells)
            if (cell != null) cell.update(game);
    }

    public void loadCropEvent()
    {
        for (RegionCell regionCell : cells) {
            if(!regionCell.isEmpty())
            {
                regionCell.crop.onCropGrow = regionCell::cropGrow;
                regionCell.crop.onWatering = regionCell.dirt::watering;
            }
        }
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
        for (RegionCell cell : cells)
        {
            if (cell.onHoverCell(mousRectangle))
            {
                Plantable plantable = FarmingSystem.onGetPlantable();
                if (plantable == null) return false;
                if (plantable instanceof Dirt)
                {
                    if(cell.isDigged()) return false;
                    cell.dig((Dirt) plantable);
                    return true;
                }
                else if (plantable instanceof Crop)
                {
                    if (!cell.isDigged() || !cell.isEmpty()) return false;
                    Crop crop = (Crop) plantable;
                    crop.onCropGrow = cell::cropGrow;
                    crop.setPosition(cell.rect);
                    cell.planted(crop);
                    crop.onWatering = cell.dirt::watering;
                    return true;
                }
                if (plantable instanceof Item)
                {
                    if (!cell.isEmpty())
                    {
                        Item item = (Item)plantable;
                        item.activate(cell.crop);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    @Override
    public boolean mouseHover(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        return false;
    }
}
