package view;
/**
 * Author: Paarth Bhasin
 * Purpose: Assignment 2 FIT3088
 * Tutor: Han Pham
 * Lecturer: Amr Hassan
 * Date: 18/10/2016
 * 
 */

/**
 * DESCRIPTION: 
 * This is a class meant for creating the User Interface for the Java Application. 
 * It is a part of the view package of the MVC Architectural Pattern.
 * It interacts with both the Model and the Controller class as expected from the usage of the MVC Pattern.
 * 
 * CAPABILITIES:
 * It creates a specfiic User Interface for the application consisting of a Menu of Menus
 * which contains selectable content in them.
 *
 */

//Importing the required libraries.

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
import java.util.*;

import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.util.FPSAnimator;

@SuppressWarnings("unused")
public class GUI {
	
	//Variables used for setting up the GUI.
	public static ButtonGroup group1 = new ButtonGroup();
    public static ButtonGroup group2 = new ButtonGroup();
    public static ButtonGroup group3 = new ButtonGroup();
    public static ButtonGroup group4 = new ButtonGroup();
    public static ButtonGroup group5 = new ButtonGroup();
    public static boolean valid;
    public static float R;
	public static float G;
	public static float B;
    
	//Getting the selected button from the group. Returns null if no button is selected.
    @SuppressWarnings("static-access")
	public
	JRadioButtonMenuItem button1 = this.getSelectedRadioButton(this.group1);
    @SuppressWarnings("static-access")
	public
    JRadioButtonMenuItem button2 = this.getSelectedRadioButton(this.group2);
    @SuppressWarnings("static-access")
    JRadioButtonMenuItem button3 = this.getSelectedRadioButton(this.group3);
    @SuppressWarnings("static-access")
	JRadioButtonMenuItem button4 = this.getSelectedRadioButton(this.group4);
    @SuppressWarnings("static-access")
	JRadioButtonMenuItem button5 = this.getSelectedRadioButton(this.group5);
	
    public void run(GLCanvas glcanvas){
    	//Getting the canvas from the controller class.
    	main(null, glcanvas);
    }
	public static void main(String[] args, GLCanvas glcanvas){
		
			
	      //Setting up the GUI.
	      final JFrame frame = new JFrame ( " Computer Graphics" );
	      frame.getContentPane().add( glcanvas, BorderLayout.CENTER );
		  
	      
	      JMenuBar menuBar = new JMenuBar();
	      JMenuItem exit = new JMenuItem("Exit");
	      JMenuItem Background = new JMenuItem("Background");
	      JMenu Display = new JMenu("Display Type");
	      JMenu var = new JMenu("Pseudo-colouring Variable");
	      JMenu proj = new JMenu("Projection");
	      JMenu rot = new JMenu("Rotation");
	      JMenu trans = new JMenu("Translation");
	      JMenu menu = new JMenu("Options");
	      
	      JMenuItem x = new JRadioButtonMenuItem("X");
	      JMenuItem y = new JRadioButtonMenuItem("Y");
	      JMenuItem z = new JRadioButtonMenuItem("Z");
	      JMenuItem s = new JRadioButtonMenuItem("S");
	      
	      JMenuItem wire = new JRadioButtonMenuItem("Wireframe");
	      JMenuItem color = new JRadioButtonMenuItem("Fill");
	      
	      JMenuItem ortho = new JRadioButtonMenuItem("Orthographic");
	      JMenuItem perspective = new JRadioButtonMenuItem("Perspective");
	      
	      JMenuItem rotx = new JRadioButtonMenuItem("Around X");
	      JMenuItem roty = new JRadioButtonMenuItem("Around Y");
	      JMenuItem rotz = new JRadioButtonMenuItem("Around Z");
	      JMenuItem free = new JRadioButtonMenuItem("Free Rotation");
	      JMenuItem deac = new JRadioButtonMenuItem("Deactivate");
	      
	      JMenuItem acc = new JRadioButtonMenuItem("Activate");
	      JMenuItem den = new JRadioButtonMenuItem("Deactivate");
	      
	      menu.setMnemonic(KeyEvent.VK_A);
	      
	      group1.add(x);
	      group1.add(y);
	      group1.add(z);
	      group1.add(s);
	     
	      group2.add(wire);
	      group2.add(color);
	      
	      group3.add(ortho);
	      group3.add(perspective);
	      
	      group4.add(rotx);
	      group4.add(roty);
	      group4.add(rotz);
	      group4.add(free);
	      group4.add(deac);
	      
	      group5.add(acc);
	      group5.add(den);
	      
	      
	      Display.add(wire);
	      Display.add(color);
	      
	      var.add(x);
	      var.add(y);
	      var.add(z);
	      var.add(s);
	      
	      proj.add(ortho);
	      proj.add(perspective);
	      
	      rot.add(rotx);
	      rot.add(roty);
	      rot.add(rotz);
	      rot.add(free);
	      rot.add(deac);
	      
	      trans.add(acc);
	      trans.add(den);
	      
	      menu.add(Display);
	      //Display.addActionListener(new Window());
	      menu.add(var);
	      menu.add(proj);
	      menu.add(rot);
	      menu.add(trans);
	      menu.add(Background);
	      menu.add(exit);
	      menuBar.add(menu);

	      //bChange = new JButton("Click Me!"); // construct a JButton
	      frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );   
	      
