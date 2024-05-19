package game.component;

import java.io.Serializable;

import engine.Rectangle;
import engine.RenderHandler;
import game.GameFrame;
import game.plantable.Dirt;
import game.plantable.crop.Crop;

public class RegionCell implements Serializable
{
    private static final long serialVersionUID = 4L;
    public Rectangle rect;
    public Crop crop;
    public Dirt dirt;
    private int counter = 0;

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

    public void cropGrow(Crop crop)
    {
        GameFrame.onGetGrowPlant.accept(crop);
        crop.growRipe(new Rectangle(rect.x, rect.y, rect.w, rect.h));
        this.crop = null;
        this.dirt.reset();
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
        if (crop != null) crop.update(game);
        if (dirt != null) 
        {
            dirt.update(game);
            if(isEmpty()) counter++;
            if(isEmpty() && counter >= 60*60)
            {
                dirt = null;
                counter = 0;
            }
        }
    }

    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        if (rect.isGen()) renderer.renderRectangle(rect, 1, 1, false);
        if(isDigged()) dirt.render(renderer, xZoom, yZoom);
        else return;
        if (crop != null) crop.render(renderer, xZoom, yZoom);
    }
}

