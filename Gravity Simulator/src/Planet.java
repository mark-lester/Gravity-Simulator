import java.awt.*;

import force.Force;


public class Planet {
	public double radius = 0; // Ball's radius
	public double posX = 0; // Ball's center (x, y)
	public double posY = 0; 
	public double velocityX = 0;   // Ball's speed for x and y
	public double velocityY = 0;
	public double mass = 0;
	public double deltaVelocityX = 0;
	public double deltaVelocityY = 0;
	public double potential;
	public Color colour; 
	public boolean sun_flag=false;

	public Planet (double x,double y,double radius,double mass, Color colour){
		this.radius = radius;
		this.posX=x;
		this.posY=y;
		this.colour = colour;
		this.mass = mass;
		this.velocityX=0;
		this.velocityY=0;
   	}

	public void Draw(Simulator gs, Graphics g){
		gs.DrawPlanet(g,(int)this.posX,(int)this.posY,(int)this.radius,this.colour);
	}

	// calc the increments in acceleration, return the potential energy
	public double Attract(Planet to,Force gravity){
		double distanceX = this.posX - to.posX;
		double distanceY = this.posY - to.posY;

		double distance = Math.sqrt((distanceX * distanceX) + (distanceY * distanceY));
		double attraction = gravity.Attraction(distance,this.mass,to.mass,this.radius + to.radius);
		double potential = gravity.Potential(distance,this.mass,to.mass,this.radius + to.radius);

 		this.deltaVelocityX -= (attraction * distanceX)/(distance * this.mass);
 		this.deltaVelocityY -= (attraction * distanceY)/(distance * this.mass);
 		to.deltaVelocityX += (attraction * distanceX)/(distance * to.mass);
 		to.deltaVelocityY += (attraction * distanceY)/(distance * to.mass);
		return potential;
	}	

	// execute the movement
	public void Move(){
		this.velocityX += this.deltaVelocityX;
		this.velocityY += this.deltaVelocityY;
		this.posX += this.velocityX;
		this.posY += this.velocityY;
// we've moved, so clear down the accumulated acceleration for next time
		this.deltaVelocityX=0;
		this.deltaVelocityY=0;
	}

	// get tangential velocity req to orbit a 'mass' from a 'distance
	public double Centrifugal(double distance,double mass_planet, double mass_sun, Force gravity){
		return gravity.Centrifugal(distance,mass_planet,mass_sun);
	}
}

