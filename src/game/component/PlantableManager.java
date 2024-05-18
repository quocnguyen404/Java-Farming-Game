package game.component;

import java.util.LinkedList;

import engine.GameObject;
import engine.HandleMouseEvent;
import engine.Rectangle;
import engine.RenderHandler;
import game.GameConstant;
import game.GameFrame;
import game.plantable.crop.Crop;

public class PlantableManager extends Component
{
    private LinkedList<PlantableEnity> enities;

    public PlantableManager(Rectangle rect, int offset) 
    {
        super(rect, offset);
        enities = new LinkedList<PlantableEnity>();
    }

    public void addPlantable(Crop crop)
    {
        PlantableEnity enity = new PlantableEnity(crop);
        enities.add(enity);
    }

    public void removePlantable(PlantableEnity enity)
    {
        enities.remove(enity);
    }

    public void removeCrop(Crop crop)
    {
        for (PlantableEnity enity : enities) {
            if(crop.equals(enity.getCrop()))
            {
                removePlantable(enity);
                break;
            } 
        }
    }

    @Override
    public boolean mouseDragged(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        boolean isDragged = false;
        for (PlantableEnity enity : enities) 
        {
            isDragged = enity.mouseDragged(mouseRectangle, camRectangle, xZoom, yZoom);
            if(isDragged) return true;
        }
        return false;
    }

    @Override
    public boolean mouseDraggedExit(Rectangle mousRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
    {
        boolean onDrag = false;
        for (PlantableEnity enity : enities)
        {
            onDrag = enity.mouseDraggedExit(mousRectangle, camRectangle, xZoom, yZoom);
            if(onDrag) return true;
        }

        return false;
    }

    @Override
    public void update(GameFrame game) {
        super.update(game);
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        super.render(renderer, xZoom, yZoom);
        for (PlantableEnity enity : enities) enity.render(renderer, xZoom, yZoom);                
    }

    @Override
    protected void generateUI() 
    {
        guis = null;
    }

    /**
     * PlantableEnity
     */
    public class PlantableEnity implements GameObject, HandleMouseEvent
    {
        private int counter;
        private Crop crop;

        public PlantableEnity(Crop crop)
        {
            this.crop = crop;
            counter = GameConstant.OBJECT_EXIST_TIME*60;
        }

        public Crop getCrop() 
        {
            return crop;
        }

        @Override
        public void render(RenderHandler renderer, int xZoom, int yZoom) 
        {
            // TODO rendering on screen
            crop.render(renderer, xZoom, yZoom);
        }

        @Override
        public void update(GameFrame game) 
        {
            // TODO time counter for exist time on screen
            counter--;
            if(counter == 0)
            {
                removePlantable(this);
            }
        }

        @Override
        public boolean mouseHover(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
        {
            //TODO display name and sell price
            return false;
        }

        @Override
        public boolean mouseDragged(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
        {
            // TODO mouse drag this entity
            return crop.mouseDragged(mouseRectangle, camRectangle, xZoom, yZoom);
        }

        @Override
        public boolean leftMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom)
        {
            return false;
        }

        @Override
        public boolean rightMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
        {
            return false;
        }

        @Override
        public boolean mouseDraggedExit(Rectangle mousRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
        {
            // TODO drop on the selling cell in shopping system
            return crop.mouseDraggedExit(mousRectangle, camRectangle, xZoom, yZoom);
        }
    }
}
