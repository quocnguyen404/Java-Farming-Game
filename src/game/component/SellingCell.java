package game.component;

import java.util.ArrayList;
import java.util.function.Supplier;

import engine.GameObject;
import engine.HandleMouseEvent;
import engine.Rectangle;
import engine.RenderHandler;
import game.GameConstant;
import game.GameFrame;
import game.data.PlantableData;

public class SellingCell implements GameObject, HandleMouseEvent
{
    private ArrayList<PlantableData> sellingCrops;
    private Rectangle rect;
    private Supplier<PlantableData> onGetCrop;

    public SellingCell(Rectangle rect, Supplier<PlantableData> onGetCrop)
    {
        this.rect = rect;
        this.rect.generateGraphics(0xFFFFFF);
        this.onGetCrop = onGetCrop;
        sellingCrops = new ArrayList<>();
    }

    private void reSize()
    {
        if (sellingCrops.size() > 1) rect.h = GameConstant.TILE_HEIGHT*sellingCrops.size();
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        if (rect.isGen()) renderer.renderRectangle(rect, xZoom, yZoom, true);
    }

    @Override
    public void update(GameFrame game) {}
    
    @Override
    public boolean mouseDraggedExit(Rectangle mousRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        //TODO implement selling cell mouse dragged exit
        if (!this.rect.intersects(mousRectangle)) return false;
        PlantableData crop = onGetCrop.get();
        if (crop != null)
        {
            sellingCrops.add(crop);
            reSize();
        }

        return true;
    }

    @Override
    public boolean leftMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }

    @Override
    public boolean rightMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }

    @Override
    public void mouseHover(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) {}

    @Override
    public boolean mouseDragged(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) { return false; }
}