package redempt.orbitsim;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.JPanel;

import redempt.orbitsim.scheduler.Task;

public class Plane extends JPanel {

	private static final long serialVersionUID = -7052828097855730005L;
	public List<Body> bodies = new CopyOnWriteArrayList<>();
	public boolean paused = true;
	public Task task;
	public double zoom = 1;
	public double timescale = 0.5;
	
	public Plane() {
		this.setBackground(Color.BLACK);
		this.setLayout(null);
		Plane plane = this;
		task = new Task(new Runnable() {
			
			@Override
			public void run() {
				if (!paused) {
					loop:
					for (Body body : bodies) {
						for (Body body2 : bodies) {
							if (!body.equals(body2)) {
								int radius = (int) Math.sqrt(body.mass / Math.PI);
								radius *= 10;
								int radius2 = (int) Math.sqrt(body2.mass / Math.PI);
								radius2 *= 10;
								double combined = radius + radius2;
								combined *= 0.6;
								if (body.location.distance(body2.location) <= combined) {
									if (body.mass > body2.mass) {
										body2.color = body.color;
									}
									Vector momentum = body.velocity.clone().multiply(body.mass);
									momentum.divide(body2.mass);
									body2.velocity.add(momentum);
									body2.mass += body.mass;
									bodies.remove(body);
									continue loop;
								}
								Vector v = body2.location.toVector().clone().subtract(body.location.toVector());
								double force = (body.mass * body2.mass) / Math.pow(v.getLength(), 2);
								force *= 10;
								v.setLength(force * timescale);
								double momentum = v.getLength() * body.mass;
								momentum /= body2.mass;
								Vector v2 = v.clone().setLength(momentum).invert();
								body.velocity.add(v);
								body2.velocity.add(v2);
							}
						}
						body.location.add(body.velocity.clone().multiply(timescale));
					}
				}
				plane.repaint();
			}
			
		}, true, 30);
		task.start();
	}
	
	public void addBody(Body body) {
		bodies.add(body);
	}
	
	public void removeBody(Body body) {
		bodies.remove(body);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for (Body body : bodies) {
			int radius = (int) Math.sqrt(body.mass / Math.PI);
			radius *= 10;
			g.setColor(body.color);
			double x = body.location.x * zoom;
			double y = body.location.y * zoom;
			x -= Main.camera.x * zoom;
			y -= Main.camera.y * zoom;
			x -= (radius / 2) * zoom;
			y -= (radius / 2) * zoom;
			x += 500;
			y += 500;
			g.fillOval((int) x, (int) y, (int) (radius * zoom), (int) (radius * zoom));
		}
	}
	
	public void normalizeMomentum() {
		double mass = 0;
		Vector momentum = null;
		for (Body b : bodies) {
			mass += b.mass;
			if (momentum == null) {
				momentum = b.velocity.clone().multiply(b.mass);
			} else {
				momentum.add(b.velocity.clone().multiply(b.mass));
			}
		}
		if (momentum != null) {
			for (Body b : bodies) {
				b.velocity.add(momentum.clone().divide(mass).invert());
			}
		}
	}
	
	public Location getCenterOfMass() {
		Location loc = new Location(0, 0);
		double mass = 0;
		for (Body body : bodies) {
			loc.add(body.location.clone().multiply(body.mass));
			mass += body.mass;
		}
		loc.divide(mass);
		return loc;
	}
	
}
