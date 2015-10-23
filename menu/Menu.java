/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breekoot.menu;

import breekoot.Board;
import breekoot.gameobject.Player;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author carlkonig
 */
public class Menu {

    private LinkedList<LinkedList<MenuOption>> menus;
    private int selected;
    private static LinkedList<Integer> oldKeys;

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
        float height = 0.3f - 0.2f;

        float x = 0.2f;
        float y = 0.1f;

        menu.add(new MenuOption(text, x, y, width, height, oState));

        text = "Edit Name";
        y = 0.4f;

        menu.add(new MenuOption(text, x, y, width, height, oState));

        text = "Exit";
        y = 0.7f;

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
        //amount of selectable options, including 0
        int cap = 1;
        if (Board.getGameState() == Board.GameState.MAIN_MENU) {
            cap = 2;
        }
        if (selected < 0) {
            selected = cap;
        }
        if (selected > cap) {
            selected = 0;
        }
        if (Board.keys.contains(KeyEvent.VK_SPACE)
                && !oldKeys.contains(KeyEvent.VK_SPACE)
                || Board.keys.contains(KeyEvent.VK_ENTER)
                && !oldKeys.contains(KeyEvent.VK_ENTER)) {
            if (selected == 0) {
                //Play or Resume, both do the same
                if (Board.getGameState() == Board.GameState.MAIN_MENU
                        || Board.getGameState() == Board.GameState.PAUSED) {
                    Board.setGameState(Board.GameState.RUNNING);
                } else if (Board.getGameState() == Board.GameState.GAME_OVER) {
                    Board.revive();
                    Board.setGameState(Board.GameState.MAIN_MENU);
                }
            }
            if (selected == 1) {
                //Change name in main
                if (Board.getGameState() == Board.GameState.MAIN_MENU) {
                    String input = JOptionPane.showInputDialog("Name:", Player.getName());
                    Player.setName(input);
                } else if (Board.getGameState() == Board.GameState.PAUSED) {
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
