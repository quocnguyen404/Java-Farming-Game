package game.component;

import java.util.function.Supplier;

import engine.Rectangle;
import engine.RenderHandler;
import game.GameFrame;
import game.data.ConfigDataHelper;
import game.data.CropData;
import game.data.ItemData;
import game.data.PlantableData;
import game.plantable.Dirt;
import game.plantable.Plantable;
import game.plantable.crop.Crop;
import game.plantable.crop.FoodCrop;
import game.plantable.crop.FruitCrop;
import game.plantable.crop.OilCrop;
import game.plantable.item.GoldBuff;
import game.plantable.item.Item;
import game.plantable.item.TimeBuff;
import game.plantable.item.WaterBuff;

public class FarmingSystem extends Component
{
    public static Supplier<PlantableData> onPlantedSeed;
    private Region[] regions;

    public FarmingSystem(Rectangle rect, int offset)
    {
        super(rect, offset);
        //create or load region
        generateUI();
        
        // Region.onGetPlantable = this::onGetPlantable;
        // Sprite turnOnSprite = ConfigDataHelper.getInstance().getSprite(null);
        // turnOnButton = new ButtonAct(, null, null, false);
    }

    public static Plantable onGetPlantable()
    {
        PlantableData data = onPlantedSeed.get();
        if (data == null) return null;
        if (data.getName().equals("PLANT_HOLE")) return new Dirt(data);

        if (data instanceof ItemData)
        {
            Item item;
            ItemData itemData = (ItemData) data;
            switch (itemData.getModify()) {
                case 0: //Gold buff
                    item = new GoldBuff(itemData);
                    break;
                case 1: //Water buff
                    item = new WaterBuff(itemData);
                    break;
                case 2: //Time buff
                    item = new TimeBuff(itemData);
                    break;
                default:
                    item = new WaterBuff(itemData);
            }
            return item;
        }

        Crop crop;
        CropData cropData = (CropData) data;
        switch (cropData.getModify())
        {
            case 0: //Food crop
                crop = new FoodCrop(cropData);
                break;
            case 1: //Fruit crop
                crop = new FruitCrop(cropData);
                break;
            case 2: //Oil crop
                crop = new OilCrop(cropData);
                break;
            default:
                crop = new FoodCrop(cropData);
        }
        return crop;
    }

    @Override
    protected void generateUI() 
    {
        regions = ConfigDataHelper.getInstance().getRegion();
        // regions = new Region[1];
        // //weird
        // int xPos = (GameConstant.WIN_HEIGHT/(2*GameConstant.TILE_WIDTH*GameFrame.X_ZOOM));
        // int yPos = (GameConstant.WIN_HEIGHT/(2*GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM));
        // regions[0] = new Region(xPos, yPos);
    }

    @Override
    public boolean mouseDraggedExit(Rectangle mousRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        for (Region region : regions) 
            if(region != null) return region.mouseDraggedExit(mousRectangle, camRectangle, xZoom, yZoom);  
        return super.mouseDraggedExit(mousRectangle, camRectangle, xZoom, yZoom);
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        super.render(renderer, xZoom, yZoom);
        for (Region region : regions)
            region.render(renderer, xZoom, yZoom);
    }

    @Override
    public void update(GameFrame game) 
    {
        super.update(game);
        for (Region region : regions) 
            region.update(game);    
    }

    @Override
    public boolean mouseHover(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        return false;
    }
}
