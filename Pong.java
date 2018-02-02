package Pong;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong extends Applet implements Runnable, KeyListener{
    //sets up default variables for the pong class
    final int width = 700;
    final int height = 500;
    int p1Score;
    int p2Score;
    boolean gameTitle;
    boolean gameStarted;
    boolean gameEnd;
    Font regFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
    Font titleFont = new Font(Font.SANS_SERIF, Font.PLAIN, 35);
    Graphics gfx;
    Image img;
    Thread thread;
    Player1 p1;
    Player2 p2;
    Ball b;
    
    
    public void init(){//assigns values to the variables
        this.resize(width, height);
        this.addKeyListener(this);
        gameTitle = false;
        gameStarted = false;
        gameEnd = false;
        Frame frame = (Frame)this.getParent().getParent();
        frame.setTitle("Pong");
        frame.setMenuBar(null);
        frame.pack();
       
        p1 = new Player1(1);
        p2 = new Player2(2);
        b = new Ball();
        img = createImage(width, height);//used to load everything into a seperate image to prevent glitches in the game running
        gfx = img.getGraphics();
        thread = new Thread(this);
        thread.start();
    }//init
    
    public void paint(Graphics g){
        gfx.setColor(Color.black);
        gfx.fillRect(0 ,0, width, height);
        if(b.getX() < -10){//sets player 2 score
            p2Score++;
            gfx.setColor(Color.white);
        }//if
        if(b.getX() > 710){//sets player 1 score
            p1Score++;
            gfx.setColor(Color.white);
        }//if
        //drawing paddles, ball, and player scores
        p1.draw(gfx);
        p2.draw(gfx);
        b.draw(gfx);
        gfx.setFont(regFont);
        gfx.drawString(Integer.toString(p2Score), 620, 60);
        gfx.drawString(Integer.toString(p1Score), 80, 60);
        
        if(!gameTitle){//when you first launch the game, set up a title screen
            gfx.setColor(Color.black);
            gfx.fillRect(0, 0, width, height);
            gfx.setColor(Color.white);
            gfx.setFont(titleFont);
            gfx.drawString("PONG", 310, 100);
            gfx.setFont(regFont);
            gfx.drawString("By Noah Prince", 290, 130);
            gfx.drawString("Games are Played to 11 Points", 218, 230);
            gfx.drawString("First one to 11 Points Wins!", 232, 252);
            gfx.drawString("Press Enter To Begin", 263, 350);
        }//if
        
        if(gameTitle){//once gameTitle is true, and the title screen in exited, display player scores
            gfx.drawString(Integer.toString(p2Score), 620, 60);
            gfx.drawString(Integer.toString(p1Score), 80, 60);
        }//if
        
        if(gameTitle == true && gameStarted == false){//when the gameTitle is true, and the start of the game isn't true, display "Press Space to Start"
            gfx.setFont(regFont);
            gfx.drawString("Press Space To Start", 260, 200);
            if(p1Score == 10){//when player 1 score gets to 10, display match point
                gfx.setFont(regFont);
                gfx.setColor(Color.white);
                gfx.drawString("Player 1 Match Point", 265, 150);
            }//if
            if(p2Score == 10){//when player 2 score gets to 10, display match point
                gfx.setFont(regFont);
                gfx.setColor(Color.white);
                gfx.drawString("Player 2 Match Point", 265, 150);
            }//if
        }//if
        
        if(p1Score == 11){//if player 1 score equals 11, display winning screen
            gfx.setFont(titleFont);
            gfx.setColor(Color.red);
            gfx.drawString("Player 1 Wins!", 245, 150);
        }//if
        if(p2Score == 11){//if player 2 score equals 11, display winning screen
            gfx.setFont(titleFont);
            gfx.setColor(Color.red);
            gfx.drawString("Player 2 Wins!", 245, 150);
        }//if
        g.drawImage(img, 0, 0, this);//displays image created, without previous glitches
    }//paint
    
    public void update(Graphics g){
        paint(g);
    }//update

    public void run() {
        for(;;){//creating an infinite loop to continue running once called on
            if(gameStarted){//if the game is started, allow all pieces, ball, and paddles, to move
                p1.move();
                p2.move();
                b.move();
                b.checkCollision(p1, p2);//checks if there are any collisions with the ball and the paddles
            }//if
            if(b.getX() < -10){//if the ball goes outside the x limitations on player 1 side, reset the paddles and balls, reset gameStarted to false, and add a score to player 2
                p1.reset();
                p2.reset();
                b.reset();
                p2Score++;
                gameStarted = false;
            }//if
            if(b.getX() > 710){//if the ball goes outside the x limitation on player 2 side, reset the paddles and balls, reset gameStarted to false, and add a score to player 1
                p1.reset();
                p2.reset();
                b.reset();
                p1Score++;
                gameStarted = false;
            }//if
            /*if(p1Score == 11){
                gameEnd = true;
            }//if
            if(p2Score == 11){
                gameEnd = true;
            }//if*/
          
            repaint();
            try {
                Thread.sleep(10);
            }catch (InterruptedException e) {
                e.printStackTrace();
            }//catch
        }//for
    }//run

    @Override
    public void keyPressed(KeyEvent e) {//all keyEvents for movement and starting the game
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
        if (e.getKeyCode() == KeyEvent.VK_ENTER){
            gameTitle = true;
        }//if
        if(gameTitle == true){
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                gameStarted = true;
            }//if
        }//if
    }//keyPressed

    @Override
    public void keyReleased(KeyEvent e) {//more keyEvents for when the paddle controlling keys are released, the paddles will stop moving
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
    public void keyTyped(KeyEvent e) {//not used in this program
    }//keyTyped
}//pong
