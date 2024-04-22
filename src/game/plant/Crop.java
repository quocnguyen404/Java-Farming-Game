package game.plant;

import engine.AnimatedSprite;
import engine.Sprite;
import game.GameFrame;
import game.Helper;
import game.data.CropData;
import game.data.Sprites.AnimationID;

public abstract class Crop extends Plantable
{
    private int counter = 0;
    private AnimatedSprite anim;
    private int waterLeft;

    public Crop(CropData plant)
    {
        this.plantableData = plant;
        Sprite[] sprites = Helper.getAnimatedSprite(AnimationID.valueOf(plant.getName()));
        anim = new AnimatedSprite(sprites, plant.getGrowTime()/sprites.length);
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
}
