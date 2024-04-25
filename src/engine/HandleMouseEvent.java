package engine;
public interface HandleMouseEvent 
{
    //Call whenever mouse click on canvas
    //Return true when clicked on something
    public boolean leftMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom);    
    public boolean rightMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom);

    public boolean mouseMoved(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom);
    public boolean mouseDragged(Rectangle mouseRectangle, Rectangle camRectangle, int xZoom, int yZoom);
    public boolean mouseDraggedExit(Rectangle mousRectangle, Rectangle camRectangle, int xZoom, int yZoom);
}
