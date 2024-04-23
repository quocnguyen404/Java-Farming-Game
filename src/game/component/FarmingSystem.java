package game.component;

import java.util.function.Supplier;

import engine.Rectangle;
import engine.RenderHandler;
import game.GameConstant;
import game.GameFrame;
import game.data.CropData;
import game.data.PlantableData;
import game.plantable.Crop;
import game.plantable.FoodCrop;
import game.plantable.FruitCrop;
import game.plantable.OilCrop;

public class FarmingSystem extends Component
{
    private Region[] regions;
    private Supplier<PlantableData> onPlantedSeed;

    public FarmingSystem(Rectangle rect, int offset, Supplier<PlantableData> onPlantedSeed)
    {
        //create or load region
        super(rect, offset);
        generateUI();
        
        this.onPlantedSeed = onPlantedSeed;
        Region.onGetCrop = this::onCreateCrop;
        // Sprite turnOnSprite = ConfigDataHelper.getInstance().getSprite(null);
        // turnOnButton = new ButtonAct(, null, null, false);
    }

    private Crop onCreateCrop()
    {
        CropData data = (CropData)onPlantedSeed.get();
        if (data == null)
            return null;
        Crop crop;
        switch (data.getModify())
        {
            case 0: //Food crop
                crop = new FoodCrop(data);
                break;
            case 1: //Fruit crop
                crop = new FruitCrop(data);
                break;
            case 2: //Oil crop
                crop = new OilCrop(data);
                break;
            default:
                crop = new FoodCrop(data);
        }
        return crop;
    }

    @Override
    protected void generateUI() 
    {
        regions = new Region[1];
        //weird
        int xPos = (GameConstant.WIN_HEIGHT/(2*GameConstant.TILE_WIDTH*GameFrame.X_ZOOM));
        int yPos = (GameConstant.WIN_HEIGHT/(2*GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM));
        regions[0] = new Region(xPos, yPos);
    }

    @Override
    public boolean mouseDraggedExit(Rectangle mousRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        for (Region region : regions) 
            if(region != null) region.mouseDraggedExit(mousRectangle, camRectangle, xZoom, yZoom);  
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
        for (Region region : regions) 
            region.update(game);    
    }
}
