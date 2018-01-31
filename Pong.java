package Pong;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong extends Applet implements Runnable, KeyListener{
    final int width = 700;
    final int height = 500;
    Thread thread;
    Player1 p1;
    Player2 p2;
    
    public void init(){
        this.resize(width, height);
        this.addKeyListener(this);
        Frame frame = (Frame)this.getParent().getParent();
        frame.setTitle("Pong");

            frame.setMenuBar(null);
            frame.pack();
       
        p1 = new Player1(1);
        p2 = new Player2(2);
        thread = new Thread(this);
        thread.start();
    }//init
    
    public void paint(Graphics g){
      g.setColor(Color.black);
      g.fillRect(0 ,0, width, height);
      p1.draw(g);
      p2.draw(g);
    }//paint
    
    public void update(Graphics g){
        paint(g);
    }//update

    public void run() {
        for(;;){
            
            p1.move();
            p2.move();
            repaint();
            
            try {
                Thread.sleep(10);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }//catch
        }//for
    }//run

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            p1.setUpAccel(true);  
        }//if
        else if(e.getKeyCode() == KeyEvent.VK_S){
            p1.setDownAccel(true);
        }//else if
        if(e.getKeyCode() == KeyEvent.VK_UP){
            p2.setUpAccel(true);
        }//if
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            p2.setDownAccel(true);
        }//else if
    }//keyPressed

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W){
            p1.setUpAccel(false);   
        }//if
        else if(e.getKeyCode() == KeyEvent.VK_S){
            p1.setDownAccel(false);
        }//else if
        if(e.getKeyCode() == KeyEvent.VK_UP){
            p2.setUpAccel(false);
        }//if
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            p2.setDownAccel(false);
        }//else if
    }//keyReleased
  
    @Override
    public void keyTyped(KeyEvent e) {
        
    }//keyTyped
}//pong
