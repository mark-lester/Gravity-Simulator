public class GravitySHM extends Force {
	
 	public double force(double distance, double massA, double massB, double combined_radii){
		return SCALE * massA * massB * distance;
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
		return SCALE * massA * massB * distance * distance /2;
	}
 	public double centrifugal(double distance, double mass){
		return distance*Math.sqrt(SCALE*mass);
	}
}

