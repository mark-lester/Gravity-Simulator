import java.awt.*;
import java.util.Formatter;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Enumeration;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.LinkedList;
import org.reflections.*;
import org.reflections.util.*;
import org.reflections.scanners.*;

import java.io.IOException;
import java.io.File;
import java.net.URL;

import force.*;


// Extends JPanel, so as to override the paintComponent() for custom rendering codes. 
public class Simulator extends JPanel implements ActionListener {
	// Container box's width and height
	private static int BOX_WIDTH = 700;
	private static int BOX_HEIGHT = 700;
	private static final int UPDATE_RATE = 500; // Number of refresh per second
	private int loop_counter=0;
  	public SolarSystem solarSystem;
	private Timer timer;
	private static int numberOfPlanets=10;	
	private static double averageEnergy=1;
	private static boolean centrifugal_flag=true;
	private static boolean sun_flag=true;
	private static double sun_size=500000;
	private boolean random_radii_flag=true;
	private boolean random_position_flag=true;
	private boolean random_colour_flag=true;
	public String forceName="Normal";
	public Force gravity=null;
	public double forceOffset=0;
	double top_loss=0;
	double bot_loss=0;

	/** Constructor to create the UI components and init game objects. */
	public Simulator(int numberOfPlanets,int width,int height,String forceName) {
		this.numberOfPlanets = numberOfPlanets;
		BOX_WIDTH=width;
		BOX_HEIGHT=height;
		this.forceName=forceName;
		SetSolarSystem();
	 	setPreferredSize(new Dimension(BOX_WIDTH, BOX_HEIGHT));
		addKeyListener(new TAdapter());
        	setFocusable(true);
		timer = new Timer(1000 / UPDATE_RATE, this);
		timer.start();
	}

	public void actionPerformed(ActionEvent e) {
	        solarSystem.Animate();
	        repaint();  
	}


  
/** Custom rendering codes for drawing the JPanel */
	public void DrawPlanet(Graphics g,int x,int y, int radius,Color colour){
		g.setColor(colour);
		g.fillOval(x - radius,y - radius, 2 * radius,2 * radius);
	}
@Override
	public void paint(Graphics g) {
		super.paintComponent(g);    // Paint background
  
		// Draw the box
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, BOX_WIDTH, BOX_HEIGHT);
  