	      frame.getContentPane().add(menuBar, BorderLayout.NORTH);
	      
	      exit.addActionListener(new ActionListener() {
	    	    @Override
	    	    public void actionPerformed(ActionEvent actionEvent) {
	    	      System.exit(0);
	    	    }
	    	});
	      
	      Background.addActionListener(new ActionListener() {
	    		@Override
	    		public void actionPerformed(ActionEvent actionEvent) {
	    			JTextField r = new JTextField(5);
	    		    JTextField g = new JTextField(5);
	    		    JTextField b = new JTextField(5);
	    		   
	    		    Object[] message = {
	    		    	    "Red:", r,
	    		    	    "Green:", g,
	    		    	    "Blue:", b,
	    		    	};
	    		    JPanel myPanel = new JPanel();
	    		    myPanel.add(new JLabel("Red"));
	    		    myPanel.add(r);
	    		    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	    		    myPanel.add(new JLabel("Green:"));
	    		    myPanel.add(g);
	    		    myPanel.add(Box.createHorizontalStrut(15)); // a spacer
	    		    myPanel.add(new JLabel("Blue:"));
	    		    myPanel.add(b);

	    		    int result = JOptionPane.showConfirmDialog(null, myPanel, 
	    		               "Enter RGB values between 0 and 1 (inclusive)", JOptionPane.OK_CANCEL_OPTION);
	    		    if (result == JOptionPane.OK_OPTION) {
	    		       
	    		       try{
	    		    	   float red = Float.parseFloat(r.getText());
	    		    	   float blue = Float.parseFloat(g.getText());
	    		    	   float green = Float.parseFloat(b.getText());
	    		    	   if(red <=1  && red >= 0 && green <= 1 && green >= 0 && blue <= 1 && blue >= 0){
	    		    		   R = red;
	    		    		   G = green;
	    		    		   B = blue;
	    		    		   valid = true;
	    		    	   }
	    		       }
	    		       
	    		       catch(NumberFormatException nfe)  
	    		       {  
	    		         System.out.println("Invalid Values for RGB"); 
	    		       }  
	    		    }
	    		}
	      	
	      	});
	      
	      frame.setSize( frame.getContentPane().getPreferredSize() );
	      frame.setVisible( true );
	      frame.pack();
	      
	}
	
	public JRadioButtonMenuItem getSelectedRadioButton(ButtonGroup buttonGroup) {

	    Enumeration<AbstractButton> abstractButtons = buttonGroup.getElements();
	    JRadioButtonMenuItem radioButton = null;

	    while (abstractButtons.hasMoreElements()) {
	        radioButton = (JRadioButtonMenuItem) abstractButtons.nextElement();
	        if (radioButton.isSelected()) {
	            break;
	        }
	    }
	    return radioButton;
	}
}
