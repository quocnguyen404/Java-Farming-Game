package game.component;

import java.util.ArrayList;
import java.util.function.Consumer;

import engine.GUI;
import engine.GUIButton;
import engine.Rectangle;
import engine.Tiles;
import engine.Map.EditMode;
import engine.Tiles.Tile;
import engine.Tiles.TileID;
import game.GameConstanst;
import game.GameFrame;
import game.SDKButton;
import game.data.ConfigDataHelper;

public class BuildingSystem extends Component
{
    public Consumer<EditMode> onChangeEditMode;
    public Consumer<TileID> onChangeEditTileID;
    
    public BuildingSystem(Consumer<EditMode> editEvent, Consumer<TileID> editTileIDEvent)
    {
        this.onChangeEditMode = editEvent;
        this.onChangeEditTileID = editTileIDEvent;
        generateUI();
    }

    @Override
    protected void generateUI() 
    {
        //uis buttons for building
        Tiles tiles = ConfigDataHelper.getInstance().getTiles();
        ArrayList<Tile> tileList = tiles.getTileList();

        //gui button
        GUIButton[] guiButtons = new GUIButton[tiles.getSize()];
        for (int i = 0; i < guiButtons.length; i++)
        {
            Rectangle btnRect = new Rectangle(0, i*(16*GameFrame.X_ZOOM), 
                                                GameConstanst.TILE_WIDTH*GameFrame.X_ZOOM, 
                                                GameConstanst.TILE_HEIGHT*GameFrame.Y_ZOOM);

            guiButtons[i] = new TileButton(tileList.get(i), btnRect, onChangeEditTileID);
        }
        buttons = new GUI(guiButtons, 5, 5, true, true);
    }

    /**
     * TileButton
     */
    public class TileButton extends SDKButton 
    {
        private Consumer<TileID> onActivate;
        private Tile tile;

        public TileButton(Tile tile, Rectangle btnRect, Consumer<TileID> onActivate)
        {
            super(tile.sprite, btnRect);
            this.tile = tile;
            this.onActivate = onActivate;
        }

        @Override
        public void activate() 
        {
            System.out.println("On change edit tile ID: " + tile.tileID);
            onActivate.accept(tile.tileID);
        }

    }
}
