// package game;
// import engine.AnimatedSprite;
// import engine.GameObject;
// import engine.KeyboardListener;
// import engine.Rectangle;
// import engine.RenderHandler;
// import engine.Sprite;
// import engine.SpriteSheet;

// public class Player implements GameObject
// {
//     public enum Direction
//     {
//         RIGHT,
//         LEFT,
//         UP,
//         DOWN,
//     }

//     private Rectangle playRectangle;
//     private Sprite sprite;
//     private AnimatedSprite animatedSprite = null;
//     private int speed = 4;
//     private Direction direction = Direction.DOWN;
//     private int animatedSpeed = 5;

//     private boolean didMove;

//     public Player(Sprite sprite)
//     {
//         //player animated
//         this.sprite = sprite;
//         playRectangle = new Rectangle(0, 0, 16, 16);
//         playRectangle.generateGraphics(3, 0xFF00FF90);
//     }

//     public Player()
//     {
//         //player animated
//         SpriteSheet playerSheet = new SpriteSheet(Helper.loadImage("Player.png"));
//         playerSheet.loadSprite(20, 26);
//         animatedSprite = new AnimatedSprite(playerSheet, animatedSpeed);
//         playRectangle = new Rectangle(0, 0, 20, 26);
        
//         //rectangle for debug
//         // playRectangle.generateGraphics(3, 0xFF00FF90);
//     }

//     private void updateAnimatedSprite(GameFrame game)
//     {
//         if (animatedSprite != null)
//         {
//             animatedSprite.setAnimationRange(direction.ordinal()*8, (direction.ordinal()*8)+7);
//             if (didMove) animatedSprite.update(game);
//             else animatedSprite.reset();
//         }
//     }
    
//     public void updateCamera(Rectangle camera)
//     {
//         camera.x = playRectangle.x + playRectangle.w - (camera.w/2);
//         camera.y = playRectangle.y + playRectangle.h - (camera.h/2);
//     }

//     private void moving(KeyboardListener keyboardListener)
//     {
//         didMove = false;

//         if (keyboardListener.left())
//         {
//             playRectangle.x -= speed;
//             direction = Direction.LEFT;
//             didMove = true;
//         }

//         if (keyboardListener.right())
//         {
//             playRectangle.x += speed;
//             direction = Direction.RIGHT;
//             didMove = true;
//         }

//         if (keyboardListener.up())
//         {
//             playRectangle.y -= speed;
//             direction = Direction.UP;
//             didMove = true;
//         }
        
//         if (keyboardListener.down())
//         {
//             playRectangle.y += speed;
//             direction = Direction.DOWN;
//             didMove = true;
//         }
//     }

//     @Override
//     public void render(RenderHandler renderer, int xZoom, int yZoom) 
//     {
//         if (animatedSprite != null) renderer.renderSprite(animatedSprite, playRectangle.x, playRectangle.y, xZoom, yZoom, false);
//         else if (sprite != null) renderer.renderSprite(sprite, playRectangle.x, playRectangle.y, xZoom, yZoom, false);
//         else renderer.renderRectangle(playRectangle, xZoom, yZoom, false);
//     }
    
//     @Override
//     public void update(GameFrame game) 
//     {
//         moving(game.getKeyboardListener());
//         updateAnimatedSprite(game);
//         updateCamera(game.getRender().getCamera());
//     }
// }
