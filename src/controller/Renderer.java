package controller;
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
 * This is a class meant for rendering the data that is built by the FileLoader class
 * from the Model Package. It is a part of the controller package of the MVC Architectural Pattern.
 * It interacts with both the View and the Model class as expected from the usage of the MVC Pattern.
 * 
 * CAPABILITIES:
 * It is capable of displaying the data from the Model in Orthographic or Perspective view.
 * It can enable translation of the model drawn or deactivate it. 
 * It can enable rotation around X, Y, Z or Free Rotation and also deactivate it.
 * It can can change the background color of the GLCanvas based on the user's choice.
 * It can display the data in both Wireframe and Fill format.
 * It can allow to change the Pseudo-Coloring variable.
 */
//Importing the required libraries.
import com.jogamp.opengl.*;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.FPSAnimator;

import view.GUI;
import model.Face;
import model.FileLoader;
import model.Vertex;

import javax.swing.*;


import static com.jogamp.opengl.GL.*;  // GL constants
import static com.jogamp.opengl.GL2.*; // GL2 constants
import static com.jogamp.opengl.GL2ES1.GL_PERSPECTIVE_CORRECTION_HINT;
import static com.jogamp.opengl.fixedfunc.GLLightingFunc.GL_SMOOTH;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_MODELVIEW;
import static com.jogamp.opengl.fixedfunc.GLMatrixFunc.GL_PROJECTION;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.*;

@SuppressWarnings({ "serial", "unused" })
public class Renderer extends GLCanvas implements GLEventListener, MouseListener, MouseMotionListener{
	
	FileLoader Image = new FileLoader();
	private GLU glu;  // for the GL Utility
	private float rtri=0;
	int x, y;
	GUI gui = new GUI();
	float prevX = 0, prevY = 0;
	float angleX = 0, angleY = 0, angleZ = 0;
	float mouseX = 0, mouseY = 0;
	float moveX = 0, moveY = 0;
	
	String display = "";
	JRadioButtonMenuItem button1= gui.getSelectedRadioButton(GUI.group2);
	String projection = "";
	String rotation = "Deactivate";
	String translation = "";
	
	//final static profile = GLProfile.get( GLProfile.GL2 );
    static GLCapabilities capabilities = new GLCapabilities( GLProfile.get(GLProfile.GL2) );
      
    // The canvas
    final static GLCanvas glcanvas = new GLCanvas( capabilities );

