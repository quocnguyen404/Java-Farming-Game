import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.Runnable;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class GameFrame extends JFrame implements Runnable
{
    public static final int alpha = 0xFFFF00DC;
    public static final String sheetImagePath = "spritesheet.png";
    public static final String tilesPath = "Tiles.txt";
    public static final String mapPath = "Map.txt";
    public static final String title = "Best game ever";
    
    public static int X_ZOOM = 2;
    public static int Y_ZOOM = 2;
    // public static int NUMX_TILE = 17;
    // public static int NUMY_TILE = 17;
    public int winWidth = 500; //X_ZOOM * Tiles.TILE_WIDTH * NUMX_TILE;
    public int winHeigth = 500; //Y_ZOOM * Tiles.TILE_HEIGHT * NUMY_TILE;

    private Canvas canvas;
    private RenderHandler renderer;
    private Tiles tiles;
    private Map map;
    private SpriteSheet sheet;
    private BufferedImage sheetImage;
    //GameObject array
    private GameObject gameObject[];

    //KeyboardListener
    private KeyboardListener keyboardListener = new KeyboardListener(this);
    //MouseEventListener
    private MouseEventListener mouseEventListener = new MouseEventListener(this);

    //constructor
    public GameFrame()
    { 
        //create canvas
        canvas = new Canvas();
        //frame set up
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, winWidth, winHeigth);
        //set frame in the middle screen
        setLocationRelativeTo(null);

        //pack frame size to fit content
        //pack();
        //set full screen
        //setExtendedState(Frame.MAXIMIZED_BOTH);
        
        //add canvas to content(container) page
        add(canvas);
        //set frame visibility
        setVisible(true);
        
        //set up canvas
        //add canvas listener
        //create canvas bufferStrategy
        canvas.createBufferStrategy(3);

        //initialize renderer
        renderer = new RenderHandler(getWidth(), getHeight());
    
        //load sprite sheet image
        sheetImage = loadImage(sheetImagePath);

        //create sprite sheet
        sheet = new SpriteSheet(sheetImage);
        sheet.loadSprite(Tiles.TILE_WIDTH, Tiles.TILE_HEIGHT);
        //create tiles
        //sprite sheet must to load before create tiles
        tiles = new Tiles(new File("tiles.txt"), sheet);
        map = new Map(new File(mapPath), tiles);

        //load object
        gameObject = new GameObject[1];
        Player player = new Player();
        gameObject[0] = player;
    
        canvas.addKeyListener(keyboardListener);
        canvas.addFocusListener(keyboardListener);
        canvas.addMouseListener(mouseEventListener);
        canvas.addMouseMotionListener(mouseEventListener);
    }

    //update function run 60 time per second
    public void update()
    {
        for (GameObject obj : gameObject)
            obj.update(this);    
    }

    public void handleCTRL(boolean[] keys)
    {
        if (keys[KeyEvent.VK_S])
        {
            map.saveMap();
        }
    }

    //mouse pressed
    public void leftMousePressed(int x, int y)
    {
        x = (int)Math.floor(((double)x + renderer.getCamera().x)/(Tiles.TILE_WIDTH * X_ZOOM));
        y = (int)Math.floor(((double)y + renderer.getCamera().y)/(Tiles.TILE_HEIGHT * Y_ZOOM));
        map.setTile(x, y, Tiles.TileID.DIRT1);
    }

    //loadImage
    private BufferedImage loadImage(String path)
    {
        try 
        {
            BufferedImage loadedImage = ImageIO.read(GameFrame.class.getResource(path));
            BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);
            return formattedImage;
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            // throw new NullPointerException("Image Null");
            return null;
        }
    }

    //render everything
    public void render()
    {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        super.paint(graphics);

        map.render(renderer, X_ZOOM, Y_ZOOM);
        
        for (GameObject obj : gameObject)
            obj.render(renderer, X_ZOOM, Y_ZOOM);    
        
        renderer.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
        renderer.clear();
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
}
