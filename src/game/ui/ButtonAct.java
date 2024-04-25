package game.ui;

import engine.GUIButton;
import engine.Rectangle;
import game.data.Sprites.SpriteID;

public class ButtonAct extends GUIButton
{
    private Runnable onActivate;
    public ButtonAct(SpriteID spriteID, Rectangle rect, Runnable onActivate, boolean genRect)
    {
        super(spriteID, rect, true, genRect);
        this.onActivate = onActivate;
    }

    @Override
    public void activate() 
    {
        onActivate.run();
    }

    @Override
    public void hover() 
    {
        //TODO
        throw new UnsupportedOperationException("Unimplemented method 'hover'");
    }
}
