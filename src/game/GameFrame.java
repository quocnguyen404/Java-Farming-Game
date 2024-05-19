package game;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;
import java.lang.Runnable;
import java.util.function.Consumer;

import javax.swing.JFrame;

import engine.*;
import game.component.Component;
import game.component.FarmingSystem;
import game.component.PlantableManager;
import game.component.SellingCell;
import game.component.ShopingSystem;
import game.data.ConfigDataHelper;
import game.plantable.crop.Crop;

public class GameFrame extends JFrame implements Runnable
{
    //game zoom/scale
    public static int X_ZOOM = 3;
    public static int Y_ZOOM = 3;

    private Canvas canvas;
    private RenderHandler renderer;
    //Component array
    private Component[] components;
    private MouseIndicator mouseIndicator;
    private TagIndicator tagIndicator;
    //KeyboardListener
    private KeyboardListener keyboardListener = new KeyboardListener(this);
    //MouseEventListener
    private MouseEventListener mouseEventListener = new MouseEventListener(this);
    //Background color
    private Rectangle background;

    public static Consumer<Crop> onGetGrowPlant;
    public static Consumer<Crop> onSellingCrop;
    public static Consumer<String> onSetMessage;
    public static Runnable defaultMessage;
    //constructor
    public GameFrame()
    { 
        //create canvas
        canvas = new Canvas();
        //frame set up
        setTitle(GameConstant.TITLE);
        // setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
        
        //indicator                
        mouseIndicator = new MouseIndicator(null);
        onSellingCrop = mouseIndicator::sellingCrop;
        tagIndicator = new TagIndicator(new Rectangle(0, 0, GameConstant.WIN_WIDTH - GameConstant.TILE_WIDTH, GameConstant.TILE_HEIGHT*Y_ZOOM));
        // tagIndicator.setMessage("Hello");
        defaultMessage = () ->
        {
            tagIndicator.setGold(ConfigDataHelper.getInstance().getPlayerGold());
            tagIndicator.setMessage("");
        };
        onSetMessage = tagIndicator::setMessage;
        
        //initialize component
        FarmingSystem.onPlantedSeed = mouseIndicator::getData;
        ShopingSystem.onBuySeed = (p) ->
        {
            mouseIndicator.buyingCrop(p);
        };

        ShopingSystem.onHoverSeed = (p) -> 
        { 
            String message = p.getName().toLowerCase().replaceAll("_", " ");
            tagIndicator.setMessage(message);
            tagIndicator.setGold(p.getBuyPrice());
        };

        PlantableManager plantableManager = new PlantableManager(null, 0);
        onGetGrowPlant = plantableManager::addPlantable;

        SellingCell.onGetSellingModify = mouseIndicator::isSelling;
        SellingCell.onSellingCrop = () -> 
        {
            Crop crop = mouseIndicator.sellCrop();
            plantableManager.removeCrop(crop);
            ConfigDataHelper.getInstance().sellingCrop(crop);
        };

        FarmingSystem farmingSystem = new FarmingSystem(new Rectangle(), GameConstant.TILE_HEIGHT*Y_ZOOM);

        components = new Component[3];
        components[0] = farmingSystem;
        components[1] = new ShopingSystem(new Rectangle(0, GameConstant.TILE_HEIGHT*Y_ZOOM+1, 0, 0), GameConstant.TILE_HEIGHT*Y_ZOOM+1);
        components[2] = plantableManager;
        mouseRect.generateGraphics(1, 0xFFFFFF);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e)
            {
                run = false;
                ConfigDataHelper.getInstance().savePlayerData();
                ConfigDataHelper.getInstance().saveRegions();;

                dispose();
                System.exit(0);
            }
        });

        //run default setting
        defaultMessage.run();

        //set up canvas
        canvas.addKeyListener(keyboardListener);
        canvas.addFocusListener(keyboardListener);
        canvas.addMouseListener(mouseEventListener);
        canvas.addMouseMotionListener(mouseEventListener);
    }

    //update function run 60 time per second
    public void update()
    {
        for (Component component : components)
            component.update(this);
    }

    public void render()
    {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();

        renderer.renderRectangle(background, 1, 1, true);

        for (Component component : components) 
            component.render(renderer, X_ZOOM, Y_ZOOM);

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
        boolean isHover = false;
        mouseRect.setPosition(x, y);
        for (Component component : components) 
        {
            isHover = component.mouseHover(mouseRect, renderer.getCamera(), X_ZOOM, Y_ZOOM);
            if(isHover) break;
        }
    }

    public void mouseDragged(int x, int y)
    {
        mouseRect.setPosition(x, y);
        mouseIndicator.setPosition(x, y);

        for (Component component : components) 
            component.mouseDragged(mouseRect, background, x, y);
            // if(isDragged) break;
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
    private boolean run = true;
    @Override
    public void run() 
    {
        long lastTime = System.nanoTime();
        double nanoSecondConversion = 1_000_000_000.0/60;
        double deltaTime = 0;

        while (run)
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
