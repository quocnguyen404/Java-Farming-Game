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
    private AnimatedSprite animatedSprite;
    private Rectangle rect;

    public Dirt(PlantableData data)
    {
        super(data);
        animatedSprite = new AnimatedSprite(Helper.getAnimatedSprite(AnimationID.DIRT), 60);
    }

    public void setRectangle(Rectangle rect)
    {
        this.rect = rect;
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        animatedSprite.render(renderer, rect.x, rect.y, xZoom, yZoom, false);
    }

    @Override
    public void update(GameFrame game) 
    {
        animatedSprite.update(game);
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
    public void mouseHover(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        // TODO Auto-generated method stub
    }
    
}
