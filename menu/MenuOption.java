/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breekoot.menu;

import breekoot.Board;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author carlkonig
 */
public class MenuOption {

    private final String text;
    private final float x;
    private final float y;
    private final float width;
    private final float height;
    private final Board.GameState state;

    public MenuOption(String text, float x, float y, float width, float height, Board.GameState state) {
        this.text = text;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.state = state;
    }

    public void activate() {

    }

    public void draw(Graphics2D g2d, int width, int height, boolean selected) {
        //Display if game is in this state
        if (Board.getGameState() == state) {
            //Show that we're selected or not
            if (selected) {
                g2d.setColor(Color.RED);
            } else {
                g2d.setColor(Color.GRAY);
            }
            int rWidth = (int) (this.width * width);
            int rHeight = (int) (this.height * height);
            int rX = (int) (this.x * width);
            int rY = (int) (this.y * height);

            g2d.fillRect(rX, rY, rWidth, rHeight);
            g2d.setColor(Color.BLACK);
            int sX = rX + rWidth / 2 - g2d.getFontMetrics().stringWidth(text) / 2;
            int sY = rY + rHeight / 2 + g2d.getFont().getSize() / 2;
            g2d.drawString(text, sX, sY);
        }
    }

}
