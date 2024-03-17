public class App 
{   
    public static void main(String[] args) throws Exception 
    {
        // EventQueue.invokeLater(() ->
        // {
        //     JFrame frame = new GameFrame();
        //     Container contentPage = frame.getContentPane();
        //     TextComponent txt = new TextComponent("Hello world!");
        //     contentPage.add(txt);
        // });
        //4:42:16 / 7:20:19

        GameFrame game = new GameFrame();
        Thread gameThread = new Thread(game);
        gameThread.start();
    }
}
