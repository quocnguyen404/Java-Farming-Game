import java.awt.image.BufferedImage;

public class SpriteSheet 
{
    private int[] pixels;
    private BufferedImage image;

    private Sprite[] loadedSprites;

    public final int SIZEX;
    public final int SIZEY;

    private int spriteSizeX;
    private boolean isSpriteLoad;

    public SpriteSheet(BufferedImage sheetImage)
    {
        if (sheetImage == null)
            System.out.println("Sheet Image is null");

        image = sheetImage;
        SIZEX = sheetImage.getWidth();
        SIZEY = sheetImage.getHeight();

        pixels = new int[SIZEX*SIZEY];
        pixels = sheetImage.getRGB(0, 0, SIZEX, SIZEY, pixels, 0, SIZEX);
    }

    public void loadSprite(int spriteSizeX, int spriteSizeY)
    {
        this.spriteSizeX = spriteSizeX;
        loadedSprites = new Sprite[(SIZEX/ spriteSizeX) * (SIZEY / spriteSizeY)];

        int spriteID = 0;
        for (int y = 0; y < SIZEY; y += spriteSizeY)
            for (int x = 0; x < SIZEX; x += spriteSizeX)
            {
                loadedSprites[spriteID] = new Sprite(this, x, y, spriteSizeX, spriteSizeY);
                spriteID++;
            }

        isSpriteLoad = true;
    }

    public Sprite getSprite(int x, int y)
    {
        if (!isSpriteLoad)
        {
            System.out.println("Sprite sheet have load yet");
            return null;
        }

        int spriteID = x + y * (SIZEX/spriteSizeX);
        if (spriteID >= loadedSprites.length)
        {
            System.out.println("Get out of the range of sprite sheet");
            return null;
        }

        return loadedSprites[spriteID];
    }

    public int[] getPixels()
    {
        return pixels;
    }

    public BufferedImage getImage()
    {
        return image;
    }
}
