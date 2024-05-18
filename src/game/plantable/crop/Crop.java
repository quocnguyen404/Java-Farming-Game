package game.plantable.crop;

import java.util.function.Consumer;

import engine.AnimatedSprite;
import engine.Rectangle;
import engine.RenderHandler;
import engine.Sprite;
import game.GameFrame;
import game.Helper;
import game.component.SellingCell;
import game.data.CropData;
import game.data.Sprites.AnimationID;
import game.data.Sprites.SpriteID;
import game.plantable.Plantable;

public abstract class Crop extends Plantable
{
    public Consumer<Crop> onCropGrow;
    public Consumer<Integer> onWatering;
    private Rectangle rect;
    private AnimatedSprite anim;
    private SpriteID icon;
    private int counter = 0;
    private int waterLeft;
    private boolean isRipe;
    private int buffGold;
    private int buffTime;

    public Crop(CropData plant)
    {
        super(plant);
        //TODO 
        waterLeft = plant.getWaterDrop();
        Sprite[] sprites = Helper.getAnimatedSprite(AnimationID.valueOf(plant.getName()));
        anim = new AnimatedSprite(sprites, plant.getGrowTime());
        icon = SpriteID.valueOf(plant.getName());
        buffGold = 0;
        buffTime = 0;
    }

    public void setPosition(Rectangle rect)
    {
        this.rect = rect;
    }

    @Override
    public boolean planted(Rectangle rectangle) 
    {
        setPosition(rectangle);
        return true;
    }

    @Override
    public boolean remove()
    {
        rect = null;
        return true;
    }

    public void watering(int waterAmount)
    {
        if(waterLeft > 0)
        {
            waterLeft -= waterAmount;
            onWatering.accept(waterLeft);
        }
    }

    public void getGoldBuff(int buffGold)
    {
        this.buffGold+=buffGold;
    }

    public void getWaterBuff(int waterBuff)
    {
        watering(waterBuff);
    }

    public void getTimeBuff(int timeBuff)
    {
        buffTime += timeBuff;
    }


    public int getSellingPrice()
    {
        return buffGold + ((CropData)getPlantableData()).getSellPrice();
    }

    public void growRipe(Rectangle newRect)
    {
        setPosition(newRect);
        isRipe = true;
        anim = null;
    }

    private void grow()
    {
        if(anim.isLastSprite()) 
        {
            onCropGrow.accept(this);
            System.out.println(getPlantableData().getName() + " grow");
            return;
        }

        counter++;
        if(counter >= (((CropData) (getPlantableData())).getGrowTime()-buffTime) * 60)
        {
            anim.incrementSprite();
            counter = 0;
        }
    }

    abstract protected void specialAbility();

    private boolean onDrag = false;
    @Override
    public boolean mouseDragged(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        if (!isRipe) return false;
        if(!SellingCell.onGetSellingModify.get()) return false;
        
        if(rect.intersects(mouseRectangle))
        {
            GameFrame.onSellingCrop.accept(this);
            onDrag = true;
        }

        if(onDrag)
        {
            GameFrame.onSetMessage.accept("selling " + getPlantableData().getName().toLowerCase());
            rect.setPosition(mouseRectangle.x-rect.w/2, mouseRectangle.y-rect.h/2);
            return true;
        }

        return false;
    }

    @Override
    public boolean mouseDraggedExit(Rectangle mousRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        if(onDrag)
        {
            onDrag = false;
            GameFrame.defaultMessage.run();
        }
        return false;
    }

    @Override
    public void update(GameFrame game)
    {
        if (waterLeft <= 0 && !isRipe)
            grow();
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        if (rect != null)
        {
            if (anim != null) anim.render(renderer, rect.x, rect.y, xZoom, yZoom, false);
            else renderer.renderSprite(icon, rect.x, rect.y, xZoom, yZoom, false);
        }
    }
}
