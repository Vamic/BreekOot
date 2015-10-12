/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breekoot;

import java.awt.Color;
import java.awt.Graphics;
import java.time.Clock;
import javax.swing.JPanel;

/**
 *
 * @author carlkonig
 */
public class Board extends JPanel implements Runnable {

    private Thread animator;
    int x = 0;

    public Board() {

        initBoard();
    }

    private void initBoard() {

        setBackground(Color.BLACK);
        setDoubleBuffered(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.blue);
        g.fillRect(0, 0, 20, 20);
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
        
    }
}