	public Renderer(){
		this.addGLEventListener(this);
	}
	@SuppressWarnings("static-access")
	public void init(GLAutoDrawable drawable){
		GL2 gl = drawable.getGL().getGL2();      // get the OpenGL graphics context
	    glu = new GLU();                         // get GL Utilities
	    gl.glClearColor(1.0f, 1.0f, 1.0f, 0.0f); // set background (clear) color
	    gl.glClearDepth(1.0f);      // set clear depth value to farthest
	    gl.glEnable(GL_DEPTH_TEST); // enables depth testing
	    //Testing whether user entered valid values for Canvas Background.
	    if (gui.valid){
	    	gl.glClearColor(gui.R, gui.G, gui.B, 0.0f); // set background color
	    }
	    else{
	    	gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // set default background (black) color
	    }
	    
	    //Setting the display based on selection by user.
	    switch (display) {
	    	case "Wireframe":
	    		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_LINE );
	    		break;
	    	case "Fill":
	    		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL );
	    		break;
	    	default:
	    		gl.glPolygonMode(GL_FRONT_AND_BACK, GL_FILL );	
	    		break;
	    }
	    //gl.glEnable(GL_POLYGON_OFFSET_FILL );
	    //gl.glDisable(GL_POLYGON_OFFSET_FILL );**/
	    gl.glDepthFunc(GL_LEQUAL);  // the type of depth test to do
	    gl.glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST); // best perspective correction
	    gl.glShadeModel(GL_SMOOTH); // blends colors nicely, and smoothes out lighting
	    //Adding mouse controls to the canvas.
	    ((Component) drawable).addMouseListener(this);
        ((Component) drawable).addMouseMotionListener(this);
	}
	@Override
	public void display(GLAutoDrawable drawable) {
		GL2 gl = drawable.getGL().getGL2();
		//Getting the button selected in each of the groups specified in the GUI.
		//If no button is selected we get null as the value.
		JRadioButtonMenuItem button1= gui.getSelectedRadioButton(GUI.group2);
		JRadioButtonMenuItem button2= gui.getSelectedRadioButton(GUI.group1);
		JRadioButtonMenuItem button3= gui.getSelectedRadioButton(GUI.group3);
		JRadioButtonMenuItem button4= gui.getSelectedRadioButton(GUI.group4);
		JRadioButtonMenuItem button5= gui.getSelectedRadioButton(GUI.group5);
		if (button3 == null) 
			projection = "Perspective";
		
		else 
			projection = button3.getText();
		
		if (button4 == null) 
			rotation = "Deactivate";
		else 
			rotation = button4.getText();
		
		if (button4 == null) 
			translation = "";
		
		else 
			translation = button5.getText();
		
	    if (button1 == null){
	    	  display = "Fill";
	    }
	    else {
	    	display = button1.getText();	  
	    }
	    switch (display) {
			case "Wireframe": 
				this.init(drawable);
				break;
			case "Fill": 
				this.init(drawable);
				break;
			default:
				this.init(drawable);
				break;
	    }
	    
	    reshape(drawable, x, y, glcanvas.getWidth(), glcanvas.getHeight());
		render3D(drawable, button2);
		
	}
	
	public void mouseClicked(MouseEvent e) { }
	public void mouseEntered(MouseEvent e) { 
		//Calculating the mouse co-ordinates when it enters the screen/canvas.
		mouseX = e.getX();
		mouseY = e.getY();
	}
	public void mouseDragged(MouseEvent e) {	
		//Calcuating the angles based on displacement of cursor position.
        float x = e.getX();
        float y = e.getY();
        float thetaY = 360.0f * ( (float)(x-prevX)/(float) glcanvas.getWidth());
        float thetaX = 360.0f * ( (float)(prevY-y)/(float) glcanvas.getHeight());
        float thetaZ = (float) Math.atan2(x - prevX, y - prevY);
        
        angleX += thetaX;
        angleY += thetaY;
        angleZ += thetaZ;
        
        //Printing the X, Y, Z co-ordinates during rotation.
        System.out.println(angleX + " : " + angleY + " : " + angleZ);
        
	}
	public void mouseMoved( MouseEvent e) {
		//Calculating the displacement from mouse motion and updating the displacement variables.
		float x = e.getX();
		float y = e.getY();
		float diffX = x - mouseX;
		float diffY = y - mouseY;
		mouseX = x;
		mouseY = y;
		moveX += diffX;
		moveY += diffY;
	}
	public void mouseExited(MouseEvent e) { }
	public void mouseReleased(MouseEvent e) { }
	public void mousePressed(MouseEvent e) { 
		//Calculating the mouse coordinated during th time when the mouse is kept pressed.
		prevX = e.getX();
		prevY = e.getY();
	}
	
	
	@SuppressWarnings("static-access")
	public void render3D(GLAutoDrawable drawable, JRadioButtonMenuItem button1) {
		
		  GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
	      gl.glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear color and depth buffers
	      gl.glLoadIdentity();  // reset the model-view matrix

	      //  -----
	      gl.glMatrixMode(GL_MODELVIEW);
	      //Checking the value of projection to switch between Orthographic and Perspective projections.
	      switch (projection) {
		      case "Perspective": gl.glTranslatef(-10.5f, 0.0f,-20.0f); break;
		      case "Orthographic": break;
		      default: gl.glTranslatef(-10.5f, 0.0f,-20.0f); break;
	      }
	      
	      //gl.glRotatef(rtri,1.0f,0.0f,0.0f);
	      //Checking the value of translation to activate or deactivate translation of model on the canvas.
	      switch (translation) {
		      case "Activate":
		    	  gl.glTranslatef(moveX/16, -moveY/16, 0.0f); 
			      System.out.println(moveX + " : " + moveY);
		    	  break;
		      case "Deactivate":
		    	  break;
		      default:
		    	  break;
	      }
	      
	      //System.out.println(rotation);
	      //Checking the value of rotation, to decide b/w Free, X, Y, Z or no rotation.
	      switch (rotation) {
		      case "Deactivate":
		    	  gl.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
		    	  break;
		      case "Free Rotation":
		    	  gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f);
			      gl.glRotatef(angleY, 0.0f, -1.0f, 0.0f);
			      gl.glRotatef(angleZ, 0.0f, 0.0f, 1.0f);
			      break;
		      case "Around X":
		    	  gl.glRotatef(angleX, 1.0f, 0.0f, 0.0f);
		    	  break;
		      case "Around Y":
		    	  gl.glRotatef(angleY, 0.0f, -1.0f, 0.0f);
		    	  break;
		      case "Around Z":
		    	  gl.glRotatef(angleZ, 0.0f, 0.0f, 1.0f);
		    	  break;
		      default:
		    	  gl.glRotatef(0.0f, 0.0f, 0.0f, 0.0f);
		    	  break;
	      }
	      //gl.glMatrixMode(GL_PROJECTION);
	      /**glu.gluLookAt(0,0,-5,  // eye pos
                  0,0,0,   // look at
                  0,0,1);  // up**/
	      
	      // draw the filled polygons
	      //String display = "";
	      
	    //gl.glScalef(5.0f, 5.0f, 5.0f);
	    //Clearing all previous data from inside the FileLoader() instance Image to begin
	    //rendering new data.
	    Image.Faces.clear();
	    Image.Vertex.clear();
	    Image.Color.clear();
	    Image.color_var = "";
	    Image.max = 0;
	    Image.min = 100;
	    Image.b = button1;
	    Image.run();
		for (int i = 0; i < Image.Faces.size(); i++){
			//Get a face.
			Face face = (Face) Image.Faces.get(i);
			gl.glBegin(GL_POLYGON); // draw using Quadrilaterals
				for (int k = 0; k < face.vertices.size(); k++ ){
					//Get a vertex of the face
					Vertex v = (Vertex) face.vertices.get(k);
					float r = v.colour.get(0);
					float g = v.colour.get(1);
					float b = v.colour.get(2);
					gl.glVertex3f(v.vertex.get(0), v.vertex.get(1), v.vertex.get(2));
					gl.glColor3f(r, g, b);
				}
				
			gl.glEnd();
			rtri+=0.2f;
		}
		
	}
	@Override
	public void dispose(GLAutoDrawable arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		  GL2 gl = drawable.getGL().getGL2();  // get the OpenGL 2 graphics context
	      //System.out.println(width);
	      //System.out.println(height);
	      if (height == 0) height = 1;   // prevent divide by zero
	      float aspect = (float)width / height;

	      // Set the view port (display area) to cover the entire window
	      gl.glViewport(0, 0, width, height);
	      
	      // Setup perspective projection, with aspect ratio matches viewport
	      gl.glMatrixMode(GL_PROJECTION);  // choose projection matrix
	      gl.glLoadIdentity();             // reset projection matrix
	      
	      //System.out.println(projection);
	      //Checking the projection to choose between Orthographic or Perspective.
	      switch (projection) {
	      	case "Perspective": 
	      		glu.gluPerspective(45.0, aspect, 1.0, 100.0); // fovy, aspect, zNear, zFar
	      		gl.glMatrixMode(GL_MODELVIEW);
	      		gl.glTranslatef(-200.0f, 1.0f, 0.0f);
	      		gl.glLoadIdentity(); // reset
	      		break;
	      	case "Orthographic":
	      		gl.glOrtho(-width/32.0, width/32.0, -height/32.0, height/32.0, -1.0, 1.0);
	      		gl.glMatrixMode(GL_MODELVIEW);
	            gl.glLoadIdentity(); // reset
	      		break;
	      	default:
	      		glu.gluPerspective(45.0, aspect, 1.0, 100.0); // fovy, aspect, zNear, zFar
	      		gl.glMatrixMode(GL_MODELVIEW);
	      		gl.glTranslatef(-200.0f, 1.0f, 0.0f);
	  	        gl.glLoadIdentity(); // reset
	      		break;
	      }
	      
	      
	      //gl.glTranslatef(-0.5f,-5.0f,-6.0f); // Perspective
	      //glu.gluOrtho2D(0.0f,width,height,0.0f);
	      //
	      // Enable the model-view transform
	      
		
	}
	
	public static void main(String[] args){
		  //Creating a new instance of Renderer().
	      Renderer renderer = new Renderer();
	      //Adding it to the canvas as an Event Listener.
	      glcanvas.addGLEventListener( renderer );
	      //Setting the canvas size.
	      glcanvas.setSize( 900, 600 );
	      //Getting an instance of the GUI from view package.
		  GUI gui = new GUI();
		  //Running the GUI on the canvas to attach GUI to the canvas.
		  gui.run(glcanvas);
	      final FPSAnimator animator = new FPSAnimator(glcanvas, 300,true);
	      animator.start();
	}
}
