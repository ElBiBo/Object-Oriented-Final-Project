/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FinalQuest;

import java.awt.EventQueue;
import javax.swing.JFrame;

public class FinalQuest extends JFrame {

    public FinalQuest() {
        
        initUI();
    }
    
    private void initUI() {
        
        add(new Board());
        
        setResizable(false);
        pack();
        
        setTitle("Final Quest: Journey for a Passing Grade");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        
        EventQueue.invokeLater(() -> {
            FinalQuest ex = new FinalQuest();
            ex.setVisible(true);
        });
    }
}