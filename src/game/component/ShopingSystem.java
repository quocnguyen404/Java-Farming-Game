package game.component;

import java.util.function.Consumer;

import engine.GUI;
import engine.GUIButton;
import engine.Rectangle;
import game.GameConstant;
import game.GameFrame;
import game.Helper;
import game.data.ConfigDataHelper;
import game.data.CropData;
import game.data.PlantableData;
import game.data.Sprites.SpriteID;
import game.ui.Button;

public class ShopingSystem extends Component
{
    private Consumer<PlantableData> onBuySeed;

    public ShopingSystem(Rectangle rect, int offset, Consumer<PlantableData> onBuySeed)
    {
        super(rect, offset);
        guis = new GUI[2];
        this.onBuySeed = onBuySeed;
        generateUI();
    }

    public void buyPlant(PlantableData plantData)
    {
        if (ConfigDataHelper.getInstance().buyPlantable(plantData))
            onBuySeed.accept(plantData);
    }

    public void sellPlant(CropData plantData)
    {

    }
    
    @Override
    protected void generateUI() 
    {
        //selling part
        guis[0] = new GUI(null, 0, 0, true, true);

        //buying part
        int xPos = GameConstant.WIN_WIDTH - GameConstant.TILE_WIDTH*GameFrame.X_ZOOM - GameConstant.TILE_WIDTH;
        guis[1] = new GUI(null, xPos, 0, true, true);
        GUIButton[] btns = new GUIButton[Helper.getPlantableNumber()];
        int btnW = GameConstant.TILE_WIDTH*GameFrame.X_ZOOM;
        int btnH = GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM;

        int count = 0;
        for (var obj : Helper.getPlantableNames()) 
        {
            String plantName = (String) obj;
            btns[count] = new Button<PlantableData>(SpriteID.valueOf(plantName),
            new Rectangle(0, rect.y+count*offset, btnW, btnH),
            Helper.getPlantableData(plantName),
            this::buyPlant,
            false);
            count++;
        }
        guis[1].setButtons(btns);
    }
    
}
