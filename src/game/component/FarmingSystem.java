package game.component;

import java.util.function.Supplier;

import engine.Rectangle;
import engine.RenderHandler;
import game.GameConstant;
import game.GameFrame;
import game.data.PlantableData;

public class FarmingSystem extends Component
{
    private Region[] regions;
    private Supplier<PlantableData> onPlantedSeed;

    public FarmingSystem(Rectangle rect, int offset, Supplier<PlantableData> onPlantedSeed)
    {
        //create or load region
        super(rect, offset);
        regions = new Region[1];
        //weird
        int xPos = (GameConstant.WIN_HEIGHT/(2*GameConstant.TILE_WIDTH*GameFrame.X_ZOOM));
        int yPos = (GameConstant.WIN_HEIGHT/(2*GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM));
        regions[0] = new Region(xPos, yPos);
        this.onPlantedSeed = onPlantedSeed;
        // Sprite turnOnSprite = ConfigDataHelper.getInstance().getSprite(null);
        // turnOnButton = new ButtonAct(, null, null, false)
        generateUI();
    }

    @Override
    protected void generateUI() 
    {
        
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        super.render(renderer, xZoom, yZoom);
        for (Region region : regions)
            region.render(renderer, xZoom, yZoom);
    }
}
