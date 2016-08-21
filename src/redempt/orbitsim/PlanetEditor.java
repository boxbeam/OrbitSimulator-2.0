package redempt.orbitsim;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class PlanetEditor extends JFrame {

	private static final long serialVersionUID = -96934122612056326L;
	
	public PlanetEditor(Body body) {
		JFrame frame = this;
		this.setResizable(false);
		this.setTitle("Planet editor");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setIconImage(Main.icon.getImage());
		this.setLayout(null);
		PlaceholderTextField mass = new PlaceholderTextField("" + body.mass);
		mass.setSize(300, 25);
		mass.setLocation(0, 0);
		mass.setPlaceholder("Mass");
		mass.setVisible(true);
		mass.setToolTipText("Mass");
		this.add(mass);
		PlaceholderTextField xvel = new PlaceholderTextField("" + body.velocity.x);
		xvel.setSize(150, 25);
		xvel.setLocation(0, 25);
		xvel.setPlaceholder("X velocity");
		xvel.setVisible(true);
		xvel.setToolTipText("X velocity");
		this.add(xvel);
		PlaceholderTextField yvel = new PlaceholderTextField("" + body.velocity.y);
		yvel.setSize(150, 25);
		yvel.setLocation(150, 25);
		yvel.setPlaceholder("Y velocity");
		yvel.setVisible(true);
		yvel.setToolTipText("Y velocity");
		this.add(yvel);
		PlaceholderTextField name = new PlaceholderTextField(body.name);
		name.setSize(300, 25);
		name.setLocation(0, 50);
		name.setPlaceholder("Name");
		name.setVisible(true);
		name.setToolTipText("Name");
		this.add(name);
		JButton edit = new JButton("Update");
		edit.setSize(150, 25);
		edit.setLocation(0, 75);
		edit.setVisible(true);
		edit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					if (!isNumber(mass.getText()) || !isNumber(xvel.getText()) || !isNumber(yvel.getText())) {
						edit.setBackground(Color.RED);
						return;
					}
					double xv = Double.parseDouble(xvel.getText());
					double yv = Double.parseDouble(yvel.getText());
					int m = Integer.parseInt(mass.getText());
					body.velocity.x = xv;
					body.velocity.y = yv;
					body.mass = m;
					body.name = name.getText();
					frame.dispose();
				}
			}
		});
		this.add(edit);
		JButton delete = new JButton("Delete");
		delete.setSize(150, 25);
		delete.setLocation(150, 75);
		delete.setVisible(true);
		delete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					Main.plane.removeBody(body);
					frame.dispose();
				}
			}
		});
		this.add(delete);
		this.setSize(300, delete.getLocation().y + (delete.getSize().height * 2) + this.getInsets().top + 5);
		this.setVisible(true);
	}
	
	private boolean isNumber(String string) {
		try {
			if (string.equals("")) {
				return false;
			}
			Double.parseDouble(string);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
}
