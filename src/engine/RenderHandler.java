package engine;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import game.GameConstant;


public class RenderHandler 
{
    private BufferedImage view;
    private Rectangle camera;
    private int[] pixels;
    
    public RenderHandler(int width, int height)
    {
        //Create a BufferImage that will represent out view
        view = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        //create a camera
        camera = new Rectangle(0, 0, width, height);
        // camera.x = -100;
        // camera.y = -30;

        //Create an array for pixels
        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();
    }    

    public Rectangle getCamera() 
    {
        return camera;
    }

    //render array of pixels to the screen
    public void render(Graphics graphics)
    {
        graphics.drawImage(view, 0, 0, view.getWidth(), view.getHeight(), null);
    }

    //render sprite
    public void renderSprite(Sprite sprite, int xPos, int yPos, int xZoom, int yZoom, boolean fixed)
    {   
        int[] spritePixels = sprite.getPixels();

        if (spritePixels == null)
        {
            System.out.println("sprite have load sprite yet");
            return;
        }

        renderArray(spritePixels, sprite.getWidth(), sprite.getHeight(), xPos, yPos, xZoom, yZoom, fixed);
    }

    // render image to array of pixles
    public void renderImage(BufferedImage image, int xPos, int yPos, int xZoom, int yZoom, boolean fixed)
    {
        int[] imagePixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
        renderArray(imagePixels, image.getWidth(), image.getHeight(), xPos, yPos, xZoom, yZoom, fixed);
    }

    //renderRectangle
    public void renderRectangle(Rectangle rec, int xZoom, int yZoom, boolean fixed)
    {
        int[] recPixels = rec.getPixels();
        if (recPixels != null)
            renderArray(rec.getPixels(), rec.w, rec.h, rec.x, rec.y, xZoom, yZoom, fixed);
    }

    public void renderRectangle(Rectangle rec, Rectangle rectOffset, int xZoom, int yZoom, boolean fixed)
    {
        int[] recPixels = rec.getPixels();
        if (recPixels != null)
            renderArray(rec.getPixels(), rec.w, rec.h, rec.x + rectOffset.x, rec.y + rectOffset.y, xZoom, yZoom, fixed);
    }

    //render array
    public void renderArray(int[] renderPixels, int renderWidth, int renderHeight, int xPos, int yPos, int xZoom, int yZoom, boolean fixed)
    {
        for (int y = 0; y < renderHeight; y++)
            for (int x = 0; x < renderWidth; x++)
                for (int yZoomPos = 0; yZoomPos < yZoom; yZoomPos++)
                    for (int xZoomPos = 0; xZoomPos < xZoom; xZoomPos++)
                        setPixel(renderPixels[x + y * renderWidth], ((x*xZoom) + xPos + xZoomPos), ((y*yZoom) + yPos + yZoomPos), fixed);
    
    }

    public void clear()
    {
        for (int i = 0; i < pixels.length; i++)
            pixels[i] = 0;
    }

    //set pixel of pixels array
    private void setPixel(int pixel, int x, int y, boolean fixed)
    {
        int pixelIndex = 0;
        if (!fixed)
        {
            if (x >= camera.x && y >= camera.y && x <= camera.x + camera.w && y <= camera.y + camera.h)
                pixelIndex = (x -camera.x) + (y - camera.y) * view.getWidth();
        }
        else
        {
            if (x >= 0 && y >= 0 && x <= camera.w && y <= camera.h)
                pixelIndex = x + y * view.getWidth();
        }

        if (pixelIndex < pixels.length && pixel != GameConstant.ALPHA)
            pixels[pixelIndex] = pixel; 
    }
}
