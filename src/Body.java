import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;


public class Body {
	
	private String name;
	private double  mass;
	private BufferedImage image;
	
	// Get methods	

	public String getName() {
		return this.name;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public double getMass() {
		return this.mass;
	}
	
	// Gets the info from a CSV file ?
	
	public Body(String name, double mass, String imagePath) {
		this.name=name;
		this.mass=mass;
		
		try {
			this.image=ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			System.out.print("Image file of a body could not be loaded.");
		}
	}
	
	public Body(String name, double mass) {
		this.name=name;
		this.mass=mass;
		
		try {
			this.image=ImageIO.read(new File("./images/default.png"));
		} catch (IOException e) {
			System.out.print("Default image file could not be loaded.");
		}
	}
	
	

}
