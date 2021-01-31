import java.io.BufferedReader;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;

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
	
	public void createProblemVariables(String path) {
		this.body = new Body("Box", Math.round((random.nextGaussian()*0.5+2)*100.0) / 100.0, "images/banana.png");
		try {
			this.sketch = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Situation sketch could not be loaded");
		}
		
		this.a = Math.round((random.nextGaussian()*8+4-this.g*Math.sin(Math.toRadians(theta)))*100.0) / 100.0;
		this.theta = Math.round((random.nextGaussian()*20+45)*100.0) / 100.0;
		T = Math.round((body.getMass()*a+this.body.getMass()*g*Math.sin(Math.toRadians(theta)))*100.0)/100.0;
		
		System.out.println("I just created a "+body.getName()+" that's "+body.getMass()+" kg."+this.theta);
		
		try {
			this.sketch = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Situation sketch could not be loaded");
		}
		
		
	}
	
	public BufferedImage getSketch() {
		return this.sketch;
	}
	
	// Solve the problem 
	public void solveProblem() {
		T = m*a+m*g*Math.sin(Math.toRadians(theta));
	}
	
	public void createFigure() {
		BufferedImage combined = new BufferedImage(sketch.getWidth(), sketch.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = combined.getGraphics();
		
		BufferedImage image = scaleImage(body.getImage(), 200,100);
		image = rotateImage(image, -20);
		g.drawImage(sketch, 0,0, null);
		g.drawImage(image, 487-image.getWidth()/2, 164-image.getHeight()/2, null);
		
		g.dispose();
		this.sketch = combined;
	}

	
	public static void main(String[] args) throws IOException{
		Situation situation = new Situation();
		situation.createProblemVariables("./images/situation.png");
		JFrame frame = new JFrame();
		situation.createFigure();
		BufferedImage sketch = addLabel(situation.getSketch(), situation.printQuestion());
		
		try {
			File out = new File("images/Problem.png");
			ImageIO.write(sketch,"png", out);
			System.out.println("File created");
		} catch(IOException e) {
			System.out.println("Could not create a png image");
		}
		
		frame.add(new Sketch(sketch));
		frame.setSize(new Dimension(1080, 1080));
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
	
	private BufferedImage rotateImage(BufferedImage image, double angdeg) {
		
		// Copied from https://blog.idrsolutions.com/2019/05/image-rotation-in-java/
		final double rads = Math.toRadians(angdeg);
		final double sin = Math.abs(Math.sin(rads));
		final double cos = Math.abs(Math.cos(rads));
		final int w = (int) Math.floor(image.getWidth() * cos + image.getHeight() * sin);
		final int h = (int) Math.floor(image.getHeight() * cos + image.getWidth() * sin);
		final BufferedImage rotatedImage = new BufferedImage(w, h, image.getType());
		final AffineTransform at = new AffineTransform();
		at.translate(w / 2, h / 2);
		at.rotate(rads,0, 0);
		at.translate(-image.getWidth() / 2, -image.getHeight() / 2);
		final AffineTransformOp rotateOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		rotateOp.filter(image,rotatedImage);
		
		return rotatedImage;
	}
	
	private BufferedImage scaleImage(BufferedImage image, int wlim,int hlim) {
		double wfactor = (double)wlim / image.getWidth();
		double hfactor = (double)hlim / image.getHeight();
		double factor = Math.min(wfactor, hfactor);
		
		BufferedImage scaledImage = new BufferedImage(wlim, hlim, image.getType());
		
		AffineTransform at = new AffineTransform();
		at.scale(factor, factor);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		scaleOp.filter(image, scaledImage);
		
		return scaledImage;
	}
	
	private static class Sketch extends JPanel{
		public BufferedImage image;
		public Sketch(BufferedImage image) {
			super();
			this.image = image;
		}
		public void paintComponent(Graphics g) {
			g.drawImage(image, 0, 0, null);
			repaint();
		}
	}
	
	private static BufferedImage addLabel(BufferedImage image, String label) {
		int lineSize = 15;
		BufferedImage textImage = new BufferedImage(image.getWidth(), image.getHeight()+10*lineSize, image.getType());
		
		String text[] = label.split("\\. ",3);
		Graphics g = textImage.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, textImage.getWidth(), textImage.getHeight());
		g.setColor(Color.black);
		g.setFont(g.getFont().deriveFont(lineSize));
		g.drawImage(image, 0, 2*lineSize, image.getWidth(), image.getHeight(), null);
		g.drawString("Question " + "1", 10, lineSize);
		int i = 0;
		for (String line : text) { 
			g.drawString(line, 10, image.getHeight()+3*lineSize+i*lineSize);
			i++;
		}
		
		return textImage;
	}
	
	public String printQuestion() {
		int unknown = random.nextInt(3); 
		String output = "empty";
		
		
				//Case where we need to find theta given everything else
			if (unknown==0) {
					output = "Find the angle of the slope on which the " + this.body.getName().toLowerCase() + " with a mass of " +
			this.body.getMass() + " kg is accelerating at " + Math.abs(this.a) +"m/(s^2)";
				if (a<0) {
					output = output + " downwards. The tension in the cable is of " + this.T + "N. Assume g = 9.81m/(s^2). The answer is theta = " + this.theta + ".";
				} else {
					output = output + " upwards. The tension in the cable is of " + this.T + "N. Assume g = 9.81m/(s^2). The answer is theta = " + this.theta + ".";
				}
			} else if (unknown == 1) {
				unknown --;
				String[][] data = { {" mass ",String.valueOf(this.body.getMass())},
									{" angle theta",String.valueOf(this.theta)}, 
									{" tension of the cable ", String.valueOf(this.T) } };
				
				output = "Find the"+ data[unknown][0] +"of the "+this.body.getName().toLowerCase()+ " on the slope with the" + data[unknown+1][0]+" = " + data[unknown+1][1] + " degrees. The"+data[unknown +2][0] +
						"is of " + this.T + "N and the acceleration of the "+this.body.getName().toLowerCase()+ " is of "+Math.abs(this.a) +"m/(s^2)";
							if (a<0) {
								output = output + " downwards. Assume g = 9.81m/(s^2)." ;
							} else {
								output = output + " upwards. Assume g = 9.81m/(s^2)." ;
							} 

				}				
				else if (unknown == 2) {
				unknown --;
				String[][] data = { {" mass ",String.valueOf(this.body.getMass())},
									{" angle theta",String.valueOf(this.theta)}, 
									{" tension of the cable", String.valueOf(this.T) } };
				
				output = "Find the acceleration of the "+this.body.getName().toLowerCase()+ " on the slope with the angle theta = " + data[unknown+1][1] + " degrees. The tension in the cable is of " +
						 + this.T + "N and the mass is of "+this.body.getMass() +" kg.";
				} else if (unknown == 3) {
					
					String[][] data = { {" mass ",String.valueOf(this.body.getMass())},
										{" angle theta",String.valueOf(this.theta)}, 
										{" tension of the cable", String.valueOf(this.T) } };
					
					output = "Find the tension in the cable pulling the "+this.body.getName().toLowerCase()+ " on the slope with the" + data[1][0]+" = " + data[1][1] +
							" degrees. The mass of the "+this.body.getName().toLowerCase()+" is of "+this.body.getMass() +"kg and the acceleration is of "+this.a+" m/(s^2)";
								if (a<0) {
									output = output + " downwards. Assume g = 9.81m/(s^2)." ;
								} else {
									output = output + " upwards. Assume g = 9.81m/(s^2)." ;
								}
					}
			
			
			
			return output;
	}
}
	
