package model;

/**
 * DESCRIPTION: 
 * This is a class meant for loading the data that is present in the .dat file. 
 * It is a part of the model package of the MVC Architectural Pattern.
 * It interacts with both the View and the Controller class as expected from the usage of the MVC Pattern.
 * 
 * CAPABILITIES:
 * It loads the informtion from the .dat file in the Tecplot ASCII format and generates relevant
 * data structures to access that information.
 * 
 * It loads the information from Color map in the CSV format and generates relevant data structures
 * to access that information.
 *
 */

//Importing the required libraries.

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;

@SuppressWarnings("unused")
public class FileLoader {
	
	
	//The variables used to handle the data accordingly.
	public static double max = 0;
	public static double min = 100;
	public static int count = 0;
	public static String color_var;
	public static List<Object> Faces = new ArrayList<Object>();
	public static List<Object> Vertex = new ArrayList<Object>();
	public static List<Object> Color = new ArrayList<Object>();
	static List<Double> Max = new ArrayList<Double>();
	static List<Double> Min = new ArrayList<Double>();

	public static JRadioButtonMenuItem b = NULL;
	static JRadioButtonMenuItem c = NULL;
	
	@SuppressWarnings({ })
	public static void colorMap() throws IOException{
		
		try {
			BufferedReader br = new BufferedReader(new FileReader("jet_surface.dat"));
			String line;
			
			while ((line = br.readLine()) != null){
				// process the line.
				//Checking pattern match for ZONE area.
				if (line.matches("ZONE(.*)")){
					
					Pattern pattern = Pattern.compile("(Nodes=(\\d+))");
					Pattern pattern1 = Pattern.compile("(Elements=(\\d+))");
					Matcher points = pattern.matcher(line);
					Matcher face_1 = pattern1.matcher(line);
					Vertex.clear();
					String nodes="", faces="";
					//Extracting the number of vertices and faces inside this zone.
					if (points.find() && face_1.find()){
						nodes = points.group(2);
						faces = face_1.group(2);
					}
					
					//Going over all the vertices of this zone.
					for (int i=0; i < Integer.parseInt(nodes); i++){
						String[] v = br.readLine().split(" ");
						//System.out.println(Arrays.asList(v));
						
						//Storing each vertex in a data structure.
						Vertex vertex = new Vertex();
						for (int j=1; j < v.length; j++){
							double d = Double.parseDouble(v[j]);
							//Parsing the scientific notation. Converting it to decimal.
							NumberFormat formatter = new DecimalFormat("###.#####");
							float v1 = Float.parseFloat(formatter.format(d));
							vertex.vertex.add(v1);
						}
						
						
						//Checking the value of Pseudo-coloring selected by the user.
						float v2 = 0;
						if (b == null){
							v2 = vertex.vertex.get(3);
						}
						
						else{
							color_var = b.getText();
							
							switch (color_var){
								case "X": v2 = vertex.vertex.get(0); break;
								case "Y": v2 = vertex.vertex.get(1); break;
								case "Z": v2 = vertex.vertex.get(2); break;
								case "S": v2 = vertex.vertex.get(3); break;
								default: v2 = vertex.vertex.get(3); break;		
							}	
						}
						
						
						//Computing the Color-Map Index
						int CMI = (int) Math.floor((256*(((v2)-min)/(max-min))));

						//Getting the color values based on the CMI.
						Color color = (Color) Color.get(CMI);
						
						//Adding color to the vertex.
						vertex.colour.add(color.colour.get(0));
						vertex.colour.add(color.colour.get(1));
						vertex.colour.add(color.colour.get(2));
						
						//Adding vertex to the list of all vertices.
						Vertex.add(vertex);
					}
					
					
					//All vertices of the zone have been generated.
					//Going over all the faces of the zone.
					for (int i=0; i < Integer.parseInt(faces); i++){
						
						List<String> myList = new ArrayList<String>(Arrays.asList(br.readLine()
								.split(" ")));
						//Creating a new face for each face encountered.
						Face face = new Face();
						//Adding vertices to that face.
						for (int j=1; j<myList.size(); j++){
							boolean state = myList.get(j) instanceof String;
							int index = Integer.parseInt(myList.get(j));
							face.vertices.add((Vertex) Vertex.get(index-1));
						}
						//Adding face to the Face data structure.
						Faces.add(face);
						
					}
				}
				//System.out.println(count);
				//count += 1;
			}	
			br.close();	
		} 
		
		catch (IOException e) {
			System.out.println("File not found. Please put file in the same directory.");
		}
	}
	
