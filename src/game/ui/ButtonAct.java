package game.ui;

import engine.GUIButton;
import engine.Rectangle;
import game.data.Sprites.SpriteID;

public class ButtonAct extends GUIButton
{
    private Runnable onClick;
    private Runnable onHover;
    private Runnable onExitHover;
    
    public ButtonAct(SpriteID spriteID, Rectangle rect, boolean genRect)
    {
        super(spriteID, rect, true, genRect);
    }

    public void addClickListener(Runnable onClick)
    {
        this.onClick = onClick; 
    }

    public void addHoverListener(Runnable onHover)
    {
        this.onHover = onHover;
    }

    public void addExitHover(Runnable onExitHover)
    {
        this.onExitHover = onExitHover;
    }

    @Override
    public void activate() 
    {
        if (onClick == null) return;
        onClick.run();
        System.out.println("On click button");
    }

    @Override
    public void hover() 
    {
        if (onHover == null) return;
        onHover.run();
        System.out.println("On hover button");
    }

    @Override
    public void existHover() 
    {
        if(onExitHover == null) return;
        onExitHover.run();
        System.out.println("On exit hover button");
    }
}
