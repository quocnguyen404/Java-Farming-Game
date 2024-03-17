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
            pixels[i] = GameFrame.alpha;

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
}