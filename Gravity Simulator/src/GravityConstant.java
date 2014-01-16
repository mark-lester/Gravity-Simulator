public class GravityConstant extends Force {
	
 	public double force(double distance, double massA, double massB, double combined_radii){
		return SCALE * massA * massB;
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
		return SCALE * massA * massB * distance;
	}
 	public double centrifugal(double distance, double mass){
		return Math.sqrt(SCALE*mass*distance);
	}
}

