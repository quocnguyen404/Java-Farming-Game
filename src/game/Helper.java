package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import engine.Sprite;
import game.data.ConfigDataHelper;
import game.data.PlantData;
import game.data.Sprites.AnimationID;
import game.data.Sprites.SpriteID;

public final class Helper 
{
    public static int handleMousePosition(int mousePos, int camPos, int offset)
    {
        return (int)Math.floor(((double)mousePos + camPos)/offset);
    }

    public static PlantData getPlantData(String name)
    {
        return ConfigDataHelper.getInstance().getPlantData(name);
    }

    public static Object[] getPlantNames()
    {
        return ConfigDataHelper.getInstance().getPlantNames();
    }

    public static Sprite[] getAnimatedSprite(AnimationID id)
    {
        return ConfigDataHelper.getInstance().getAnimtedSprite(id);
    }

    public static Sprite getSprite(SpriteID id)
    {
        return ConfigDataHelper.getInstance().getSprite(id);
    }
    
    public static int getPlantNumber()
    {
        return ConfigDataHelper.getInstance().getPlantNumber();
    }

    public static int getSpriteWidth(SpriteID spriteID)
    {
        return ConfigDataHelper.getInstance().getSprite(spriteID).getWidth();
    }

    public static int getSpriteHeight(SpriteID spriteID)
    {
        return ConfigDataHelper.getInstance().getSprite(spriteID).getHeight();
    }

    //loadImage
    public static BufferedImage loadImage(String path)
    {
        try 
        {
            BufferedImage loadedImage = ImageIO.read(new File(path));
            BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
            return formattedImage;
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            return null;
        }
    }

    private Helper()
    {

    }
}
