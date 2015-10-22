/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breekoot.gameobject;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author carlkonig
 */
public class Block extends GameObject {

    private boolean dying;
    private boolean dead;

    public Block(float x, float y, float width, float height, boolean physical, Color color) {
        super(x, y, width, height, physical, color);
        dying = false;
        dead = false;
    }

    @Override
    public void update(long delta) {
        if (dying && !dead) {
            height -= 0.01f;
            width -= 0.01f;

            y += 0.005f;
            x += 0.005f;
            if (width < 0 || height < 0) {
                width = 0;
                height = 0;
                dead = true;
                physical = false;
            }
        }
    }

    public void hit() {
        dying = true;
        Player.addScore(100);
    }

}
