/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breekoot.gameobject;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author carlkonig
 */
public abstract class GameObject {

    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected boolean physical;
    protected Color color;

    public GameObject(float x, float y, float width, float height, boolean physical, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.physical = physical;
        this.color = color;
    }

    /**
     * What the object will do every update.
     *
     * @param delta amount of time since last update, in milliseconds.
     */
    public abstract void update(long delta);

    public void draw(Graphics2D g2d, int width, int height) {
        int rWidth = (int) (this.width * width);
        int rHeight = (int) (this.height * height);
        int rX = (int) (this.x * width);
        int rY = (int) (this.y * height);

        g2d.setColor(color);
        g2d.fillRect(rX, rY, rWidth, rHeight);
        g2d.setColor(Color.BLACK);
        g2d.drawRect(rX, rY, rWidth, rHeight);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle2D getRectangle() {
        if (physical) {
            //Turn dimensions from decimals to bigger numbers for comparing
            int scale = 1000;
            int rX = (int) (x * scale);
            int rY = (int) (y * scale);
            int rWidth = (int) (width * scale);
            int rHeight = (int) (height * scale);
            return new Rectangle(rX, rY, rWidth, rHeight);
        } else {
            return new Rectangle(0, 0, 0, 0);
        }
    }
}
