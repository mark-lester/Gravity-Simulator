package force;


public class GravitySuper extends Force {
	
 	public double force(double distance, double massA, double massB, double combined_radii){
		return SCALE * massA * massB * distance * distance ;
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
		return SCALE * massA * massB * distance * distance * distance;
	}
}

