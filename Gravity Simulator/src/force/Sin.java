package force;


public class Sin extends Force {
 	public double force(double distance, double massA, double massB, double combined_radii){
 		distance=Math.max((double)3, distance);
		return massA * massB * Math.sin(distance * Math.PI/solar_system_scale);
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
 		distance=Math.max((double)3, distance);
		return 0 -  massA * massB * Math.cos(distance*Math.PI/solar_system_scale);
	}
 }

