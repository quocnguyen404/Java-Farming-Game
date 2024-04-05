package game.component;

import java.util.ArrayList;
import java.util.function.Consumer;

import engine.GUI;
import engine.GUIButton;
import engine.Rectangle;
import engine.RenderHandler;
import game.GameConstant;
import game.GameFrame;
import game.Map.EditMode;
import game.data.ConfigDataHelper;
import game.data.Tiles;
import game.data.Tiles.Tile;
import game.data.Tiles.TileID;
import game.data.Tools.ToolID;
import game.ui.Button;
import game.ui.ButtonAct;

public class BuildingSystem extends Component
{
    public Consumer<EditMode> onChangeEditMode;
    public Consumer<TileID> onChangeEditTileID;

    public BuildingSystem(Consumer<EditMode> editEvent, Consumer<TileID> editTileIDEvent)
    {
        this.onChangeEditMode = editEvent;
        this.onChangeEditTileID = editTileIDEvent;

        turnOnButton = new ButtonAct(ConfigDataHelper.getInstance().getTools().getTool(ToolID.HAMMER).sprite,
                                    new Rectangle(10, 5, 16*GameFrame.X_ZOOM, 16*GameFrame.Y_ZOOM), 
                                    () -> { setButtonsVisibility(); },
                                    true);

        generateUI();
    }

    @Override
    protected void setButtonsVisibility() 
    {
        super.setButtonsVisibility();
        onChangeEditMode.accept(EditMode.NONE);
    }

    @Override
    protected void generateUI() 
    {
        //uis buttons for building
        Tiles tiles = ConfigDataHelper.getInstance().getTiles();
        ArrayList<Tile> tileList = tiles.getTileList();

        buttons = new GUI(null, 10, 50, true, false);
        //gui button
        GUIButton[] guiButtons = new GUIButton[tiles.getSize()+1];

        int i;
        for (i = 0; i < tiles.getSize(); i++)
        {
            Rectangle btnRect = new Rectangle(0, 
                                              i*(16*GameFrame.X_ZOOM + 3), 
                                              GameConstant.TILE_WIDTH*GameFrame.X_ZOOM, 
                                              GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM);

            guiButtons[i] = new Button<TileID>(tileList.get(i).sprite, btnRect, tileList.get(i).tileID, onChangeEditTileID, false);
        }


        guiButtons[i] = new Button<EditMode>(ConfigDataHelper.getInstance().getTools().getTool(ToolID.SHOVEL).sprite,
                                             new Rectangle(0, i*(16*GameFrame.X_ZOOM + 3), GameConstant.TILE_WIDTH*GameFrame.X_ZOOM, GameConstant.TILE_HEIGHT*GameFrame.Y_ZOOM), 
                                             EditMode.REMOVING, 
                                             onChangeEditMode, 
                                             true);

        buttons.setButtons(guiButtons);
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        super.render(renderer, xZoom, yZoom);
        turnOnButton.render(renderer, xZoom, yZoom);
    }
}
