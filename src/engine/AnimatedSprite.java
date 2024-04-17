package engine;
import java.awt.image.BufferedImage;

import game.GameFrame;

public class AnimatedSprite extends Sprite
{
    private Sprite[] sprites;
    private int currentSprite = 0;
    private int speed;
    private int counter;

    private int startSprite = 0;
    private int endSprite;

    public AnimatedSprite(SpriteSheet sheet, Rectangle[] position, int speed)
    {
        sprites = new Sprite[position.length];
        this.speed = speed;
        endSprite = position.length - 1;
        for (int i = 0; i < position.length; i++)
            sprites[i] = new Sprite(sheet, position[i].x, position[i].y, position[i].w, position[i].h);
    }

    public AnimatedSprite(SpriteSheet sheet, int speed)
    {
        sprites = sheet.getLoadedSprites();
        this.speed = speed;
        this.endSprite = sprites.length - 1;
    }

    //@param speed represnets how many frames pass until the sprite changes
    public AnimatedSprite(BufferedImage[] image, int speed) 
    {
        sprites = new Sprite[image.length];
        this.speed = speed;
        endSprite = image.length - 1;
        for (int i = 0; i < image.length; i++)
            sprites[i] = new Sprite(image[i]);
    }

    public void reset()
    {
        counter = 0;
        currentSprite = startSprite;
    }

    public void setAnimationRange(int startSprite, int endSprite)
    {
        this.startSprite = startSprite;
        this.endSprite = endSprite;
    }

    // Render is dealt specifically with the Layer class
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
    }

    public void update(GameFrame game) 
    {
        counter++;
        if (counter >= speed)
        {
            counter = 0;
            incrementSprite();
        }
    }

    public void incrementSprite()
    {
        if (currentSprite < startSprite)
            currentSprite = startSprite;
        currentSprite++;
        if (currentSprite >= endSprite)
            currentSprite = startSprite;
    }

    @Override
    public int getWidth() 
    {
        return sprites[currentSprite].getWidth();
    }

    @Override
    public int getHeight()
    {
        return sprites[currentSprite].getHeight();
    }

    //this override the get pixels of Sprite class so it renderer.renderSprite can work
    @Override
    public int[] getPixels() 
    { 
        return sprites[currentSprite].getPixels();
    }
}
