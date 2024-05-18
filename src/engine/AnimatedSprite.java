package engine;

import game.GameFrame;

public class AnimatedSprite
{
    private Sprite[] sprites;
    private int currentSprite = 0;
    private int speed;
    private int counter = 0;

    private int startSprite = 0;
    private int endSprite;
    private boolean isFinalSprite = false;

    // public AnimatedSprite(SpriteID)
    public AnimatedSprite(Sprite[] sprites, int speed)
    {
        this.sprites = sprites;
        this.speed = speed;
        setAnimationRange(0, sprites.length);
        currentSprite = startSprite;
        
    }

    public void reset()
    {
        counter = 0;
        currentSprite = startSprite;
        isFinalSprite = false;
    }

    public void setAnimationRange(int startSprite, int endSprite)
    {
        this.startSprite = startSprite;
        this.endSprite = endSprite;
    }

    // Render is dealt specifically with the Layer class
    public void render(RenderHandler renderer, int xPos, int yPos, int xZoom, int yZoom, boolean fixed) 
    {
        renderer.renderSprite(sprites[currentSprite], xPos, yPos, xZoom, yZoom, fixed);
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

    public boolean isLastSprite() 
    {
        return isFinalSprite;
    }

    public void incrementSprite()
    {
        if (isFinalSprite) return;
        if (currentSprite < startSprite)
            currentSprite = startSprite;
       if(currentSprite == endSprite-1)
       {
            isFinalSprite = true;
            // currentSprite = endSprite-1;
            System.out.println("Current sprite: " + currentSprite);
       }
       else if(currentSprite < endSprite)
       {
            currentSprite++;
            System.out.println("Is on sprite: " + currentSprite);
       }
    }
}
