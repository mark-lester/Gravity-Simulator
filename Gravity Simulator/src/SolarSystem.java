import java.awt.*;

public class SolarSystem {
	private Planet[] planets;
	private Planet centreOfMass;
	private static final int S=400000;
	public double total_mass = 0;
	public double centre_of_mass_x = 0;
	public double centre_of_mass_y = 0;
	public double originalEnergy = 0;
	public double potentialEnergy = 0;
	public double kineticEnergy = 0;
	public double momentumX = 0;
	public double momentumY = 0;
	public int numberOfPlanets = 0;
	public double sun_size=500000;
	public boolean sun_flag = true;
	public boolean centrifugal_flag = false;
	public boolean solar_system_flag = false;
	public double averageEnergy=1000;
	private Force gravity;
	public SolarSystem solarSystem;

	public void Draw(GravitySim gs, Graphics g) { 
		for(int i = 0; i < numberOfPlanets; i++) {
			this.planets[i].Draw(gs,g);
		}
		this.centreOfMass.Draw(gs,g);
	}

	public SolarSystem(
			int numberOfPlanets,
			int height,
			int width,
			Force gravity,
			boolean centrifugal_flag,
			double averageEnergy,
			boolean sun_flag,
			double sun_size){

		int i,j;
		Color colour;

		total_mass = 0;
		centre_of_mass_x = 0;
		centre_of_mass_y = 0;
		originalEnergy = 0;
		potentialEnergy = 0;
		kineticEnergy = 0;
		momentumX = 0;
		momentumY = 0;
		Force.SCALE=1;

		this.numberOfPlanets = numberOfPlanets;
		this.averageEnergy = averageEnergy;
		this.centrifugal_flag = centrifugal_flag;
		this.sun_flag = sun_flag;
		this.sun_size = sun_size;
		this.planets = new Planet[numberOfPlanets];
		this.gravity=gravity;

		colour=Color.RED;
		this.centreOfMass = new Planet(0,0,5,0,colour);

		for(i = 0; i < numberOfPlanets; i++) {
			double x,y,radius,mass;

			x = (Math.random()*width % (width/2)) + (width/4);
			y = (Math.random()*width % (height/2)) + (height/4);
			radius = (Math.random()*width % 15) + 1;
			colour=new Color((float)Math.random(),(float)Math.random(),(float)Math.random());

			// Set the first two up at nice spots and sizes, handy for debugging force behaviour
			if (i == 0){
				x = 3 * width / 8 ;
				y = 3 * height / 8;
				radius = 10;
				colour=Color.YELLOW;
			}
			if (i == 1){
				x = 5 * width / 8;
				y = 5 * height / 8;
				radius = 10;
				colour=Color.GREEN;
			}

			mass = radius*radius*radius;

			if (sun_flag && (i == numberOfPlanets -1) ){ // make it the last one so it renders on top
				x=width/2;
				y=height/2;
				radius = 25;
				mass = this.sun_size;
				colour = Color.WHITE;
			}
			this.planets[i] = new Planet(x,y,radius,mass,colour);
	
			this.total_mass += mass;
			this.centreOfMass.posX = this.centre_of_mass_x+=(mass/total_mass)*(x-centre_of_mass_x);
			this.centreOfMass.posY = this.centre_of_mass_y+=(mass/total_mass)*(y-centre_of_mass_y);
	    	}

		// do a dummy run to add up the potential eneGrgy at start

		for(i = 0; i < this.numberOfPlanets; i++) {
			for(j = i +1 ; j < this.numberOfPlanets; j++) {
				this.potentialEnergy += this.planets[i].Attract(this.planets[j],gravity);
			}
			// zap the velocity and acceleration back to zero after the dummy run to get the potential
			this.planets[i].velocityX=0;
			this.planets[i].velocityY=0;
			this.planets[i].deltaVelocityX=0;
			this.planets[i].deltaVelocityY=0;
		}

		// now we know the total energy, we can scale 
		// else when you bump up the number of planets, 
		// the energy in the system goes exponetially up and the animation goes bonkers.
		Force.SCALE = averageEnergy * numberOfPlanets/this.potentialEnergy;
		this.potentialEnergy=averageEnergy * numberOfPlanets;

		if (centrifugal_flag){  // get stuff to orbit the centre of mass, using the sun's mass as an attractor
			double momentum_x = 0;
			double momentum_y = 0;
			double SunMass=planets[numberOfPlanets-1].mass;

			for(i = 0; i < numberOfPlanets-1; i++) {
				// distance to centre of mass
				double distCoM = Math.sqrt(
					(planets[i].posY - centre_of_mass_y) * (planets[i].posY - centre_of_mass_y) +
					(planets[i].posX - centre_of_mass_x) * (planets[i].posX - centre_of_mass_x));
				double v=gravity.centrifugal(distCoM,sun_size);

				planets[i].velocityX += (planets[i].posY - centre_of_mass_y) * v / distCoM;
				planets[i].velocityY +=-(planets[i].posX - centre_of_mass_x) * v / distCoM;
				momentum_x += planets[i].mass * planets[i].velocityX;
				momentum_y += planets[i].mass * planets[i].velocityY;
				kineticEnergy += (planets[i].velocityX * planets[i].velocityX) + 
						(planets[i].velocityY * planets[i].velocityY) * 
						planets[i].mass/2 ;
			}
			// i = last one, which might be a sun, so shift it to balance the momentum
			planets[i].velocityX -= momentum_x / planets[i].mass;
			planets[i].velocityY -= momentum_y / planets[i].mass;
			kineticEnergy += (planets[i].velocityX * planets[i].velocityX) + 
					(planets[i].velocityY * planets[i].velocityY) * 
					planets[i].mass/2 ;
		}

		this.originalEnergy=this.potentialEnergy+this.kineticEnergy;
	}

	public void Animate(){
		int i,j;

		this.potentialEnergy = 0;
		this.kineticEnergy = 0;		
		this.momentumX = 0;
		this.momentumY = 0;
		this.centre_of_mass_x=this.planets[0].posX;
		this.centre_of_mass_y=this.planets[0].posY;
		this.total_mass = 0; // used for Centre of Mass

		for(i = 0; i < this.numberOfPlanets; i++) {
// only do this once, so for j > i. attract will work out the new incremental velocity vectors for both objects 
// and return the total potential energy
			for(j = i +1 ; j < this.numberOfPlanets; j++) {
				this.potentialEnergy += this.planets[i].Attract(this.planets[j],this.gravity);
			}

			this.planets[i].Move();
			this.total_mass += this.planets[i].mass;
			this.centreOfMass.posX = this.centre_of_mass_x+=(this.planets[i].mass/this.total_mass)*(this.planets[i].posX-this.centre_of_mass_x);
			this.centreOfMass.posY = this.centre_of_mass_y+=(this.planets[i].mass/this.total_mass)*(this.planets[i].posY-this.centre_of_mass_y);

				// add up kinetic energy
			this.kineticEnergy += (	(this.planets[i].velocityX * this.planets[i].velocityX) + 
						(this.planets[i].velocityY * this.planets[i].velocityY)) * 
						(this.planets[i].mass/2);
			this.momentumX += this.planets[i].mass * this.planets[i].velocityX;
			this.momentumY += this.planets[i].mass * this.planets[i].velocityY;
		}
	}
//EOC
}


