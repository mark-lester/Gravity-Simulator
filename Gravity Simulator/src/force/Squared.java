package force;

public class Squared extends Force {
	
 	public double force(double distance, double massA, double massB, double combined_radii){
		return massA * massB * distance * distance ;
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
		return massA * massB * distance * distance * distance;
	}
}

