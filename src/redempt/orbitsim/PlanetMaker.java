package redempt.orbitsim;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class PlanetMaker extends JFrame {

	private static final long serialVersionUID = -6569882971892533529L;
	

	public PlanetMaker(double mx, double my) {
		super();
		Main.creating = false;
		JFrame frame = this;
		this.setSize(500, 130 + this.getInsets().top + this.getInsets().bottom);
		this.setTitle("Body maker");
		this.setIconImage(Main.icon.getImage());
		this.setLayout(null);
		this.setLocationRelativeTo(null);
		PlaceholderTextField mass = new PlaceholderTextField();
		mass.setColumns(5);
		mass.setSize(500, 25);
		mass.setLocation(0, 0);
		mass.setPlaceholder("Mass");
		mass.setVisible(true);
		this.add(mass);
		PlaceholderTextField red = new PlaceholderTextField();
		red.setLocation(0, 25);
		red.setSize(166, 25);
		red.setPlaceholder("Red");
		red.setVisible(true);
		this.add(red);
		PlaceholderTextField green = new PlaceholderTextField();
		green.setLocation(166, 25);
		green.setSize(166, 25);
		green.setPlaceholder("Green");
		green.setVisible(true);
		this.add(green);
		PlaceholderTextField blue = new PlaceholderTextField();
		blue.setLocation(332, 25);
		blue.setSize(166, 25);
		blue.setPlaceholder("Blue");
		blue.setVisible(true);
		this.add(blue);
		PlaceholderTextField xvel = new PlaceholderTextField();
		xvel.setLocation(0, 50);
		xvel.setSize(250, 25);
		xvel.setPlaceholder("X velocity");
		xvel.setVisible(true);
		this.add(xvel);
		PlaceholderTextField yvel = new PlaceholderTextField();
		yvel.setLocation(250, 50);
		yvel.setSize(250, 25);
		yvel.setPlaceholder("Y velocity");
		yvel.setVisible(true);
		this.add(yvel);
		PlaceholderTextField name = new PlaceholderTextField();
		name.setLocation(0, 75);
		name.setSize(500, 25);
		name.setPlaceholder("Name (Optional)");
		name.setVisible(true);
		this.add(name);
		JButton create = new JButton("Create");
		create.setLocation(0, 100);
		create.setSize(500, 25);
		create.setVisible(true);
		create.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() != MouseEvent.BUTTON1) {
					return;
				}
				if (!isNumber(mass.getText()) || !isNumber(red.getText()) || !isNumber(blue.getText()) || !isNumber(green.getText()) || !isNumber(xvel.getText())  || !isNumber(yvel.getText())) {
					create.setBackground(Color.RED);
					return;
				}
				int massValue = Integer.parseInt(mass.getText());
				int redValue = Integer.parseInt(red.getText());
				int greenValue = Integer.parseInt(green.getText());
				int blueValue = Integer.parseInt(blue.getText());
				int x = Integer.parseInt(xvel.getText());
				int y = Integer.parseInt(yvel.getText());
				Color color = new Color(redValue, greenValue, blueValue);
				Vector velocity = new Vector(x, y);
				Body body = new Body(massValue, new Location(mx, my), velocity, color, name.getText().equals("") ? "(None)" : name.getText());
				Main.plane.addBody(body);
				frame.dispose();
			}
			
		});
		this.add(create);
		this.setSize(500, create.getLocation().y + (create.getSize().height * 2) + this.getInsets().top + 5);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	private boolean isNumber(String string) {
		try {
			if (string.equals("")) {
				return false;
			}
			Integer.parseInt(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}