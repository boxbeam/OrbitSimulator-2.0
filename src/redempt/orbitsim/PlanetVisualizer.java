package redempt.orbitsim;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class PlanetVisualizer extends JPanel {
	
	private static final long serialVersionUID = 620087334473602511L;
	private Body body;
	public Point mouse;
	
	public PlanetVisualizer(Body body) {
		super();
		mouse = new Point(0, 0);
		this.body = body;
		this.setSize(230, 50);
		this.setBackground(Color.BLACK);
		this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, false));
		this.setVisible(true);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					new PlanetEditor(body);
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					Main.camera = body.location;
				}
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(body.color);
		g.fillRect(0, 0, 30, 100);
		g.setColor(Color.WHITE);
		DecimalFormat format = new DecimalFormat("########.##");
		g.drawString("Mass: " + body.mass, 90, 15);
		g.drawString("Speed: " + format.format(body.velocity.getLength()), 90, 30);
		g.drawString("Name: " + body.name, 90, 45);
		Vector vector = body.velocity.clone();
		vector.setLength(25);
		g.fillOval(60 - 2, 25 - 2, 4, 4);
		int x = (int) vector.x;
		int y = (int) vector.y;
		g.drawLine(60, 25, 60 + x, 25 + y);
	}
}
