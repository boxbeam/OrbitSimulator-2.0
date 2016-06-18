package redempt.orbitsim;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
	
	public static Location camera = new Location(-500, -500);
	public static Plane plane = new Plane();
	public static JFrame frame = new JFrame();
	public static JFrame controls = new JFrame();
	public static ImageIcon icon;
	public static boolean creating = false;
	public static boolean deleting = false;
	public static Point mouse = new Point(0, 0);
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		frame.setSize(1000, 1000);
		icon = new ImageIcon(Main.class.getResource("res/icon.png"));
		frame.setIconImage(icon.getImage());
		frame.setResizable(false);
		frame.setTitle("Orbit Simulator 2.0");
		frame.setLocationRelativeTo(null);
		frame.setLayout(null);
		plane.setSize(1000, 1000);
		plane.setVisible(true);
		frame.add(plane);
		Body body = new Body(40, new Location(500, 500), new Vector(30, -20), Color.GREEN, "Planet");
		Body body2 = new Body(300, new Location(1100, 1100), new Vector(0, 0), Color.YELLOW, "Sun");
		plane.addBody(body);
		plane.addBody(body2);
		camera = plane.getCenterOfMass();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		MouseAdapter adapter = new MouseAdapter() {
			
			Point start = null;
			int button = -1;
			
			@Override
			public void mousePressed(MouseEvent e) {
				start = e.getPoint();
				button = e.getButton();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (creating) {
					Point p = e.getPoint();
					double x = p.x / plane.zoom;
					double y = p.y / plane.zoom;
					x += camera.x;
					y += camera.y;
					x -= 500 / plane.zoom;
					y -= 500 / plane.zoom;
					new PlanetMaker(x, y);
				}
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if (button != -1 && (button == MouseEvent.BUTTON2 || button == MouseEvent.BUTTON3)) {
					if (start == null) {
						start = e.getPoint();
					}
					double diffx = e.getPoint().x - start.x;
					double diffy = e.getPoint().y - start.y;
					camera = camera.clone().subtract(diffx / plane.zoom, diffy / plane.zoom);
					start = e.getPoint();
				}
			}
			
			@Override
			public void mouseMoved(MouseEvent e) {
				Point point = e.getPoint();
				point.y -= frame.getInsets().top;
				mouse = point;
			}
			
		};
		frame.addMouseMotionListener(adapter);
		frame.addMouseListener(adapter);
		frame.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				plane.zoom += (double) (e.getWheelRotation()) / -50;
			}
			
		});
		frame.setVisible(true);
		controls.setSize(250, 1000);
		controls.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		controls.setTitle("Controls");
		controls.setLocationRelativeTo(null);
		Point location = controls.getLocation();
		location.x += 650;
		controls.setLocation(location);
		controls.setLayout(null);
		controls.setResizable(false);
		ControlPanel panel = new ControlPanel();
		panel.setSize(250, 1000);
		controls.add(panel);
		controls.setIconImage(icon.getImage());
		controls.setVisible(true);
	}
	
	public static Color getContrastColor(Color color) {
		  double y = (299 * color.getRed() + 587 * color.getGreen() + 114 * color.getBlue()) / 1000;
		  return y >= 128 ? Color.black : Color.white;
	}
	
}
