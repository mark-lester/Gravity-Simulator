package force;


public class Cubed extends Force {
	
 	public double force(double distance, double massA, double massB, double combined_radii){
		return massA * massB * distance * distance * distance;
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
		return massA * massB * distance * distance *distance *distance /4;
	}
}
