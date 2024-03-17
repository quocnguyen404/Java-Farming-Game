public interface GameObject 
{
    //call every time possible
    public void render(RenderHandler renderer, int xZoom, int yZoom);
    
    //call at 60 fps rate
    public void update(GameFrame game);
}
