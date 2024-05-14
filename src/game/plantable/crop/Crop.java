package game.plantable.crop;

import engine.AnimatedSprite;
import engine.Rectangle;
import engine.RenderHandler;
import engine.Sprite;
import game.GameFrame;
import game.Helper;
import game.data.CropData;
import game.data.Sprites.AnimationID;
import game.plantable.Plantable;

public abstract class Crop extends Plantable
{
    private Rectangle rect;
    private AnimatedSprite anim;
    private int counter = 0;
    private int waterLeft;

    public Crop(CropData plant)
    {
        super(plant);
        Sprite[] sprites = Helper.getAnimatedSprite(AnimationID.valueOf(plant.getName()));
        anim = new AnimatedSprite(sprites, plant.getGrowTime());
    }

    public void setPosition(Rectangle rect)
    {
        this.rect = rect;
    }

    @Override
    public boolean planted(Rectangle rectangle) 
    {
        setPosition(rectangle);
        return true;
    }

    @Override
    public boolean remove()
    {
        rect = null;
        return true;
    }

    public void watering(int waterAmount)
    {
        if(waterLeft > 0) {
            waterLeft -= waterAmount;
        }
    }

    public void getBuff()
    {
        
    }

    private void grow()
    {
        
        if(anim.isLastSprite()) {
            System.out.println("This is the last sprite!");
            return;
        }

        counter++;
        if(counter >= ((CropData) getPlantableData()).getGrowTime() * 60) {
            anim.incrementSprite();
            counter = 0;
        }

    }

    abstract protected void specialAbility();

    @Override
    public void update(GameFrame game)
    {
        grow();
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        if (rect != null)
            anim.render(renderer, rect.x, rect.y, xZoom, yZoom, false);
    }
}
