package engine;
import game.GameConstanst;

public class Rectangle
{
    private int[] pixels;
    public int x, y, w, h;
    
    public Rectangle(int x, int y, int w, int h)
    {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public Rectangle()
    {
        this(0, 0, 0, 0);
    }

    //AABB
    public boolean intersects(Rectangle otherRect)
    {
        return this.x < otherRect.x + otherRect.w &&
               this.x + this.w > otherRect.x &&
               this.y < otherRect.y + otherRect.h &&
               this.y + this.h > otherRect.y;
    }

    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public void generateGraphics(int color)
    {
        pixels = new int[w*h];
        
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                pixels[x+y*w] = color;
    }

    public void generateGraphics(int borderWidth, int color)
    {
        pixels = new int[w*h];

        //set all to transparent
        for (int i = 0; i < pixels.length; i++)
            pixels[i] = GameConstanst.ALPHA;

        //upper border
        for (int y = 0; y < borderWidth; y++)
            for (int x = 0; x < w; x++)
                    pixels[x + y * w] = color;            

        //left border
        for (int y = 0; y < h; y++)
            for (int x = 0; x < borderWidth; x++)
                pixels[x + y * w] = color;

        //right border
        for (int y = 0; y < h; y++)
            for (int x = w - borderWidth; x < w; x++)
                pixels[x + y * w] = color;
        
        //bottom border
        for (int y = h - borderWidth; y < h; y++)
            for (int x = 0; x < w; x++)
                pixels[x + y * w] = color;
    }

    public int[] getPixels()
    {
        if (pixels != null) return pixels;
        else System.out.println("Attemped to retrive pixels from a Rectangle withou generated graphics");
        return null;
    }

    @Override
    public String toString() 
    {
        return String.format("{%d, %d, %d, %d}", x, y, w, h);    
    }
}