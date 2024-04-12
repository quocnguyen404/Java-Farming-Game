package engine;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import game.GameFrame;

public class MouseEventListener implements MouseListener, MouseMotionListener
{
    private GameFrame game;
    private boolean mouseDragged = false;

    public MouseEventListener(GameFrame game)
    {
        this.game = game;
    }

    @Override
    public void mouseDragged(MouseEvent e) 
    {
        mouseDragged = true;
        game.mouseDragged(e.getX(), e.getY());
    }

    @Override
    public void mouseMoved(MouseEvent e) 
    {
        game.mouseMoved(e.getX(), e.getY());
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

        if (mouseDragged == true)
        {
            game.mouseDraggedExit(e.getX(), e.getY());
        }

        mouseDragged = false;
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
