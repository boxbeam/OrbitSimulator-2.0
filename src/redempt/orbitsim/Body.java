package redempt.orbitsim;

import java.awt.Color;

public class Body {
	
	public int mass;
	public Vector velocity;
	public Color color;
	public Location location;
	public String name;
	
	public Body(int mass, Location location, Vector velocity, Color color, String name) {
		this.name = name;
		this.mass = mass;
		this.velocity = velocity;
		this.color = color;
		this.location = location;
	}
	
	public Body clone() {
		return new Body(mass, location.clone(), velocity.clone(), color, name);
	}
	
}
