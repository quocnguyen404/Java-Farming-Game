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
        anim.update(game);
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        if (rect != null)
            anim.render(renderer, rect.x, rect.y, xZoom, yZoom, false);
    }
}