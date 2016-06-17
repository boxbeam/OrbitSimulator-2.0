package redempt.orbitsim;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSlider;

import redempt.orbitsim.scheduler.Task;

public class ControlPanel extends JPanel {

	private static final long serialVersionUID = 6312311327636671689L;
	JButton pause = new JButton();
	JButton momentum = new JButton();
	JSlider slider = new JSlider();
	int scale = 50;
	JButton resetScale = new JButton();
	JButton centerOfMass = new JButton();
	JButton resetTimeScale = new JButton();
	JButton create = new JButton();
	JSlider time = new JSlider();
	int timescale = 25;

	public ControlPanel() {
		this.setBackground(Color.WHITE);
		this.setVisible(true);
		this.setLayout(null);
		pause.setText("Unpause");
		pause.setSize(250, 50);
		pause.setLocation(0, 0);
		pause.setVisible(true);
		pause.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					Main.plane.paused = !Main.plane.paused;
					if (Main.plane.paused) {
						pause.setText("Unpause");
					} else {
						pause.setText("Pause");
					}
				}
			}
		});
		this.add(pause);
		momentum.setText("Normalize momentum");
		momentum.setSize(250, 50);
		momentum.setLocation(0, 50);
		momentum.setVisible(true);
		momentum.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					Main.plane.normalizeMomentum();
				}
			}
		});
		this.add(momentum);
		centerOfMass.setText("Center of mass");
		centerOfMass.setSize(250, 50);
		centerOfMass.setLocation(0, 100);
		centerOfMass.setVisible(true);
		centerOfMass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					Main.camera = Main.plane.getCenterOfMass();
				}
			}
		});
		this.add(centerOfMass);
		slider.setSize(250, 60);
		slider.setLocation(0, 150);
		slider.setVisible(true);
		slider.setToolTipText("Zoom");
		Task update = new Task(new Runnable() {
			@Override
			public void run() {
				if (scale != slider.getValue()) {
					scale = slider.getValue();
					Main.plane.zoom = ((double) (slider.getValue()) / 50) + 0.01;
				} else {
					scale = (int) (Main.plane.zoom * 50);
					slider.setValue(scale);
				}
			}
		}, true, 40);
		update.start();
		this.add(slider);
		resetScale.setText("Reset zoom");
		resetScale.setSize(250, 50);
		resetScale.setLocation(0, 210);
		resetScale.setVisible(true);
		resetScale.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					Main.plane.zoom = 1;
				}
			}
		});
		this.add(resetScale);
		time.setSize(250, 60);
		time.setLocation(0, 260);
		time.setVisible(true);
		time.setValue(25);
		time.setToolTipText("Time scale");
		Task updatetime = new Task(new Runnable() {
			@Override
			public void run() {
				if (timescale != time.getValue()) {
					timescale = time.getValue();
					Main.plane.timescale = ((double) (time.getValue()) / 25) + 0.01;
				} else {
					timescale = (int) (Main.plane.timescale * 25);
					time.setValue(timescale);
				}
			}
		}, true, 40);
		updatetime.start();
		this.add(time);
		resetTimeScale.setText("Reset time scale");
		resetTimeScale.setSize(250, 50);
		resetTimeScale.setLocation(0, 320);
		resetTimeScale.setVisible(true);
		resetTimeScale.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					Main.plane.timescale = 0.5;
				}
			}
		});
		this.add(resetTimeScale);
		create.setText("Create body");
		create.setSize(250, 50);
		create.setLocation(0, 370);
		create.setVisible(true);
		create.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					Main.deleting = false;
					Main.creating = true;
				}
			}
		});
		this.add(create);
		JPanel bodies = new JPanel();
		bodies.setSize(250, 580);
		bodies.setLocation(0, 420);
		bodies.setBackground(Color.GRAY);
		bodies.setLayout(null);
		bodies.setVisible(true);
		JScrollBar bar = new JScrollBar();
		bar.setVisible(true);
		bar.setLocation(230, 0);
		bar.setSize(15, 550);
		bodies.add(bar);
		List<Body> old = new ArrayList<>();
		List<PlanetVisualizer> planets = new ArrayList<>();
		Task render = new Task(new Runnable() {
			@Override
			public void run() {
				if (old.containsAll(Main.plane.bodies) && Main.plane.bodies.containsAll(old)) {
					bodies.repaint();
					int pos = 0;
					for (PlanetVisualizer planet : planets) {
						planet.setLocation(0, (pos * 50) - (bar.getValue() * old.size() * 2));
						pos++;
					}
				} else {
					old.clear();
					old.addAll(Main.plane.bodies);
					planets.clear();
					for (Body body : Main.plane.bodies) {
						PlanetVisualizer planet = new PlanetVisualizer(body);
						planets.add(planet);
					}
					bodies.removeAll();
					for (PlanetVisualizer visualizer : planets) {
						bodies.add(visualizer);
					}
					bodies.add(bar);
					bodies.repaint();
				}
			}
		}, true, 40);
		render.start();
		this.add(bodies);
	}

}
