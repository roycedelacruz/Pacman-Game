package com.pacman.menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.pacman.environment.GeneralEnvinronment;

/**
 * Menu class extends the parent class which implements KeyListener
 */
public final class Menu extends javax.swing.JFrame implements KeyListener {

	JFrame frame = new JFrame();

    /**
     * Creates the menu graphics
     */
    public Menu() {
    	ImageIcon image = new ImageIcon("src/com/pacman/menu/Initial.png");
        
        JLabel label = new JLabel();
        label.setIcon(image);
  
        frame.setLocationRelativeTo(null);
        frame.setTitle("PACMAN");
        frame.setVisible(true);
        frame.setSize(960,580);
        frame.setResizable(false);
		frame.add(label);
		frame.addKeyListener(this);
		setFocusable(true);	
    }

 
    /**
     * @param args the command line arguments
     */
    
   
    public static void main(String args[]) {
  
        /* Create and display the form */
 
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Menu();
                
            }
        });
    }


    /**
     * generates the general environment of the game
     */
public void generate(int n)throws InterruptedException{

     new GeneralEnvinronment(n,5,0);
}





@Override
public void keyTyped(KeyEvent e) {

}


@Override
public void keyPressed(KeyEvent e) {
	// TODO Auto-generated method stub

	int key = e.getKeyCode();
	if (key == KeyEvent.VK_SPACE) {
		
		try {
		     frame.setVisible(false); //removes the window menu
			generate(0);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}
	
}


@Override
public void keyReleased(KeyEvent e) {
	// TODO Auto-generated method stub
	
}

    
}

