public class GravitySuper3 extends Force {
	
 	public double force(double distance, double massA, double massB, double combined_radii){
		return SCALE * massA * massB * distance * distance * distance;
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
		return SCALE * massA * massB * distance * distance *distance *distance /4;
	}
 	public double centrifugal(double distance, double mass){
		return distance*distance*Math.sqrt(SCALE*mass);
	}
}
