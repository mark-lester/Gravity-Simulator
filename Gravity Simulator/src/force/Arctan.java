package force;

public class Arctan extends Force {
 	public double force(double distance, double massA, double massB, double combined_radii){
 		distance=Math.max((double)3, distance);
		return massA * massB * Math.atan(distance * Math.PI/solar_system_scale);
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
 		distance=Math.max((double)3, distance);
 		double x = distance*Math.PI/solar_system_scale;
		return  massA * massB * 
				(x * Math.atan(x) - 0.5 * Math.log(1 + x * x)) ;
	}
 }

