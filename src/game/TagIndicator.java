package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import engine.GameObject;
import engine.Rectangle;
import engine.RenderHandler;

public class TagIndicator implements GameObject
{
    private Rectangle rect;
    private String message = "";
    private BufferedImage img;
    private int[] pixels;
    private Font font;

    //idea
    //render sprite bg first then text
    public TagIndicator (Rectangle rect)
    {
        this.rect = rect;
        img = new BufferedImage(rect.w, rect.h, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        font = new Font("Arial", Font.BOLD, 30);
        resetBG();
    }

    private void resetBG()
    {
        for(int i = 0; i < pixels.length; i++) pixels[i] = 0xFFFFFF;
    }

    public void setMessage(String message)
    {
        if (this.message.equals(message)) return;
        this.message = message;
        resetBG();

        Graphics graphic = img.createGraphics();
        graphic.setFont(font);
        graphic.setColor(new Color(1));
        FontMetrics fm = graphic.getFontMetrics();
        int x = (img.getWidth()-fm.stringWidth(message)/2);
        int y = (img.getHeight()-fm.getHeight()/2 + fm.getAscent());
        graphic.drawString(message, x/font.getSize(), rect.h/2+y/6);
        graphic.dispose();
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        // renderer.renderImage(img, rect.x, rect.y, 1, 1, false);
        renderer.renderImage(img, rect.x, rect.y, 1, 1, false);
        // renderer.renderSprite(spriteID, rect.x, rect.y, (rect.w/GameConstant.TILE_WIDTH), (rect.h/GameConstant.TILE_HEIGHT), false);
    }

    @Override
    public void update(GameFrame game)
    {
    }
    
}
