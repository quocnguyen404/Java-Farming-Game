package game;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.lang.Runnable;

import javax.swing.JFrame;

import engine.*;
import game.component.Component;
import game.component.FarmingSystem;
import game.component.ShopingSystem;
import game.data.ConfigDataHelper;
import game.data.Sprites.SpriteID;

public class GameFrame extends JFrame implements Runnable
{
    //game zoom
    public static int X_ZOOM = 3;
    public static int Y_ZOOM = 3;

    private Canvas canvas;
    private RenderHandler renderer;

    //GameObject array
    // private GameObject[] gameObjects;
    //Component array
    private Component[] components;
    private MouseIndicator mouseIndicator;
    private TagIndicator tagIndicator;

    //animated test
    // private AnimatedSprite testAnim;
    // private AnimatedSprite testAnim1;

    //KeyboardListener
    private KeyboardListener keyboardListener = new KeyboardListener(this);
    //MouseEventListener
    private MouseEventListener mouseEventListener = new MouseEventListener(this);
    //Background color
    private Rectangle background;

    //constructor
    public GameFrame()
    { 
        //create canvas
        canvas = new Canvas();
        //frame set up
        setTitle(GameConstant.TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, GameConstant.WIN_WIDTH, GameConstant.WIN_HEIGHT);
        //set frame in the middle screen
        setLocationRelativeTo(null);

        //add canvas to content(container) page
        add(canvas);
        setVisible(true);
        
        //create canvas bufferStrategy
        canvas.createBufferStrategy(3);
        //initialize renderer
        renderer = new RenderHandler(getWidth(), getHeight());

        //init background
        background = new Rectangle(0, 0, getWidth(), getHeight());
        background.generateGraphics(GameConstant.BACKGROUND_COLOR);
        
        // //load game object for update and render
        // gameObjects = new GameObject[1];
        // player = new Player();
        // gameObjects[0] = player;

        //animated test
        // Sprite[] onion = ConfigDataHelper.getInstance().getAnimtedSprite(AnimationID.ONION);
        // Sprite[] potato = ConfigDataHelper.getInstance().getAnimtedSprite(AnimationID.POTATO);
        // testAnim = new AnimatedSprite(onion, 60);
        // testAnim1 = new AnimatedSprite(potato, 60);

        
        //indicator                
        mouseIndicator = new MouseIndicator(null);
        tagIndicator = new TagIndicator(new Rectangle(0, 0, GameConstant.WIN_WIDTH - GameConstant.TILE_WIDTH, GameConstant.TILE_HEIGHT*Y_ZOOM));
        // tagIndicator.setMessage("Hello");
        tagIndicator.setGold(ConfigDataHelper.getInstance().getPlayerGold());
        
        FarmingSystem.onPlantedSeed = mouseIndicator::getData;
        ShopingSystem.onSellCrop = mouseIndicator::getData;
        ShopingSystem.onBuySeed = (p) ->
        {
            mouseIndicator.setSprite(SpriteID.valueOf(p.getName()));
            mouseIndicator.setData(p);
        };

        ShopingSystem.onHoverSeed = (p) -> 
        { 
            String message = p.getName().toLowerCase().replaceAll("_", " ");
            tagIndicator.setMessage(message);
            tagIndicator.setGold(p.getBuyPrice());
        };
        
        components = new Component[2];
        components[0] = new FarmingSystem(new Rectangle(), GameConstant.TILE_HEIGHT*Y_ZOOM);
        components[1] = new ShopingSystem(new Rectangle(0, GameConstant.TILE_HEIGHT*Y_ZOOM+1, 0, 0), GameConstant.TILE_HEIGHT*Y_ZOOM+1);

        mouseRect.generateGraphics(1, 0xFFFFFF);

        //set up canvas
        canvas.addKeyListener(keyboardListener);
        canvas.addFocusListener(keyboardListener);
        canvas.addMouseListener(mouseEventListener);
        canvas.addMouseMotionListener(mouseEventListener);
    }

