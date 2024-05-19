package engine;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Sprite implements Serializable
{
    private static final long serialVersionUID = 13L;

    private int width, height;
    private int[] pixels;

    public Sprite() {}
    public Sprite(SpriteSheet sheet, int startX, int startY, int width, int height)
    {
        this.width = width;
        this.height = height;
        pixels = new int[width*height];
        pixels = sheet.getImage().getRGB(startX, startY, width, height, pixels, 0, width);
    }

    public Sprite(BufferedImage image)
    {
        width = image.getWidth();
        height = image.getHeight();

        pixels = new int[width*height];
        pixels = image.getRGB(0, 0, width, height, pixels, 0, width);
    }

    public int[] getPixels() 
    {
        return pixels;
    }

    public int getWidth() 
    {
        return width;
    }

    public int getHeight() 
    {
        return height;
    }
}
