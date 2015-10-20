/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breekoot;

import com.sun.javafx.geom.Vec2f;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import javax.swing.JPanel;

/**
 *
 * @author carlkonig
 */
public class Board extends JPanel implements Runnable, KeyListener {

    private Thread animator;
    static public LinkedList<Integer> keys;
    private LinkedList<GameObject> gameObjects;

    public enum GameState {

        MAIN_MENU,
        PAUSED,
        RUNNING,
    }
    GameState gameState;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        gameState = GameState.MAIN_MENU;
        keys = new LinkedList<>();
        gameObjects = new LinkedList<>();
        
        //Create ball
        //5% wide, 5% high
        float width = 0.05f;
        float height = 0.05f;
        //a bit left to the middle of the screen
        float x = 0.25f-width/2;
        float y = 0.5f-height/2;
        //25% down per second, 50% right per second, white
        Vec2f bSpeed = new Vec2f(0.25f, 0.5f);
        Color color = Color.WHITE;
        
        gameObjects.add(new Ball(x, y, width, height, true, color, bSpeed));
        //Add player
        //15% wide, 5% high
        width = 0.15f;
        height = 0.05f;
        //middle of screen, 5% above the bottom
        x = 0.5f-width/2;
        y = 1-(height+0.05f);
        //50% per second, yellow
        float pSpeed = 0.5f;
        color = Color.YELLOW;
        gameObjects.add(new Player(x, y, width, height, true, color, pSpeed));

        int rows = 5;
        int columns = 20;
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < columns; j++)
            {
                width = 1/(float)columns;
                height = 0.3f/(float)rows;
                
                x = width*j;
                y = height*i;
                
                color = Color.MAGENTA;
                gameObjects.add(new Block(x, y, width, height, true, color));
            }
        }
        
        addKeyListener(this);
        setFocusable(true);
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        draw(g);
    }

    @Override
    public void addNotify() {
        super.addNotify();

        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!keys.contains(e.getKeyCode())) {
            keys.add(e.getKeyCode());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.removeFirstOccurrence(e.getKeyCode());
    }

    @Override
    public void run() {

        long now, then, delta, sleep;

        then = System.currentTimeMillis();

        //The game loop
        while (true) {
            //Get how long it's been since the last loop
            now = System.currentTimeMillis();
            delta = now - then;
            //Run at a maximum of 60 fps 
            //(i dont know why but it seems like 1000/X gives X * 2 fps)
            sleep = 1000 / 30 - delta;

            //Update everything using the delta time and then display it
            updateGame(delta);
            repaint();

            then = System.currentTimeMillis();

            if (sleep < 0) {
                sleep = 2;
            }

            try {
                Thread.sleep(sleep);
            } catch (InterruptedException e) {
                System.out.println("Interrupted: " + e.getMessage());
            }

        }

    }

    private void updateGame(long delta) {
        if (gameState == GameState.RUNNING) {
            //Update all GameObjects
            for(GameObject go : gameObjects)
            {
                if(go.getClass().getSimpleName().equals("Ball"))
                {
                    Ball ball = (Ball)go;
                    for(GameObject go2 : gameObjects)
                    {
                        if(!go2.getClass().getSimpleName().equals("Ball"))
                            ball.collissionCheck(go2);
                    }
                }
                go.update(delta);
            }
            //Pause with ESC or P
            if (keys.contains(KeyEvent.VK_ESCAPE)
                    || keys.contains(KeyEvent.VK_P)) {
                gameState = GameState.PAUSED;
                keys.removeFirstOccurrence(KeyEvent.VK_P);
                keys.removeFirstOccurrence(KeyEvent.VK_ESCAPE);
            }
        } else if (gameState == GameState.PAUSED) {
            //Unpause with ESC or P
            if (keys.contains(KeyEvent.VK_ESCAPE)
                    || keys.contains(KeyEvent.VK_P)) {
                gameState = GameState.RUNNING;
                keys.removeFirstOccurrence(KeyEvent.VK_P);
                keys.removeFirstOccurrence(KeyEvent.VK_ESCAPE);
            }
        } else if (gameState == GameState.MAIN_MENU) {
            gameState = GameState.RUNNING;
        }
    }

    private void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();

        if (gameState == GameState.RUNNING) {
            for(GameObject go : gameObjects)
            {
                go.draw(g2d, width, height);
            }
        } else if (gameState == GameState.PAUSED) {
            g2d.setColor(Color.GRAY);
            double oWidth = 0.8;
            double oHeight = 0.2;
            //Temporarily messy, will get a class later
            g2d.fillRect((int) (0.1 * width), (int) (0.2 * height),
                    (int) (oWidth * width), (int) (oHeight * height));
            g2d.fillRect((int) (0.1 * width), (int) (0.5 * height),
                    (int) (oWidth * width), (int) (oHeight * height));
        } else if (gameState == GameState.MAIN_MENU) {
            g2d.setColor(Color.GRAY);
            double oWidth = 0.8;
            double oHeight = 0.2;
            //Temporarily messy, will get a class later
            g2d.fillRect((int) (0.1 * width), (int) (0.2 * height),
                    (int) (oWidth * width), (int) (oHeight * height));
            g2d.fillRect((int) (0.1 * width), (int) (0.5 * height),
                    (int) (oWidth * width), (int) (oHeight * height));
        }

    }
}
