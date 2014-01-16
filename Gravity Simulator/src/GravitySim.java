import java.awt.*;
import java.util.Formatter;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// Extends JPanel, so as to override the paintComponent() for custom rendering codes. 
public class GravitySim extends JPanel implements ActionListener {
	// Container box's width and height
	private static int BOX_WIDTH = 700;
	private static int BOX_HEIGHT = 700;
	private static final int UPDATE_RATE = 500; // Number of refresh per second
	private int loop_counter=0;
  	public SolarSystem solarSystem;
	private Timer timer;
	private static int numberOfPlanets=400;	
	private static int averageEnergy=400;
	private static boolean centrifugal_flag=true;
	private static boolean sun_flag=true;
	private static double sun_size=50000;
	public String forceName="Normal";
	public Force gravity=null;
	double top_loss=0;
	double bot_loss=0;

	/** Constructor to create the UI components and init game objects. */
	public GravitySim(int numberOfPlanets,int width,int height,String forceName) {
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
		formatter.format("**PRESS JUST a KEY ** %s: o %d :gain(%6.0f->%6.0f)%6.0f:k %6.0f:p %6.0f:l %d",
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
		String[] force_names = {"Normal", "SHM","Mini","Constant","Log","Super","Super3"};
    		JComboBox force_names_field = new JComboBox(force_names);
		    force_names_field.setSelectedItem(forceName);
    		JTextField numberOfPlanets_field = new JTextField(Integer.toString(this.numberOfPlanets));
    		JTextField averageEnergy_field = new JTextField(Integer.toString(this.averageEnergy));
    		JTextField sun_size_field = new JTextField(Double.toString(this.sun_size));
    		JCheckBox centrifugal_flag_field = new JCheckBox("Centrifugal");
		centrifugal_flag_field.setSelected(this.centrifugal_flag);
    		JCheckBox sun_flag_field = new JCheckBox("Sun");
		sun_flag_field.setSelected(this.sun_flag);

		JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.add(new JLabel("Gravity Type"));
		panel.add(force_names_field);
		panel.add(new JLabel("Number of Planets"));
		panel.add(numberOfPlanets_field);
		panel.add(new JLabel("Average Energy"));
		panel.add(averageEnergy_field);
		panel.add(centrifugal_flag_field);
		panel.add(sun_flag_field);
		panel.add(new JLabel("Sun Size"));
		panel.add(sun_size_field);
		
		int result = JOptionPane.showConfirmDialog(this, panel, "Solar System Configuration",
					        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		if (result == JOptionPane.OK_OPTION) {
			numberOfPlanets = Integer.parseInt(numberOfPlanets_field.getText());
			averageEnergy = Integer.parseInt(averageEnergy_field.getText());
			forceName=force_names_field.getSelectedItem().toString();
			centrifugal_flag=centrifugal_flag_field.isSelected();
			sun_flag=sun_flag_field.isSelected();
			sun_size = Double.parseDouble(sun_size_field.getText());
			change=true;
		}
		return change;
   }

   private void SetSolarSystem(){
	switch (forceName){
		case "Normal":
			gravity=new GravityNormal();
			break;
		case "SHM":
			gravity=new GravitySHM();
			break;
		case "Mini":
			gravity=new GravityMini();
			break;
		case "Constant":
			gravity=new GravityConstant();
			break;
		case "Log":
			gravity=new GravityLog();
			break;
		case "Super":
			gravity=new GravitySuper();
			break;
		case "Super3":
			gravity=new GravitySuper3();
			break;
	}
	top_loss=bot_loss=0;
	this.solarSystem = new SolarSystem(numberOfPlanets, BOX_HEIGHT,BOX_WIDTH,gravity,centrifugal_flag,averageEnergy,sun_flag,sun_size);
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

}
