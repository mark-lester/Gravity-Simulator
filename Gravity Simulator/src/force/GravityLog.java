package force;


public class GravityLog extends Force {
	
 	public double force(double distance, double massA, double massB, double combined_radii){
 		distance=Math.max((double)3, distance);
		return SCALE * massA * massB * Math.log(distance);
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
 		distance=Math.max((double)3, distance);
		return SCALE * massA * massB * 
					(distance*Math.log(distance) - distance);
	}
}

