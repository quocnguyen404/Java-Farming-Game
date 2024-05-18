package game.component;

import java.util.function.Consumer;
import java.util.function.Supplier;

import engine.GUI;
import engine.GUIButton;
import engine.Rectangle;
import engine.RenderHandler;
import game.GameConstant;
import game.GameFrame;
import game.Helper;
import game.data.ConfigDataHelper;
import game.data.CropData;
import game.data.PlantableData;
import game.data.Sprites.SpriteID;
import game.ui.Button;
import game.ui.ButtonAct;

public class ShopingSystem extends Component
{
    public static Consumer<PlantableData> onBuySeed;
    public static Consumer<PlantableData> onHoverSeed;
    public static Supplier<PlantableData> onSellCrop;

    private SellingCell sellingCell;

    public ShopingSystem(Rectangle rect, int offset)
    {
        super(rect, offset);
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
        //init part
        guis = new GUI[2];
        int btnW = GameConstant.TILE_WIDTH*GameFrame.X_ZOOM;
        int btnH = GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM;

        //selling part
        guis[0] = new GUI(null, 0, 0, true, true);
        GUIButton[] sellBtns = new GUIButton[1];
        ButtonAct sellBtn = new ButtonAct(SpriteID.GREEN1_SQUARE, new Rectangle(0, rect.y, btnW, btnH), false);
        //init selling cell

        sellingCell = new SellingCell(new Rectangle(0, rect.y + offset, GameConstant.TILE_WIDTH*GameFrame.X_ZOOM, GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM));

        //TODO add sell action
        sellBtn.addClickListener(null);
        sellBtns[0] = sellBtn;
        guis[0].setButtons(sellBtns);

        //buying part
        int xPos = GameConstant.WIN_WIDTH - GameConstant.TILE_WIDTH*GameFrame.X_ZOOM - GameConstant.TILE_WIDTH;
        guis[1] = new GUI(null, xPos, 0, true, true);
        GUIButton[] btns = new GUIButton[Helper.getPlantableNumber()];
        int count = 0;
        for (var obj : Helper.getPlantableNames()) 
        {
            String plantName = (String) obj;
            Button<PlantableData> btn = new Button<PlantableData>(SpriteID.valueOf(plantName),
                                                                 new Rectangle(0, rect.y+count*offset, btnW, btnH),
                                                                 Helper.getPlantableData(plantName),
                                                                 true);
            btn.addClickListener(this::buyPlant);
            btn.addHoverListener(onHoverSeed);
            btn.addExitHover(GameFrame.defaultMessage);
            // btn.ad
            btns[count] = btn;
            count++;
        }
        guis[1].setButtons(btns);
    }

    // @Override
    // public void update(GameFrame game) 
    // {
    // }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        super.render(renderer, xZoom, yZoom);
        sellingCell.render(renderer, 1, 1);
    }

    @Override
    public boolean mouseDraggedExit(Rectangle mousRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        return sellingCell.mouseDraggedExit(mousRectangle, camRectangle, xZoom, yZoom);
    }

    @Override
    public boolean mouseHover(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        boolean isHover = false;
        for (GUI gui : guis)
        {
            isHover = gui.mouseHover(mouseRectangle, camRectangle, xZoom, yZoom);
            if(isHover) break;
        } 
        return isHover;
    }
}
