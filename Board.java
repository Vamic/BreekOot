/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breekoot;

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
    double x;
    double speed;
    LinkedList<Integer> keys;

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
            System.out.println(e.getKeyCode() + " added");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys.removeFirstOccurrence(e.getKeyCode());
        System.out.println(e.getKeyCode() + " removed");
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
            //change to seconds
            double mod = delta * 0.001;
            //Measured in percent per second
            speed = 0.5;
            //Move right
            if (keys.contains(KeyEvent.VK_RIGHT)) {
                x += speed * mod;
                //Border constraint
                if (x > 0.85) {
                    x = 0.85;
                }
            }
            //Move left
            if (keys.contains(KeyEvent.VK_LEFT)) {
                x -= speed * mod;
                //Border constraint
                if (x < 0) {
                    x = 0;
                }
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

        int height = getHeight();
        int width = getWidth();

        if (gameState == GameState.RUNNING) {
            g2d.setColor(Color.BLUE);
            //Temporarily messy, will get a class later
            g2d.fillRect((int) (x * width), height - (int) (0.05 * height + 10),
                    (int) (0.15 * width), (int) (0.05 * height));
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
