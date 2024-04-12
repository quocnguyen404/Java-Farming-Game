package game.component;

import java.util.function.Consumer;

import engine.GUI;
import engine.GUIButton;
import engine.RenderHandler;
import game.GameConstant;
import game.GameFrame;
import game.data.ConfigDataHelper;
import game.data.PlantData;
import game.ui.ButtonAct;

public class FarmingSystem extends Component
{
    private Region[] regions;
    Consumer<PlantData> onPickPlant;

    public FarmingSystem()
    {
        //create or load region
        regions = new Region[1];
        int xPos = (GameConstant.WIN_HEIGHT/(2*GameConstant.TILE_WIDTH*GameFrame.X_ZOOM));
        int yPos = (GameConstant.WIN_HEIGHT/(2*GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM));
        regions[0] = new Region(xPos, yPos);

        // Sprite turnOnSprite = ConfigDataHelper.getInstance().getSprite(null);
        // turnOnButton = new ButtonAct(, null, null, false)
        generateUI();
    }

    @Override
    protected void generateUI() 
    {
        buttons = new GUI(null, 50, 50, true, false);
        GUIButton[] btns = new GUIButton[3];

        // for (int i = 0; i < btns.length; i++)
        // {
        //     btns[i] = new Button<PlantData>(ConfigDataHelper.getInstance().getSprite(SpriteID.PLANT), null, null, onPickPlant, false);
            
        // }
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        super.render(renderer, xZoom, yZoom);
        for (Region region : regions)
            region.render(renderer, xZoom, yZoom);
    }
}
