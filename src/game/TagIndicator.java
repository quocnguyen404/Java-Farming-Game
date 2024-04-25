package game;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import engine.GameObject;
import engine.Rectangle;
import engine.RenderHandler;
import game.data.Sprites.SpriteID;

public class TagIndicator implements GameObject
{
    private boolean isVisible;
    private SpriteID spriteID;
    private Rectangle rect;
    private BufferedImage img;
    private String message;
    private Font font;

    //idea
    //render sprite bg first then text
    public TagIndicator (SpriteID spriteID)
    {
        this.spriteID = spriteID;
        this.rect = new Rectangle(10, 10, GameConstant.TILE_WIDTH*3, GameConstant.TILE_HEIGHT);
        if (spriteID == null) this.rect.generateGraphics(1, 0x00000);
        message = "";
        img = new BufferedImage(rect.w, rect.h, BufferedImage.TYPE_INT_RGB);
        isVisible = false;
        font = new Font("Arial", Font.CENTER_BASELINE, 10);
    }

    public boolean isVisible() 
    {
        return isVisible;
    }

    public void setVisible(boolean isVisible) 
    {
        this.isVisible = isVisible;
    }

    public void setPosition(int x, int y)
    {
        rect.setPosition(x, y);
    }

    public void setMessage(String message)
    {
        if (this.message.equals(message) || message.isEmpty()) return;
     
        this.message = message;
        Graphics2D g = img.createGraphics();
        g.setFont(font);
        g.drawString(message, 0, rect.h/2);
        // g.dispose();
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        if (!isVisible) return;
        if (spriteID == null) renderer.renderRectangle(rect, xZoom, yZoom, false);
        else 
        {
            renderer.renderSprite(spriteID, rect.x, rect.y, (rect.w/GameConstant.TILE_WIDTH), (rect.h/GameConstant.TILE_HEIGHT), false);
            renderer.renderImage(img, rect.x, rect.y, xZoom, yZoom, false);
        }
    }

    @Override
    public void update(GameFrame game)
    {
    }
    
}
