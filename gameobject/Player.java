/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breekoot.gameobject;

import breekoot.Board;
import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 *
 * @author carlkonig
 */
public class Player extends GameObject {

    private final float speed;
    private float direction;
    private static int score;
    private static int lives;
    private static String name;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        Player.name = name.toUpperCase().substring(0, 3);
    }

    public int getLives() {
        return lives;
    }

    public static void loseLife() {
        Player.lives--;
    }

    public static int getScore() {
        return score;
    }

    public static void addScore(int score) {
        Player.score += score;
    }
    public Player(float x, float y, float width, float height, boolean physical, Color color, float speed, int lives) {
        super(x, y, width, height, physical, color);
        this.speed = speed;
        direction = 0;
        Player.lives = lives;
        Player.score = 0;
        Player.name = "AAA";
    }

    @Override
    public void update(long delta) {
        
        //Change to seconds
        double mod = delta * 0.001;
        float oldX = x;
        //Move right
        if (Board.keys.contains(KeyEvent.VK_RIGHT)) {
            x += speed * mod;
            //Border constraint
            if (x+width > 1) {
                x = 1-width;
            }
        }
        //Move left
        if (Board.keys.contains(KeyEvent.VK_LEFT)) {
            x -= speed * mod;
            //Border constraint
            if (x < 0) {
                x = 0;
            }
        }
        direction = Math.signum(x-oldX);
    }

    public float getCurrentSpeed()
    {
        return direction*speed;
    }
}
