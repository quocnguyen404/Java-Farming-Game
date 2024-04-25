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
        guis = new GUI[2];

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

            Button<PlantableData> btn = new Button<PlantableData>(SpriteID.valueOf(plantName),
                                                                 new Rectangle(0, rect.y+count*offset, btnW, btnH),
                                                                 Helper.getPlantableData(plantName),
                                                                 true);
            btn.addClickListener(this::buyPlant);
            // btn.ad
            btns[count] = btn;
            count++;
        }
        guis[1].setButtons(btns);
    }

    @Override
    public void update(GameFrame game) 
    {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean mouseMoved(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'mouseMoved'");
    }
    
}
