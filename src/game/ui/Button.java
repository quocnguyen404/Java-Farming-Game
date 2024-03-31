package game.ui;

import java.util.function.Consumer;

import engine.Rectangle;
import engine.Sprite;

public class Button<T> extends SDKButton
{
    private Consumer<T> onActivate;
    private T data;

    public Button(Sprite sprite, Rectangle rect, T data, Consumer<T> onActivate, boolean genRect) 
    {
        super(sprite, rect, genRect);
        this.data = data;
        this.onActivate = onActivate;
    }


    @Override
    public void activate() 
    {
        onActivate.accept(data);
        System.out.println("On activate: " + data);
    }
    
}