	public void run(){
		//Resetting the variables before execution.
		max = 0;
		min = 0;
		Faces.clear();
		Max.clear();
		Min.clear();
		Vertex.clear();
		Color.clear();
		main(null);
	}
	
	public static void main( String[] args){
		try{
			BufferedReader br = new BufferedReader(new FileReader("jet_surface.dat"));
			String line;
			
			//Reading the entire file.
			while((line = br.readLine()) != null){
				
				//min = 100;
				//max = 0;
				//Checking pattern match for ZONE area.
				if (line.matches("ZONE(.*)")) {
					
					//System.out.println(line);
					//Extracting Nodes and Faces from the ZONE line.
					Pattern pattern = Pattern.compile("(Nodes=(\\d+))");
					Pattern pattern1 = Pattern.compile("(Elements=(\\d+))");
					Matcher points = pattern.matcher(line);
					Matcher face_1 = pattern1.matcher(line);
						
					String nodes="", faces="";
					//Extracting the number of faces and vertices from the pattern.
					if (points.find() && face_1.find()){
						nodes = points.group(2);
						faces = face_1.group(2);
					}
					
					
					//Going over all the vertices to compute the min and max.
					for (int i=1; i<Integer.parseInt(nodes); i++) {
						String[] v = br.readLine().split(" ");
						//System.out.println(Arrays.asList(v));
						double d = 0;
						
						NumberFormat formatter;
						
						if (b == null){
							d = Double.parseDouble(v[4]);
							formatter = new DecimalFormat("###.#####");
							d = Float.parseFloat(formatter.format(d));
						}
						
						else{
							String state = b.getText();
							switch (state){
								case "X":
									d = Double.parseDouble(v[1]);
									formatter = new DecimalFormat("###.#####");
									d = Float.parseFloat(formatter.format(d));
									break;
								case "Y":
									d = Double.parseDouble(v[2]);
									formatter = new DecimalFormat("###.#####");
									d = Float.parseFloat(formatter.format(d));
									break;
								case "Z":
									d = Double.parseDouble(v[3]);
									formatter = new DecimalFormat("###.#####");
									d = Float.parseFloat(formatter.format(d));
									break;
								default:
									d = Double.parseDouble(v[4]);
									formatter = new DecimalFormat("###.#####");
									d = Float.parseFloat(formatter.format(d));
									break;
							}
						}
						
						
						//System.out.println(d);
						//Parsing the scientific notation. Converting it to decimal.
						//NumberFormat formatter = new DecimalFormat("###.#####");
						//Calculating the min and max values for the data value selected.
						if (d < min && d > max)
						{
							min = d;	
							max = d;
						}
						else if (d > max && d > min)
						{
							max = d;
						}
						else if (d < min && d < max)
						{
							min = d;
						}
					}
					//Max.add(max);
					//Min.add(min);
					
					//Skipping over all the faces to get back to a new zone.
					for (int i=0; i<Integer.parseInt(faces); i++){
						br.readLine();
					}
				}
			}
			br.close();
			
			//System.out.println(CMI);
			
			//Reading the color map.
			BufferedReader map = new BufferedReader (new FileReader("CoolWarmFloat257.csv"));
			map.readLine();
			String color;
			//Putting all colors in a data structure for future use.
			while ((color = map.readLine()) != null ){
				Color c = new Color();
				String[] str = color.split(",");
				c.colour.add(Float.parseFloat(str[1]));
				c.colour.add(Float.parseFloat(str[2]));
				c.colour.add(Float.parseFloat(str[3]));
				Color.add(c);
			}
			map.close();	
			//System.out.println(Max);
			//System.out.println(Min);
			colorMap();
			
		}
		
		catch (IOException e){
			System.out.println("Error opening file. Please put it in the same directory");
		}
	}
}
