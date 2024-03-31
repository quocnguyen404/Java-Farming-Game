package engine;
public interface HandleMouseClick 
{
    //Call whenever mouse click on canvas
    //Return true when clicked on something
    public boolean leftMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom);    
    public boolean rightMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom);
}
