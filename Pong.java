package Pong2;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong extends Applet implements Runnable, KeyListener{
    final int WIDTH = 700;
    final int HEIGHT = 500;
    Thread thread;
    
    public void init(){
        this.resize(WIDTH, HEIGHT);
        this.addKeyListener(this);
        thread = new Thread(this);
        thread.start();
    }
    public void paint(Graphics g){
      g.setColor(Color.black);
      g.fillRect(0 ,0, WIDTH, HEIGHT);
    }
    
    public void update(Graphics g){
        paint(g);
    }

    public void run() {
        for(;;){
            repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){
            
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            
        }
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_UP){

        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){

        }
    }
    
}//pong