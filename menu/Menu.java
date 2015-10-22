/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breekoot.menu;

import breekoot.Board;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.LinkedList;

/**
 *
 * @author carlkonig
 */
public class Menu {

    private LinkedList<LinkedList<MenuOption>> menus;
    private int selected;
    private LinkedList<Integer> oldKeys;

    public Menu() {
        initialize();

        oldKeys = new LinkedList<>();
    }

    private void initialize() {
        menus = new LinkedList<>();
        LinkedList<MenuOption> menu = new LinkedList<>();
        //Main menu
        Board.GameState oState = Board.GameState.MAIN_MENU;
        String text = "Play";
        float width = 0.6f;
        float height = 0.5f - 0.2f;

        float x = 0.2f;
        float y = 0.1f;

        menu.add(new MenuOption(text, x, y, width, height, oState));

        text = "Exit";
        y = 0.6f;

        menu.add(new MenuOption(text, x, y, width, height, oState));

        menus.add(menu);

        menu = new LinkedList<>();
        //Pause menu
        oState = Board.GameState.PAUSED;

        text = "Resume";
        y = 0.1f;

        menu.add(new MenuOption(text, x, y, width, height, oState));

        text = "Exit";
        y = 0.6f;

        menu.add(new MenuOption(text, x, y, width, height, oState));

        menus.add(menu);
    }

    public void update() {

        if (Board.keys.contains(KeyEvent.VK_LEFT)
                && !oldKeys.contains(KeyEvent.VK_LEFT)
                || Board.keys.contains(KeyEvent.VK_UP)
                && !oldKeys.contains(KeyEvent.VK_UP)) {
            selected--;
        } else if (Board.keys.contains(KeyEvent.VK_RIGHT)
                && !oldKeys.contains(KeyEvent.VK_RIGHT)
                || Board.keys.contains(KeyEvent.VK_DOWN)
                && !oldKeys.contains(KeyEvent.VK_DOWN)) {
            selected++;
        }
        if (selected < 0) {
            selected = 1;
        }
        if (selected > 1) {
            selected = 0;
        }
        if (Board.keys.contains(KeyEvent.VK_SPACE)
                || Board.keys.contains(KeyEvent.VK_ENTER)) {
            if (selected == 0) {
                //Play or Resume, both do the same
                if (Board.getGameState() == Board.GameState.MAIN_MENU
                        || Board.getGameState() == Board.GameState.PAUSED) {
                    Board.setGameState(Board.GameState.RUNNING);
                }
            }
            if (selected == 1) {
                //Exit on both menues
                if (Board.getGameState() == Board.GameState.MAIN_MENU
                        || Board.getGameState() == Board.GameState.PAUSED) {
                    Board.quit();
                }
            }
            selected = 0;
        }
        oldKeys = new LinkedList<>();
        for (int i = 0; i < Board.keys.size(); i++) {
            oldKeys.add(Board.keys.get(i));
        }
    }

    public void draw(Graphics2D g2d, int width, int height) {
        for (int j = 0; j < menus.size(); j++) {

            for (int i = 0; i < menus.get(j).size(); i++) {

                menus.get(j).get(i).draw(g2d, width, height, selected == i);
            }
        }
    }
}
