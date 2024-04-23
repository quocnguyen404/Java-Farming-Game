package game.plantable;

import engine.AnimatedSprite;
import engine.Rectangle;
import engine.RenderHandler;
import engine.Sprite;
import game.GameFrame;
import game.Helper;
import game.data.CropData;
import game.data.Sprites.AnimationID;

public abstract class Crop extends Plantable
{
    private Rectangle rect;
    private int counter = 0;
    private AnimatedSprite anim;
    private int waterLeft;

    public Crop(CropData plant)
    {
        super(plant);
        Sprite[] sprites = Helper.getAnimatedSprite(AnimationID.valueOf(plant.getName()));
        anim = new AnimatedSprite(sprites, plant.getGrowTime()/sprites.length);
    }

    public void setPosition(Rectangle rect)
    {
        this.rect = rect;
    }

    @Override
    public  boolean planted()
    {
        return false;
    }

    @Override
    public  boolean remove()
    {
        return false;
    }

    public void watering()
    {
    }

    private void grow()
    {

    }

    abstract protected void specialAbility();

    @Override
    public void update(GameFrame game)
    {
        // if (counter > )
        anim.update(game);
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        if (rect != null)
            anim.render(renderer, rect.x, rect.y, xZoom, yZoom, false);
    }
}
