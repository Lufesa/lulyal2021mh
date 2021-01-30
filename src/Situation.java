import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Situation {
	
	
	private String name = "Simple problem of a ramp-box problem without friction";

	
	private double T;
	private double m;
	private double g = 9.81;
	private double a;
	private double theta;
	private Random random = new Random();
	
	// Main variables needed, might end up putting it in the constructor tho
	public void createProblemVariables () {
		Body body = new Body("Box", random.nextDouble());
		System.out.println("I just created a "+body.getName()+" that's "+body.getMass()+" kg.");
		
	}
	
	// Solve the problem 
	public void solveProblem() {
		T = m*a+m*g*Math.sin(Math.toRadians(theta));
	}

	
	public static void main(String[] args) throws IOException{
		String row;
		String[] extract = new String[5];
		
		Situation situation = new Situation();
		situation.createProblemVariables();
		
			
	
		// Get information from an object in CSV file
		BufferedReader csvReader = new BufferedReader(new FileReader("C:\\\\Users\\\\superlufesa\\\\Desktop\\\\McHacks2021\\\\src\\\\objectData.csv"));

		while ((row = csvReader.readLine()) != null) {
		    String[] data = row.split(",");
		    System.out.println(data[0]);
		    extract = data;
		}
		csvReader.close();
		
		System.out.println(extract[1]);
		
	}
	
}
