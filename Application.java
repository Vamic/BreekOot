/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package breekoot;

import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author carlkonig
 */
public class Application extends JFrame {

    public Application() {
        initUI();
    }

    private void initUI() {
        //Create the custom JPanel for the game
        add(new Board());
        //Window size
        setSize(800, 600);
        
        
        setTitle("Breek Oot");
        //Exit program when you press the X button.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Put this window in the middle of the screen.
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Application ex = new Application();
                ex.setVisible(true);
            }
        });
    }
}
