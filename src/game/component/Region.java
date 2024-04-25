package game.component;

import java.util.function.Supplier;

import engine.HandleMouseEvent;
import engine.Rectangle;
import engine.RenderHandler;
import engine.Sprite;
import game.GameConstant;
import game.GameFrame;
import game.Helper;
import game.data.Sprites.SpriteID;
import game.plantable.Crop;
import game.plantable.Dirt;
import game.plantable.Plantable;

public class Region implements HandleMouseEvent
{
    //5x4s
    public static Supplier<Plantable> onGetPlantable;
    private Rectangle rect;
    private Sprite bgSprite;
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
        renderer.renderSprite(bgSprite, rect.x, rect.y, xZoom, yZoom, false);

        if (cells == null) return;

        for (RegionCell cell : cells)
            if (cell != null) cell.render(renderer, xZoom, yZoom);
    }

    public void update(GameFrame game)
    {
        for (RegionCell cell : cells)
            if (cell != null) cell.update(game);
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
                Plantable plantable = onGetPlantable.get();
                if (plantable == null) return false;
                if (plantable instanceof Dirt)
                {
                    if(cell.isDigged()) return false;
                    cell.dig((Dirt) plantable);
                    return true;
                }
                else if (cell.isDigged() && cell.isEmpty())
                {
                    Crop crop = (Crop) plantable;
                    crop.setPosition(cell.rect);
                    cell.planted(crop);
                    return true;
                }
 
            }
        }
        return false;
    }

    /**
     * RegionCell
     */
    public class RegionCell 
    {
        private Rectangle rect;
        private Crop crop;
        private Dirt dirt;

        public RegionCell(Rectangle rect)
        {
            this.rect = rect;
            crop = null;
            dirt = null;
        }

        public boolean isEmpty()
        {
            return crop == null;
        }

        public boolean isDigged()
        {
            return dirt != null;
        }

        public void dig(Dirt dirt)
        {
            this.dirt = dirt;
            this.dirt.setRectangle(rect);
        }

        public void planted(Crop crop)
        {
            this.crop = crop;
        }

        public boolean onHoverCell(Rectangle otherRect)
        {
            return rect.intersects(otherRect);
        }

        public void update(GameFrame game)
        {
            crop.update(game);
        }

        public void render(RenderHandler renderer, int xZoom, int yZoom)
        {
            if (rect.isGen()) renderer.renderRectangle(rect, 1, 1, false);
            if(isDigged()) dirt.render(renderer, xZoom, yZoom);
            else return;
            if (crop != null) crop.render(renderer, xZoom, yZoom);
        }
    }

    @Override
    public boolean mouseMoved(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }
}
