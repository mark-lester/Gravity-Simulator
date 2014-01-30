package force;



public class SquareRoot extends Force {
	
 	public double force(double distance, double massA, double massB, double combined_radii){
 		distance=Math.max((double)1, distance);
		return SCALE * massA * massB * Math.sqrt(distance);
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
 		distance=Math.max((double)1, distance);
		return SCALE * massA * massB * distance * Math.sqrt(distance) * 2 /3 ;
	}
}

	


