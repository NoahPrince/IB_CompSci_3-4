package Pong;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong extends Applet implements Runnable, KeyListener{
    final int width = 700;
    final int height = 500;
    Thread thread;
    HumanPaddle p1;
    
    public void init(){
        this.resize(width, height);
        this.addKeyListener(this);
        p1 = new HumanPaddle(1);
        thread = new Thread(this);
        thread.start();
    }
    public void paint(Graphics g){
      g.setColor(Color.black);
      g.fillRect(0 ,0, width, height);
      p1.draw(g);
    }
    
    public void update(Graphics g){
        paint(g);
    }

    public void run() {
        for(;;){
            
            p1.move();
            
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
            p1.setUpAccel(true);  
            System.out.println("up");
        }
        else if(e.getKeyCode() == KeyEvent.VK_DOWN){
            p1.setDownAccel(true);
            System.out.println("down");
        }
        
    }
