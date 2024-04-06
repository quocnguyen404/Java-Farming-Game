package game;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.lang.Runnable;

import javax.swing.JFrame;

import engine.*;
import game.component.Component;

public class GameFrame extends JFrame implements Runnable
{
    //game zoom
    public static int X_ZOOM = 3;
    public static int Y_ZOOM = 3;

    private Canvas canvas;
    private RenderHandler renderer;

    private Map map;
    //GameObject array
    // private GameObject[] gameObjects;
    //Component array
    private Component[] components;
    //Player
    // private Player player;

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
        //initialize map
        map = new Map();
        
        // //load game object for update and render
        // gameObjects = new GameObject[1];
        // player = new Player();
        // gameObjects[0] = player;


        //initialize building system

        components = new Component[0];

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
    }

    public void handleCTRL(boolean[] keys)
    {
        if (keys[KeyEvent.VK_S])
        {
        }
    }

    //left mouse pressed
    public void leftMousePressed(int x, int y)
    {
        boolean clicked = false;
        Rectangle mouseRect = new Rectangle(x, y, 1, 1);

        for (Component component : components) 
            clicked = component.leftMouseClick(mouseRect, renderer.getCamera(), x, y);

            //is clicked on ui
        if (clicked)
            return;

        map.leftMouseClick(mouseRect, renderer.getCamera(), x, y);
    }

    //right mouse pressed
    public void rightMousePressed(int x, int y)
    {

    }

    public void mouseDragged(int x, int y)
    {

    }

    public void mouseDraggedExit(int x, int y)
    {

    }


    //render everything
    public void render()
    {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        super.paint(graphics);

        renderer.renderRectangle(background, 1, 1, true);

        map.render(renderer, X_ZOOM, Y_ZOOM);

        // for (GameObject obj : gameObjects)
        //     obj.render(renderer, X_ZOOM, Y_ZOOM);

        for (Component component : components) 
            component.render(renderer, X_ZOOM, Y_ZOOM);    
        
        renderer.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
        renderer.clear();
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
