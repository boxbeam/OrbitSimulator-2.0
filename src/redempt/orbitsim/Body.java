package redempt.orbitsim;

import java.awt.Color;

public class Body {
	
	public int mass;
	public Vector velocity;
	public Color color;
	public Location location;
	
	public Body(int mass, Location location, Vector velocity, Color color) {
		this.mass = mass;
		this.velocity = velocity;
		this.color = color;
		this.location = location;
	}
	
}
