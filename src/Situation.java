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
		this.body = new Body("Box", random.nextDouble(), "images/penguin.png");
		try {
			this.sketch = ImageIO.read(new File(path));
		} catch (IOException e) {
			System.out.println("Situation sketch could not be loaded");
		}
		System.out.println("I just created a "+body.getName()+" that's "+body.getMass()+" kg.");
		
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
		String row;
		String[] extract = new String[5];
		
		Situation situation = new Situation();
		situation.createProblemVariables("./images/situation.png");
		
			
	
		// Get information from an object in CSV file
//		BufferedReader csvReader = new BufferedReader(new FileReader("C:\\\\Users\\\\superlufesa\\\\Desktop\\\\McHacks2021\\\\src\\\\objectData.csv"));
//
//		while ((row = csvReader.readLine()) != null) {
//		    String[] data = row.split(",");
//		    System.out.println(data[0]);
//		    extract = data;
//		}
//		csvReader.close();
//		
//		System.out.println(extract[1]);
		
		JFrame frame = new JFrame();
		situation.createFigure();
		BufferedImage sketch = addLabel(situation.getSketch(), "Given that m = " + "1.5" + "kg, and the acceleration is " + " 3 " + "m/s^2. What is the tension on the cable?");
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
		BufferedImage textImage = new BufferedImage(image.getWidth(), image.getHeight()+10*20, image.getType());
		
		String text[] = label.split("\\. ",3);
		Graphics g = textImage.getGraphics();
		g.setColor(Color.white);
		g.fillRect(0, 0, textImage.getWidth(), textImage.getHeight());
		g.setColor(Color.black);
		g.setFont(g.getFont().deriveFont(20f));
		g.drawImage(image, 0, 2*20, image.getWidth(), image.getHeight(), null);
		g.drawString("Question " + "1", 10, 20);
		int i = 0;
		for (String line : text) { 
			g.drawString(line, 10, image.getHeight()+3*20+i*20);
			i++;
		}
		
		return textImage;
	}
	
}
