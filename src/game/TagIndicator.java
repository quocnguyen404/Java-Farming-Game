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
import game.data.Sprites.SpriteID;

public class TagIndicator implements GameObject
{
    private Rectangle rect;
    private String message = "";
    private int goldAmount = 0;

    private BufferedImage messageViewport;
    private BufferedImage goldViewport;

    private int[] messPixels;
    private int[] goldPixels;
    private Font font;

    //idea
    //render sprite bg first then text
    public TagIndicator (Rectangle rect)
    {
        this.rect = rect;

        messageViewport = new BufferedImage(rect.w/2, rect.h, BufferedImage.TYPE_INT_RGB);
        goldViewport = new BufferedImage(rect.w/2, rect.h, BufferedImage.TYPE_INT_RGB);

        messPixels = ((DataBufferInt)messageViewport.getRaster().getDataBuffer()).getData();
        goldPixels = ((DataBufferInt) goldViewport.getRaster().getDataBuffer()).getData();

        font = new Font("Arial", Font.BOLD, 30);
        resetMessBG();
        resetGoldBG();
    }

    private void resetGoldBG()
    {
        for(int i = 0; i < goldPixels.length; i++) goldPixels[i] = 0xFFFFFF;
    }

    private void resetMessBG()
    {
        for(int i = 0; i < messPixels.length; i++) messPixels[i] = 0xFFFFFF;
    }

    public void setMessage(String message)
    {
        if (this.message.equals(message)) return;
        this.message = message;
        resetMessBG();

        Graphics graphic = messageViewport.createGraphics();
        graphic.setFont(font);
        graphic.setColor(Color.BLACK);
        FontMetrics fm = graphic.getFontMetrics();
        int x = (messageViewport.getWidth()-fm.stringWidth(message)/2);
        int y = (messageViewport.getHeight()-fm.getHeight()/2 + fm.getAscent());
        graphic.drawString(message, x/font.getSize(), rect.h/2+y/6);
        graphic.dispose();
    }

    public void setGold(int goldAmount)
    {
        if (this.goldAmount == goldAmount) return;
        this.goldAmount = goldAmount;
        resetGoldBG();
        String goldText = Integer.toString(goldAmount);

        Graphics graphic = goldViewport.createGraphics();
        graphic.setFont(font);
        graphic.setColor(Color.BLACK);
        FontMetrics fm = graphic.getFontMetrics();
        int x = (goldViewport.getWidth()-fm.stringWidth(goldText)*2);
        int y = (goldViewport.getHeight()-fm.getHeight()/2 + fm.getAscent());
        graphic.drawString(goldText, x, rect.h/2+y/6);
        graphic.dispose();
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        renderer.renderImage(messageViewport, rect.x, rect.y, 1, 1, false);
        renderer.renderImage(goldViewport, rect.x+rect.w/2, rect.y, 1, 1, false);
        renderer.renderSprite(SpriteID.GOLD, rect.x+rect.w-GameConstant.TILE_WIDTH*xZoom*GameFrame.X_ZOOM, rect.y, xZoom, yZoom, false);
    }

    @Override
    public void update(GameFrame game)
    {
    }
    
}
