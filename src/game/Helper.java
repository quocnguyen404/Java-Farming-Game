package game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class Helper 
{
    public static int handleMousePosition(int mousePos, int camPos, int offset)
    {
        return (int)Math.floor(((double)mousePos + camPos)/offset);
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
