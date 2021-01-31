import java.io.BufferedReader;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.awt.Graphics;

public class Situation {
	
	
	private String name = "Simple problem of a ramp-box problem without friction";

	
	private double T;
	private double m;
	private double g = 9.81;
	private double a;
	private double theta;
	
	private BufferedImage sketch;
	private Body body;
	private Random random = new Random();
	
	// Main variables needed, might end up putting it in the constructor tho
	public void createProblemVariables () {
		this.body = new Body("Box", Math.round((random.nextGaussian()*0.5+2)*100.0) / 100.0);
		System.out.println("I just created a "+body.getName()+" that's "+body.getMass()+" kg.");
		this.a = Math.round((random.nextGaussian()*0.5+2)*100.0) / 100.0;
		this.theta = Math.round((random.nextGaussian()*30+45)*100.0) / 100.0;
		T = Math.round((body.getMass()*a+this.body.getMass()*g*Math.sin(Math.toRadians(theta)))*100.0)/100.0;
	}
	
	// Solve the problem 
	public void solveProblem() {
		
	}
	
	public void createFigure() {
		BufferedImage combined = new BufferedImage(sketch.getHeight(), sketch.getWidth(), BufferedImage.TYPE_4BYTE_ABGR);
		
		Graphics g = combined.getGraphics();
		g.drawImage(sketch, 0,0, null);
		g.drawImage(body.getImage(), 0,0, null);
		
		g.dispose();
		this.sketch = combined;
	}

	
	public static void main(String[] args) throws IOException{
		
		
		Situation situation = new Situation();
		situation.createProblemVariables();
		
		
		//int unknown = situation.random.nextInt(5);
		int unknown = 0;
		
		// Case where we need to find theta given everything else
		if (unknown==0) {
			System.out.println("Find the angle of the slope where the " + situation.body.getName().toLowerCase() + " with a mass of " + situation.body.getMass() + " kg is accelerating at " + situation.a +"m/(s^2). The tension in the cable is of " + situation.T + "N. Assume g = 9.81m/(s^2). The answer is theta = " + situation.theta + "." );
		} else {
			System.out.println("Something else");
		}
		
	}
	
}



/*
// Get information from an object in CSV file
String row;
String[] extract = new String[5];
BufferedReader csvReader = new BufferedReader(new FileReader("C:\\Users\\superlufesa\\Desktop\\McHacks2021\\src\\objectData.csv"));

while ((row = csvReader.readLine()) != null) {
    String[] data = row.split(",");
    System.out.println(data[0]);
    extract = data;
}
csvReader.close();

System.out.println(extract[1]);
*/
