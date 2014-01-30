package force;


public class DirectlyProportional extends Force {
	
 	public double force(double distance, double massA, double massB, double combined_radii){
		return massA * massB * distance;
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
		return massA * massB * distance * distance /2;
	}
}

