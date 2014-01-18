
public class GravitySqrt extends Force {
	
 	public double force(double distance, double massA, double massB, double combined_radii){
 		distance=Math.max((double)1, distance);
		return SCALE * massA * massB * Math.sqrt(distance);
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
 		distance=Math.max((double)1, distance);
		return SCALE * massA * massB * distance * Math.sqrt(distance) * 2 /3 ;
	}
 	public double centrifugal(double distance, double mass){
		distance=Math.max((double)1,distance);
		return  Math.sqrt(SCALE*mass*distance*Math.sqrt(distance));
	}
}

	


