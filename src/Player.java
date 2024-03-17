public class Player implements GameObject
{
    Rectangle playRectangle;
    int speed = 4;

    public Player()
    {
        playRectangle = new Rectangle(0, 0, 16, 16);
        playRectangle.generateGraphics(3, 0xFF00FF90);
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        renderer.renderRectangle(playRectangle, xZoom, yZoom);
    }

    @Override
    public void update(GameFrame game) 
    {
        KeyboardListener keyboardListener = game.getKeyboardListener();
        if (keyboardListener.up())
            playRectangle.y -= speed;
        if (keyboardListener.down())
            playRectangle.y += speed;
        if (keyboardListener.right())
            playRectangle.x += speed;
        if (keyboardListener.left())
            playRectangle.x -= speed;
        updateCamera(game.getRender().getCamera());
    }

    public void updateCamera(Rectangle camera)
    {
        camera.x = playRectangle.x + playRectangle.w - (camera.w/2); 
        camera.y = playRectangle.y + playRectangle.h - (camera.h/2);
    }
}