		// Draw the planets
		this.solarSystem.Draw(this,g);
		double loss = this.solarSystem.kineticEnergy + this.solarSystem.potentialEnergy - this.solarSystem.originalEnergy;
		top_loss = Math.max(top_loss,loss);
		bot_loss = Math.min(bot_loss,loss);
		// Display the information line
		g.setColor(Color.WHITE);
		g.setFont(new Font("Courier New", Font.PLAIN, 12));
		StringBuilder sb = new StringBuilder();
		Formatter formatter = new Formatter(sb);
		formatter.format("**JUST PRESS A KEY ** %s: o %d :gain(%6.0f->%6.0f)%6.0f:k %6.0f:p %6.0f:l %d",
                                gravity.getClass().getName(),
				this.solarSystem.numberOfPlanets,
				bot_loss,
				top_loss,
				loss,
				this.solarSystem.kineticEnergy,
				this.solarSystem.potentialEnergy,
				this.loop_counter++
				 );
		g.drawString(sb.toString(), 0, 12);
		formatter.close();
   }

   public boolean Dialogue(){
	boolean change=false;
//	ArrayList<String> force_names = new ArrayList<String>();
	String[] force_names = {"InverseCube","InverseSquare", "Inverse","InverseSquareRoot","SquareRoot","Sin","Constant","Log","DirectlyProportional","Squared","Cubed"};

    /*
	try {
	  Class forces[]=getClasses("force");
	  for (Class c : forces){
		  force_names.add(c.getName());
	  }	  
	} catch (IOException e) {
		;
		//something freaked out reading classes
	} catch (ClassNotFoundException e) {
		;//something freaked out reading package
	}
	*/
	
    		JComboBox force_names_field = new JComboBox(force_names);
		    force_names_field.setSelectedItem(forceName);
    		JTextField forceOffset_field = new JTextField(Double.toString(this.forceOffset));
    		JTextField numberOfPlanets_field = new JTextField(Integer.toString(this.numberOfPlanets));
    		JTextField averageEnergy_field = new JTextField(Double.toString(this.averageEnergy));
    		JTextField sun_size_field = new JTextField(Double.toString(this.sun_size));
    		JCheckBox centrifugal_flag_field = new JCheckBox("Centrifugal");
    		centrifugal_flag_field.setSelected(this.centrifugal_flag);
    		JCheckBox sun_flag_field = new JCheckBox("Sun");
    		sun_flag_field.setSelected(this.sun_flag);
    		JCheckBox random_radii_flag_field = new JCheckBox("Random Radii");
    		random_radii_flag_field.setSelected(this.random_radii_flag);
    		JCheckBox random_position_flag_field = new JCheckBox("Random Position");
    		random_position_flag_field.setSelected(this.random_position_flag);
    		JCheckBox random_colour_flag_field = new JCheckBox("Random Colour");
    		random_colour_flag_field.setSelected(this.random_colour_flag);

		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(new JLabel("Gravity Type (select your own gravity)"));
		panel.add(force_names_field);
		panel.add(new JLabel("Force offset"));
		panel.add(forceOffset_field);		
		panel.add(new JLabel("Number of Planets"));
		panel.add(numberOfPlanets_field);
		panel.add(new JLabel("Average Energy (increase to go faster)"));
		panel.add(averageEnergy_field);
		panel.add(centrifugal_flag_field);
		panel.add(sun_flag_field);
		panel.add(new JLabel("Sun Size (increase to stablise solar system"));
		panel.add(sun_size_field);
		panel.add(random_radii_flag_field);
		panel.add(random_position_flag_field);
		panel.add(random_colour_flag_field);
		
		int result = JOptionPane.showConfirmDialog(this, panel, "Solar System Configuration",
					        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			numberOfPlanets = Integer.parseInt(numberOfPlanets_field.getText());
			forceOffset = Double.parseDouble(forceOffset_field.getText());
			averageEnergy = Double.parseDouble(averageEnergy_field.getText());
			forceName=force_names_field.getSelectedItem().toString();
			centrifugal_flag=centrifugal_flag_field.isSelected();
			sun_flag=sun_flag_field.isSelected();
			sun_size = Double.parseDouble(sun_size_field.getText());
			random_radii_flag=random_radii_flag_field.isSelected();
			random_position_flag=random_position_flag_field.isSelected();
			random_colour_flag=random_colour_flag_field.isSelected();

			change=true;
		}
		return change;
   }

   private void SetSolarSystem(){
	switch (forceName){
		case "Sin":
			gravity=new Sin();
			gravity.solar_system_scale=BOX_WIDTH/8;
			break;
		case "InverseCube":
			gravity=new InverseCube();
			break;
		case "InverseSquareRoot":
			gravity=new InverseSquareRoot();
			break;
		case "Constant":
			gravity=new Constant();
			break;
		case "InverseSquare":
			gravity=new InverseSquare();
			break;
		case "SquareRoot":
			gravity=new SquareRoot();
			break;
		case "DirectlyProportional":
			gravity=new DirectlyProportional();
			break;
		case "Inverse":
			gravity=new Inverse();
			break;
		case "Log":
			gravity=new Log();
			break;
		case "Squared":
			gravity=new Squared();
			break;
		case "Cubed":
			gravity=new Cubed();
			break;
	}
	gravity.setOffset(forceOffset);

	top_loss=bot_loss=0;
	loop_counter=0;
	this.solarSystem = new SolarSystem(numberOfPlanets, 
								BOX_HEIGHT,
								BOX_WIDTH,
								gravity,
								centrifugal_flag,
								averageEnergy,
								sun_flag,
								sun_size,
								random_radii_flag,
								random_position_flag,
								random_colour_flag);
   }


   private class TAdapter extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
//dont care, just hit the key booard
		if (Dialogue()){
			SetSolarSystem();
		}
	}
   }
   
   private static Class[] getClasses(String packageName) throws ClassNotFoundException, IOException {
	     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	     assert classLoader != null;
	     String path = packageName.replace('.', '/');
	     Enumeration<URL> resources = classLoader.getResources(path);
	     List<File> dirs = new ArrayList<File>();
	     while (resources.hasMoreElements()) {
	    	 URL resource = resources.nextElement();
	    	 dirs.add(new File(resource.getFile()));
	     }
	     ArrayList<Class> classes = new ArrayList<Class>();
	     for (File directory : dirs) {
	    	 classes.addAll(findClasses(directory, packageName));
	     }
	     return classes.toArray(new Class[classes.size()]);
	}
   
   private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException {
	   List<Class> classes = new ArrayList<Class>();
	   if (!directory.exists()) {
		   return classes;
	   }
	   File[] files = directory.listFiles();
	   for (File file : files) {
		   if (file.isDirectory()) {
			   assert !file.getName().contains(".");
			   classes.addAll(findClasses(file, packageName + "." + file.getName()));
		   } else if (file.getName().endsWith(".class")) {
			   classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
		   }
	   }
	   return classes;
   }
}
