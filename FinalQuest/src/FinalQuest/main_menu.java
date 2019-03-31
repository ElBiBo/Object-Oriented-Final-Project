/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.*;
import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.ImageObserver;
/**
 *
 * @author SusyBW65
 */
public class main_menu implements KeyListener{//extend it to Board
    JFrame window;
    Container con;
    JPanel titleNamePanel, startButtonPanel, leaderBoardButtonPanel,
    achievementButtonPanel, exitButtonPanel, arrowButtonPanel;
    JLabel titleNameLabel;
    private BufferedImage image;

    Font titleFont = new Font("Impact", Font.PLAIN, 90);
    Font normalFont = new Font("Times New Roman", Font.PLAIN, 30);
    JButton startButton,leaderBoardButton,achievementButton,exitButton;
   
    
     public static void main(String[] args){
       
       new main_menu();
       
    }


    public main_menu(){
       window = new JFrame();
       window.setSize(800, 600);
       window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       window.getContentPane().setBackground(Color.black);
       window.setLayout(null);
      
       con = window.getContentPane();

       titleNamePanel = new JPanel();
       titleNamePanel.setBounds(100, 100, 550, 150);
       titleNamePanel.setBackground(Color.black);
       titleNameLabel = new JLabel("Final Quest");
       titleNameLabel.setForeground(Color.white);
       titleNameLabel.setFont(titleFont);

       startButtonPanel = new JPanel();
       startButtonPanel.setBounds(300, 300, 200, 40);
       startButtonPanel.setBackground(Color.black);

       startButton = new JButton("START");
       startButton.setBackground(Color.black);
       startButton.setForeground(Color.white);
       startButton.setFont(normalFont);

       leaderBoardButtonPanel = new JPanel();
       leaderBoardButtonPanel.setBounds(300, 350, 200, 40);
       leaderBoardButtonPanel.setBackground(Color.black);

       leaderBoardButton = new JButton("Scores");
       leaderBoardButton.setBackground(Color.black);
       leaderBoardButton.setForeground(Color.white);
       leaderBoardButton.setFont(normalFont);
      
       achievementButtonPanel = new JPanel();
       achievementButtonPanel.setBounds(300, 400, 200, 40);
       achievementButtonPanel.setBackground(Color.black);

       achievementButton = new JButton("Achievements");
       achievementButton.setBackground(Color.black);
       achievementButton.setForeground(Color.white);
       achievementButton.setFont(normalFont);

       exitButtonPanel = new JPanel();
       exitButtonPanel.setBounds(300, 450, 200, 40);
       exitButtonPanel.setBackground(Color.black);

       exitButton = new JButton("Exit");
       exitButton.setBackground(Color.black);
       exitButton.setForeground(Color.white);
       exitButton.setFont(normalFont);

       arrowButtonPanel = new JPanel();
       arrowButtonPanel.setBounds(250, 300, 100, 500);
       arrowButtonPanel.setBackground(Color.blue);
	

       titleNamePanel.add(titleNameLabel);
       startButtonPanel.add(startButton);
       leaderBoardButtonPanel.add(leaderBoardButton);
       achievementButtonPanel.add(achievementButton);
       exitButtonPanel.add(exitButton);
      
exitButton.addActionListener(new ActionListener() {
                        
			public void actionPerformed(ActionEvent e) {
				
				System.exit(0);
			}
		});

        try{
	image = ImageIO.read(new File("/Users/SusyBW65/Pictures/greenArrow.png"));
	}catch (IOException ex){
		
	}catch (Exception ex) {
		
	}
	ImageIcon imageicon=new ImageIcon(image);
	JLabel arrow=new JLabel(imageicon);
	arrow.setBounds(300, 300, 30, 44);
        Point pt = arrow.getLocation();
        int x = pt.x;
	int y = pt.y;
        arrow.setLocation(x-30,y); 
        window.add(arrow);
	

	

       con.add(titleNamePanel);
       con.add(startButtonPanel);
       con.add(leaderBoardButtonPanel);
       con.add(achievementButtonPanel);
       con.add(exitButtonPanel);
       con.add(arrowButtonPanel);
 
       
       
       

	moveArrow(x, y, arrow);
        window.setVisible(true);
    }


public void moveArrow(int x, int y, JLabel arrow){

ArrayList<String> options = new ArrayList<String>();

options.add("Start");
options.add("Highscores");
options.add("Achievements");
options.add("Exit");


window.setFocusTraversalKeysEnabled(false);
window.addKeyListener(this);
for(int i=0; i<options.size();i=i+0){

/*if(key.getKeyCode == KeyEvent.VK_UP){
	if(i==0){
		    i=3;
		    
		    arrow.setLocation(x-30,y-50);
		   
		    window.add(arrow);
		}
		else{
		 i--;
		}
	
     }
*/

}
 
}   


@Override
 public void keyPressed(KeyEvent e){
  
	
	
	
}  

@Override
public void keyReleased(KeyEvent e){
	
}

@Override
public void keyTyped(KeyEvent e)
{
	
}


} 
