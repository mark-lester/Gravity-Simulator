public class GravityMini extends Force {
	
 	public double force(double distance, double massA, double massB, double combined_radii){
 		distance=Math.max((double)1, distance);
 		return SCALE * massA * massB / distance; 
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
 		distance=Math.max((double)1, distance);
		return SCALE * massA * massB * Math.log(distance);
	}
 	public double centrifugal(double distance, double mass){
		return  Math.sqrt(SCALE*mass*distance*Math.log(distance));
	}
}

