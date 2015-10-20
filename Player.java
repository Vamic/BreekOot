/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breekoot;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 *
 * @author carlkonig
 */
public class Player extends GameObject {

    private float speed;
    private float velocity;
    public Player(float x, float y, float width, float height, boolean physical, Color color, float speed) {
        super(x, y, width, height, physical, color);
        this.speed = speed;
        velocity = 0;
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
        velocity = x-oldX;
    }

    public float getVelocity()
    {
        return velocity;
    }
}
