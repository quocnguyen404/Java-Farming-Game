package game.plantable;

import engine.AnimatedSprite;
import engine.Rectangle;
import engine.RenderHandler;
import game.GameFrame;
import game.Helper;
import game.data.PlantableData;
import game.data.Sprites.AnimationID;

public class Dirt extends Plantable
{
    private static final long serialVersionUID = 12L;
    private AnimatedSprite animatedSprite;
    private Rectangle rect;

    public Dirt(PlantableData data)
    {
        super(data);
        animatedSprite = getAnim();
    }

    public void watering(int waterLeft)
    {
        if(animatedSprite.isLastSprite()) return;
        animatedSprite.incrementSprite();
        if(waterLeft <= 0)
        {
            while(!animatedSprite.isLastSprite()) animatedSprite.incrementSprite();
            return;
        }
    }

    public AnimatedSprite getAnim()
    {
        if(animatedSprite == null) animatedSprite = new AnimatedSprite(Helper.getAnimatedSprite(AnimationID.DIRT), 60);
        return animatedSprite;
    }

    public void reset()
    {
        animatedSprite.reset();
    }

    public void setRectangle(Rectangle rect)
    {
        this.rect = rect;
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        getAnim().render(renderer, rect.x, rect.y, xZoom, yZoom, false);
    }

    @Override
    public void update(GameFrame game) 
    {
        // animatedSprite.update(game);
    }

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
    public boolean mouseHover(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        return false;
    }
    
}
