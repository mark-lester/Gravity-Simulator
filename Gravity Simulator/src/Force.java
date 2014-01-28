public class Force {
public static double SCALE=1;
public double solar_system_scale=1;
public double offset=0;

	public double setOffset(double offset){
		return this.offset=offset;
	}
	
 	public double Force(double distance, double massA, double massB,double combined_radii){
		return force(distance-offset, massA, massB,combined_radii);
	}
 	public double Potential(double distance, double massA, double massB, double combined_radii){
		return potential(distance-offset, massA, massB, combined_radii);
	}
 	public double Centrifugal(double distance, double massA,double massB){ 		
		return Math.sqrt(
					Math.max(
							0,	
							distance * Force(distance,massA,massB,0)/ massA  
							)
						);
	}
 	public double force(double distance, double massA, double massB,double combined_radii){
		return 0;
	}
 	public double potential(double distance, double massA, double massB, double combined_radii){
		return 0;
	}
}



