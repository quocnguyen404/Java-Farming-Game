package game.plantable.item;

import engine.RenderHandler;
import game.GameFrame;
import game.data.ItemData;
import game.plantable.Plantable;

public abstract class Item extends Plantable
{
    public Item(ItemData data)
    {  
        super(data);
    }

    public abstract void activate();

    @Override
    public boolean planted() 
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
