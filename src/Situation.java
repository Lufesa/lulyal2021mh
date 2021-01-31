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
		this.body = new Body("Box", random.nextDouble());
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
		BufferedImage combined = new BufferedImage(sketch.getHeight(), sketch.getWidth(), BufferedImage.TYPE_4BYTE_ABGR);
		Graphics g = combined.getGraphics();
		
		BufferedImage image = rotateImage(body.getImage(), 20);
		g.drawImage(sketch, 0,0, null);
		g.drawImage(image, 610-image.getHeight()/2, 420-image.getWidth()/2, null);
		
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
		frame.add(new Sketch(situation.getSketch()));
		frame.setSize(new Dimension(1080, 1080));
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
		
		return image;
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
	
}
