package game.component;

import java.util.function.Consumer;

import engine.GUI;
import engine.GUIButton;
import engine.Rectangle;
import engine.RenderHandler;
import game.GameConstant;
import game.GameFrame;
import game.Helper;
import game.data.PlantData;
import game.data.Sprites.SpriteID;
import game.ui.Button;

public class FarmingSystem extends Component
{
    private Rectangle rect;
    private int offset;
    private Region[] regions;
    Consumer<PlantData> onPickPlant;

    public FarmingSystem(Rectangle rect, int offset, Consumer<PlantData> onPickPlant)
    {
        //create or load region
        this.rect = rect;
        this.offset = offset;
        regions = new Region[1];
        //weird
        int xPos = (GameConstant.WIN_HEIGHT/(2*GameConstant.TILE_WIDTH*GameFrame.X_ZOOM));
        int yPos = (GameConstant.WIN_HEIGHT/(2*GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM));
        regions[0] = new Region(xPos, yPos);
        this.onPickPlant = onPickPlant;
        // Sprite turnOnSprite = ConfigDataHelper.getInstance().getSprite(null);
        // turnOnButton = new ButtonAct(, null, null, false)
        generateUI();
    }

    @Override
    protected void generateUI() 
    {
        gui = new GUI(null, rect.x, rect.y, true, true);
        GUIButton[] btns = new GUIButton[Helper.getPlantNumber()];
        int btnW = GameConstant.TILE_WIDTH*GameFrame.X_ZOOM;
        int btnH = GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM;

        int count = 0;
        for (var obj : Helper.getPlantNames()) 
        {
            String plantName = (String) obj;
            btns[count] = new Button<PlantData>(SpriteID.valueOf(plantName),
            new Rectangle(0, rect.y+count*offset, btnW, btnH),
            Helper.getPlantData(plantName),
            onPickPlant,
    false);
            count++;
        }
        gui.setButtons(btns);
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        super.render(renderer, xZoom, yZoom);
        for (Region region : regions)
            region.render(renderer, xZoom, yZoom);
    }
}
