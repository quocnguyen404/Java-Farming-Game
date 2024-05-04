package game.plantable;

import engine.Rectangle;
import engine.RenderHandler;
import game.GameFrame;
import game.data.PlantableData;
import game.data.Sprites.SpriteID;

public class Dirt extends Plantable
{
    private SpriteID spriteID;
    private Rectangle rect;

    public Dirt(PlantableData data) 
    {
        super(data);
        spriteID = SpriteID.PLANT_HOLE;
    }

    public void setRectangle(Rectangle rect)
    {
        this.rect = rect;
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        renderer.renderSprite(spriteID, rect.x, rect.y, xZoom, yZoom, false);
    }

    @Override
    public void update(GameFrame game) 
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

    @Override
    public void mouseHover(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }
    
}
