package force;

public class Quadratic extends Force {
 	public double force(double distance, double massA, double massB, double combined_radii){
		return massA * massB * (distance * distance -  distance);
				
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
		return massA * massB * (distance * distance * distance / 3 - 100 * distance * distance / 2);
	}
}
