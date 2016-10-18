****USER DOCUMENTATION*****


REQUIREMENTS:
-Windows
-Eclipse or any other Java Compiler like NetBeans, IntelliJ etc.
-Java JDK 1.8.0_102 and JRE 1.8.0_101 or above.
-The following .jar files to be referenced by the Project:
	-gluegen-rt.jar
	-gluegen-rt-natives-windows-amd64.jar
	-jogl-all.jar
	-jogl-all-natives-windows-amd64.jar
	-jogl-java-src.zip
	-gluegen-java-src.zip

INSTALLING:

Instructions on running the Java Application:

1. Extract the contents into a folder.
2. Open the project in eclipse or any other Java compiler.
3. Run the Renderer.java, this should make the application run.


CAPABILITIES AND USAGE
Instructions on using the Java Application:

1. Running Renderer.java opens up a window with the title "Jet Plane".
2. You can change the setting of the window by going to the "Options" menu
   at the top.
3. This menu allows you the following:
	-Activate or Deactivate Translation
	-Change the background color.
	-Rotate the object in X,Y,Z,Free of deactivate rotation.
	-Set the Pseudo Coloring variable.
	-Switch between Wireframe and Fill models.
	-Switch between Orthographic and Perspective Projection.
	-Exit the application.

Interesting features to watch out for:

-This app allows the user to disable translation as well. So if you have lost
you jet plane, it will come back to you upon disabling rotation/translation.

-It allows users 256 possible color choices basd on RGB values, instead of just
giving a few choices like Red, Green, Blue etc.

FOR DEVELOPERS

-This application uses the MVC Architectural Pattern and is fully documented.
-It needs the standard Java library distribution along with JOGL.
-It runs fine on Windows but has not been tested on Linux or OSX because of
the lack of presence of the latter two.