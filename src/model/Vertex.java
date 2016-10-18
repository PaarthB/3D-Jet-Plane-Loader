package model;
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
 * This is a class meant for creating the Vertex Data Structure in which the information would be stored.
 * It is a part of the model package of the MVC Architectural Pattern.
 * 
 * CAPABILITIES:
 * It creates a data structure for loading Vertex information which includes a vertex's co-ordinates
 * in X, Y, Z and its color values in R, G, B.
 *
 */

//Importing the required libraries.
import java.util.*;

public class Vertex {
	
	public List<Float> vertex = new ArrayList<Float>();
	public List<Float> colour = new ArrayList<Float>();
	
}
