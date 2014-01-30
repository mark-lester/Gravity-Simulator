package force;


public class Constant extends Force {
	
 	public double force(double distance, double massA, double massB, double combined_radii){
		return massA * massB;
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
		return massA * massB * distance;
	}
}

