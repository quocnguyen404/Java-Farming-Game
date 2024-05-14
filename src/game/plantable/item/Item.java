package game.plantable.item;

import engine.Rectangle;
import engine.RenderHandler;
import game.GameFrame;
import game.data.ItemData;
import game.plantable.Plantable;
import game.plantable.crop.Crop;

public abstract class Item extends Plantable
{
    public Item(ItemData data)
    {  
        super(data);
    }

    public abstract void activate(Crop crop);

    @Override
    public boolean planted(Rectangle rectangle) 
    {
        return false;
    }

    @Override
    public boolean remove() 
    {
        return false;
    }

    @Override
    public void update(GameFrame game) 
    {
        
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        
    }
}
