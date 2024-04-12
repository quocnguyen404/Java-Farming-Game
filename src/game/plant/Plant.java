package game.plant;

import engine.RenderHandler;
import game.GameFrame;
import game.data.PlantData;
import game.data.Sprites.SpriteID;

public class Plant extends Plantable
{
    private int counter = 0;
    private PlantData plantData;

    public Plant()
    {

    }

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

    private void grow()
    {

    }

    @Override
    public void update(GameFrame game) 
    {
        if (counter >= plantData.getGrowTime())
        {
            counter = 0;
            grow();
        }

        counter++;
    }
}
