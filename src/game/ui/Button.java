package game.ui;

import java.util.function.Consumer;

import engine.GUIButton;
import engine.Rectangle;
import game.data.Sprites.SpriteID;

public class Button<T> extends GUIButton
{
    private Consumer<T> onClick = null;
    private Consumer<T> onHover = null;
    private T data;

    public Button(SpriteID spriteID, Rectangle rect, T data, boolean genRect) 
    {
        super(spriteID, rect, true, genRect);
        this.data = data;
    }

    public void addClickListener(Consumer<T> onClick)
    {
        this.onClick = onClick;
    }

    public void addHoverListener(Consumer<T> onHover)
    {
        this.onHover = onHover;
    }


    @Override
    public void activate() 
    {
        if (onClick == null) return;
        onClick.accept(data);
        System.out.println("On activate: " + data);
    }

    @Override
    public void hover() 
    {
        if (onHover == null) return;
        onHover.accept(data);
        System.out.println("On hover: " + data);
    }
}
