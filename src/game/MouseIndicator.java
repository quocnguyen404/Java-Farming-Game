package game;

import engine.GameObject;
import engine.RenderHandler;
import game.data.Sprites.SpriteID;

public class MouseIndicator implements GameObject
{
    private int x, y;
    private SpriteID defaultSprite;
    private SpriteID sprite;

    public MouseIndicator(SpriteID sprite)
    {
        this.defaultSprite = sprite;
        this.sprite = null;
        x = y = 0;
    }

    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        if (sprite != null) renderer.renderSprite(sprite, yZoom, yZoom, xZoom, yZoom, false);
        else renderer.renderSprite(defaultSprite, yZoom, yZoom, xZoom, yZoom, false);
    }

    @Override
    public void update(GameFrame game) 
    {
        // // TODO Auto-generated method stub
        // throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