    //update function run 60 time per second
    public void update()
    {
        // for (GameObject obj : gameObjects)
        //     obj.update(this);
        // mouseIndicator.update(this);

        for (Component component : components)
            component.update(this);
        

        // testAnim.update(this);
        // testAnim1.update(this);
    }

    public void render()
    {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();

        renderer.renderRectangle(background, 1, 1, true);

        // for (GameObject obj : gameObjects)
        //     obj.render(renderer, X_ZOOM, Y_ZOOM);

        for (Component component : components) 
            component.render(renderer, X_ZOOM, Y_ZOOM);
        
        //test sprite
        // int count = 0;
        // for (SpriteID spriteID : SpriteID.values()) 
        // {
        //     if (spriteID != SpriteID.REGION)
        //         renderer.renderSprite(spriteID, GameConstant.TILE_WIDTH*count*X_ZOOM, GameConstant.TILE_HEIGHT, X_ZOOM, Y_ZOOM, false);
        //     count++;
        // }

        tagIndicator.render(renderer, X_ZOOM, Y_ZOOM);
        mouseIndicator.render(renderer, X_ZOOM, Y_ZOOM);
        renderer.renderRectangle(mouseRect, X_ZOOM, Y_ZOOM, false);
        super.paint(graphics);
        renderer.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
        renderer.clear();
    }

    public void handleCTRL(boolean[] keys)
    {
        if (keys[KeyEvent.VK_S])
        {
        }
    }

    private Rectangle mouseRect = new Rectangle(0, 0, 1, 1);
    //left mouse pressed
    public void leftMousePressed(int x, int y)
    {
        boolean clicked = false;
        // Rectangle mouseRect = new Rectangle(x, y, 1, 1);
        mouseRect.setPosition(x, y);
        System.out.println("Mouse click pos x:" + x + " y:" + y);

        for (Component component : components) 
            clicked = component.leftMouseClick(mouseRect, renderer.getCamera(), x, y);

            //is clicked on ui
        if (clicked)
            return;

    }

    //right mouse pressed
    public void rightMousePressed(int x, int y)
    {
        
    }

    public void mouseMoved(int x, int y)
    {
        mouseRect.setPosition(x, y);
        for (Component component : components) component.mouseHover(mouseRect, renderer.getCamera(), X_ZOOM, Y_ZOOM);
    }

    public void mouseDragged(int x, int y)
    {
        // mouseIndicator.setPosition(x, y);
        mouseRect.setPosition(x, y);
        mouseIndicator.setPosition(x, y);
    }

    public void mouseDraggedExit(int x, int y)
    {
        System.out.println("Mouse drag exit pos x:" + x + " y:" + y);
        mouseRect.setPosition(x, y);
        boolean successBuy = false;
        for (Component component : components)
        {
            successBuy = component.mouseDraggedExit(mouseRect, renderer.getCamera(), X_ZOOM, Y_ZOOM);
            if(successBuy) break;
        }
        mouseIndicator.releaseMouse(successBuy);
    }


    //render everything
   

    public KeyboardListener getKeyboardListener()
    {
        return keyboardListener;
    }

    public MouseEventListener getMouseEventListener() 
    {
        return mouseEventListener;
    }

    public RenderHandler getRender()
    {
        return renderer;
    }
    
    //run function of Runnable interface
    @Override
    public void run() 
    {
        long lastTime = System.nanoTime();
        double nanoSecondConversion = 1_000_000_000.0/60;
        double deltaTime = 0;

        while (true)
        {
            long currentTime = System.nanoTime();

            deltaTime += (currentTime - lastTime)/nanoSecondConversion;
            // System.out.println(deltaTime);`
            
            while (deltaTime >= 1) //1 is 1 seconds
            {
                update();
                deltaTime--;
            }
            render();

            lastTime = currentTime;
        }
    }


}
