package engine;

import game.GameFrame;

public class GUI implements HandleMouseClick, GameObject 
{
    private Sprite bgSprite;
    private GUIButton[] buttons;
    private Rectangle rect = new Rectangle();
    private boolean fixed;
    private boolean visible;

    public GUI()
    {
        this (null, 0, 0, true, true);
    }

    public GUI(GUIButton[] buttons, int x, int y, boolean fixed, boolean visible)
    {
        this(null, buttons, x, y, fixed, visible);
    }

    public GUI(Sprite bgSprite, GUIButton[] buttons, int x, int y, boolean fixed, boolean visible)
    {
        this.bgSprite = bgSprite;
        this.buttons = buttons;
        this.fixed = fixed;
        this.visible = visible;
        rect.setPosition(x, y);

        if (bgSprite != null)
        {
            rect.w = bgSprite.getWidth();
            rect.h = bgSprite.getHeight();
        }
    }

    public void setVisibility(boolean value)
    {
        visible = value;
    }

    public boolean getVisibility()
    {
        return visible;
    }

    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) 
    {
        if (!fixed) 
            mouseRectangle = new Rectangle(mouseRectangle.x + camera.x , mouseRectangle.y + camera.y, 1, 1);

        if (rect.w == 0 || rect.h == 0 || mouseRectangle.intersects(rect))
        {
            // mouseRectangle.x += rect.x;
            // mouseRectangle.y += rect.y;
            for (GUIButton guiButton : buttons)
                if (guiButton.handleMouseClick(mouseRectangle, camera, xZoom, yZoom))
                    return true;
        }
        return false;
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) 
    {
        if (!visible)
            return;
            
        if (bgSprite != null)
            renderer.renderSprite(bgSprite, rect.x, rect.y, xZoom, yZoom, fixed);
     
        if (buttons != null)
        {
            for (int i = 0; i < buttons.length; i++)
                buttons[i].render(renderer, xZoom, yZoom, rect);
        }

    }

    @Override
    public void update(GameFrame game) 
    {
    }
}
