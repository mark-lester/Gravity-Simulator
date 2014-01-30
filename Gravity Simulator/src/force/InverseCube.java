package force;


public class InverseCube extends Force {
	
 	public double force(double distance, double massA, double massB, double combined_radii){
 		distance=Math.copySign(Math.max(SMALL, Math.abs(distance)),distance);
		return (massA * massB) /  (distance * distance * distance);
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
 		distance=Math.copySign(Math.max(SMALL, Math.abs(distance)),distance);
		return (massA * massB) / (2 * distance * distance);
	}
}
