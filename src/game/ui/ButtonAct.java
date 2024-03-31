package game.ui;

import engine.Rectangle;
import engine.Sprite;

public class ButtonAct extends SDKButton
{
    private Runnable onActivate;
    public ButtonAct(Sprite sprite, Rectangle rect, Runnable onActivate, boolean genRect)
    {
        super(sprite, rect, genRect);
        this.onActivate = onActivate;
    }

    @Override
    public void activate() 
    {
        onActivate.run();
    }
}
