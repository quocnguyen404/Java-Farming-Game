package engine;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import game.GameFrame;

public class MouseEventListener implements MouseListener, MouseMotionListener
{
    private GameFrame game;

    public MouseEventListener(GameFrame game)
    {
        this.game = game;
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {
    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {

    }

    @Override
    public void mousePressed(MouseEvent e) 
    {
        
        //left mouse click
        if (e.getButton() == MouseEvent.BUTTON1)
        {
            game.leftMousePressed(e.getX(), e.getY());
        }

        if (e.getButton() == MouseEvent.BUTTON3)
        {
            game.rightMousePressed(e.getX(), e.getY());
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) 
    {
    }

    @Override
    public void mouseEntered(MouseEvent e) 
    {
    }

    @Override
    public void mouseExited(MouseEvent e) 
    {
    }
    
}
