/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breekoot;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.time.Clock;
import javax.swing.JPanel;

/**
 *
 * @author carlkonig
 */
public class Board extends JPanel implements Runnable {

    private Thread animator;
    double x;
    double speed;
    boolean running;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        
        running = true;
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
    public void run() {

        long now, then, delta, sleep;

        then = System.currentTimeMillis();

        //The game loop
        while (running) {
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
        double mod;
        mod = delta * 0.001;
        //speed is measured in percent per second right now
        speed = 0.3; //10%
        x += speed * mod;
        if (x > 1) {
            x = -0.1;
        }
        System.out.println();
    }

    private void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        int height = getHeight();
        int width = getWidth();
        g2d.setColor(Color.blue);
        g2d.fillRect((int) (x * width), height - (int) (0.1 * height),
                (int) (0.1 * width), (int) (0.1 * height));
    }
}
