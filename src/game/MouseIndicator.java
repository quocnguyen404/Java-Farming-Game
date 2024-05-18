package game;

import engine.GameObject;
import engine.Rectangle;
import engine.RenderHandler;
import game.data.ConfigDataHelper;
import game.data.PlantableData;
import game.data.Sprites.SpriteID;
import game.plantable.crop.Crop;

public class MouseIndicator implements GameObject
{
    private boolean isVisible;
    private Rectangle mouseRect;
    private SpriteID spriteID;
    private PlantableData data;
    private Crop sellingCrop;
    private boolean isSelling;

    public MouseIndicator(SpriteID spriteID)
    {
        this.spriteID = spriteID;
        mouseRect = new Rectangle(0, 0, GameConstant.TILE_WIDTH, GameConstant.TILE_HEIGHT);
        // mouseRect.generateGraphics(2, 0xFFFFFF);
        isVisible = false;
        isSelling = false;
    }

    public boolean isSelling() 
    {
        return isSelling;
    }

    public void buyingCrop(PlantableData data)
    {
        isSelling = false;
        setData(data);
        setSprite(SpriteID.valueOf(data.getName()));
    }

    public void sellingCrop(Crop crop)
    {
        sellingCrop = crop;
        setSprite(SpriteID.valueOf(crop.getPlantableData().getName()));
        // ConfigDataHelper.getInstance().
    }

    private void setData(PlantableData data)
    {
        this.data = data;
    }

    public PlantableData getData()
    {
        return data;
    }

    private void setSprite(SpriteID spriteID)
    {
        this.spriteID = spriteID;
    }

    public void setPosition(int x, int y)
    {
        // System.out.println("Drag mouse pos x:" + x + " y:" + y);
        isVisible = true;
        mouseRect.setPosition(x-(GameFrame.X_ZOOM*GameConstant.TILE_WIDTH/2), y-(GameFrame.Y_ZOOM*GameConstant.TILE_HEIGHT/2));
    }

    public void releaseMouse(boolean success)
    {
        spriteID = null;
        isVisible = false;
        if (!success && !isSelling && data != null)
            ConfigDataHelper.getInstance().cancelBuy(data);
        isSelling = true;
        data = null;
    }

    public Crop sellCrop()
    {
        if (!isSelling) return null;
        Crop crop = sellingCrop;
        sellingCrop = null;
        setSprite(null);
        return crop;
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        if (!isVisible) return;
        if (spriteID != null) renderer.renderSprite(spriteID, mouseRect.x, mouseRect.y, xZoom, yZoom, false);
        else if (mouseRect.isGen()) renderer.renderRectangle(mouseRect, xZoom, yZoom, false);
    }

    @Override
    public void update(GameFrame game)
    {
        // throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
}
