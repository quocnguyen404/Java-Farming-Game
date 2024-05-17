package game.component;

import java.util.LinkedList;

import engine.GameObject;
import engine.HandleMouseEvent;
import engine.Rectangle;
import engine.RenderHandler;
import game.GameConstant;
import game.GameFrame;
import game.plantable.Plantable;

public class PlantableManager extends Component
{
    private LinkedList<PlantableEnity> enities;

    public PlantableManager(Rectangle rect, int offset) 
    {
        super(rect, offset);
        enities = new LinkedList<PlantableEnity>();
    }

    public void addPlantable(Plantable plantable)
    {
        PlantableEnity enity = new PlantableEnity(plantable);
        enities.add(enity);
    }

    public void removePlantable(PlantableEnity plantable)
    {
        enities.remove(plantable);
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
        private Plantable plantable;

        public PlantableEnity(Plantable plantable)
        {
            this.plantable = plantable;
            counter = GameConstant.OBJECT_EXIST_TIME*60;
        }

        @Override
        public void render(RenderHandler renderer, int xZoom, int yZoom) 
        {
            // TODO rendering on screen
            plantable.render(renderer, xZoom, yZoom);
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
        public void mouseHover(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
        {
            //TODO display name and sell price
        }

        @Override
        public boolean mouseDragged(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom) 
        {
            // TODO mouse drag this entity
            return plantable.mouseDragged(mouseRectangle, camRectangle, xZoom, yZoom);
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
            return false;
        }
    }
}
