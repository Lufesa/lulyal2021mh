
public class Body {
	
	private String name;
	private double  mass;
	private String image;
	
	// Get methods
	

	public String getName() {
		String output = this.name;
		return output;
	}
	
	public String getImage() {
		String output = this.image;
		return output;
	}
	
	public double getMass() {
		double output = this.mass;
		return output;
	}
	
	// Gets the info from a CSV file ?
	
	public Body(String name, double mass, String URL) {
		this.name=name;
		this.mass=mass;
		this.image=URL;
	}
	
	public Body(String name, double mass) {
		this.name=name;
		this.mass=mass;
		this.image="DEFAULT BOX URL";
	}
	
	

}
