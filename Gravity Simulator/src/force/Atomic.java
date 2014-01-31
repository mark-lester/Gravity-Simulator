package force;

public class Atomic extends Force {
	private Force inverseSquare,inverseCube;
	public Atomic(){
		inverseSquare = new InverseSquare();
		inverseCube = new InverseCube();		
	}
 	public double force(double distance, double massA, double massB, double combined_radii){
		return 
				inverseSquare.force(distance,massA,massB,combined_radii) -
				inverseCube.force(distance,massA,massB,combined_radii);
				
	}
 	public double potential(double distance, double massA, double massB,double combined_radii){
		return
				inverseSquare.potential(distance,massA,massB,combined_radii) -
				inverseCube.potential(distance,massA,massB,combined_radii);
	}
}
