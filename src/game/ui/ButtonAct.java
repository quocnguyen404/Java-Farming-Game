package game.ui;

import engine.Rectangle;
import game.data.Sprites.SpriteID;

public class ButtonAct extends SDKButton
{
    private Runnable onActivate;
    public ButtonAct(SpriteID spriteID, Rectangle rect, Runnable onActivate, boolean genRect)
    {
        super(spriteID, rect, genRect);
        this.onActivate = onActivate;
    }

    @Override
    public void activate() 
    {
        onActivate.run();
    }
}
