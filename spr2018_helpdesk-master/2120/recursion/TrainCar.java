public abstract class TrainCar{
	protected double itsDistanceFromHome;
	protected TrainCar itsNextConnectedCar;

	public TrainCar (TrainCar nextCar){
		itsNextConnectedCar = nextCar;
		itsDistanceFromHome = 0.0;

	}

	public abstract void advance( double howFar);
	public abstract boolean isMemberOfTrain();
	
}