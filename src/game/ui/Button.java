package game.ui;

import java.util.function.Consumer;

import engine.Rectangle;
import game.data.Sprites.SpriteID;

public class Button<T> extends SDKButton
{
    private Consumer<T> onActivate;
    private T data;

    public Button(SpriteID spriteID, Rectangle rect, T data, Consumer<T> onActivate, boolean genRect) 
    {
        super(spriteID, rect, genRect);
        this.data = data;
        this.onActivate = onActivate;
    }


    @Override
    public void activate() 
    {
        onActivate.accept(data);
        System.out.println("On activate: " + data);
    }
    // @Override
    // public void dragActivate() 
    // {
    //     onActivate.accept(data);
    //     System.out.println("On drag activate: " + data);
    // }
}
