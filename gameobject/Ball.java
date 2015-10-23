/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breekoot.gameobject;

import com.sun.javafx.geom.Vec2f;
import java.awt.Color;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author carlkonig
 */
public class Ball extends GameObject {

    private final Vec2f speed;
    private final float maxSpeed;
    public Ball(float x, float y, float width, float height, boolean physical, Color color, Vec2f speed) {
        super(x, y, width, height, physical, color);
        this.speed = speed;
        maxSpeed = 0.75f;
    }

    @Override
    public void update(long delta) {
        //Change to seconds
        double mod = delta * 0.001;
        x += speed.x * mod;
        y += speed.y * mod;
        if (x < 0) {
            speed.x = Math.abs(speed.x);
        } else if (x + width > 1) {
            speed.x = -Math.abs(speed.x);
        }
        if (y < 0) {
            speed.y = Math.abs(speed.y);
        }
        if (y > 1) {
            y = 0.4f;
            x = 0.4f;
            Player.loseLife();
        }
    }

    public void collissionCheck(GameObject target) {
        //Make rectangles to see if they intersect
        Rectangle2D thisRect = getRectangle();
        Rectangle2D thatRect = target.getRectangle();

        if (thisRect.intersects(thatRect)) {
            //Get a rectangle representing the intersercted area
            Rectangle2D intersectRect = thisRect.createIntersection(thatRect);
            //Higher than wide means the side got hit
            if (intersectRect.getHeight() > intersectRect.getWidth()) {
                //Check if left or right side
                if (intersectRect.getX() < thatRect.getCenterX()) {
                    x = target.getX()-width;
                    speed.x = -Math.abs(speed.x);
                } else if (intersectRect.getX() + intersectRect.getWidth() > thatRect.getCenterX()) {
                    x = target.getX() + target.getWidth();
                    speed.x = Math.abs(speed.x);
                }
            } else {
                //Check if top or bottom
                if (intersectRect.getCenterY() < thatRect.getCenterY()) {
                    y = target.getY()-height;
                    speed.y = -Math.abs(speed.y);
                } else if (intersectRect.getCenterY() > thatRect.getCenterY()) {
                    y = target.getY() + target.getHeight();
                    speed.y = Math.abs(speed.y);
                }
                //Change speed depending on the players current speed
                if (target.getClass().getSimpleName().equals("Player")) {
                    Player player = (Player) target;
                    
                    speed.x += player.getCurrentSpeed() * 0.25;
                    if(Math.abs(speed.x) > maxSpeed)
                    {
                        speed.x = Math.signum(speed.x)*maxSpeed;
                    }
                }
            }
            if (target.getClass().getSimpleName().equals("Block")) {

                Block block = (Block) target;
                block.hit();
            }
        }
    }
}
