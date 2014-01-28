public class GravityNormal extends Force {
	
 	public double force(double distance, double massA, double massB, double combined_radii){
 		distance=Math.max(combined_radii, distance);
 		return SCALE * (massA * massB) / (distance * distance);
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
 		distance=Math.max((double)1, distance);
 		return SCALE * massA * massB / distance;
	}
 }

