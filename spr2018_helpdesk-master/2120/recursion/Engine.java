public class Engine{
	
	public void advance(double howFar){
		itsDistanceFromHome += howFar;
		nextCar.advance(howFar);

	}
	
	public void isMemberOfTrain(){
		if (nextCar == null){
			return true;
		}
		else{
			return isMemberOfTrain(nextCar);
		}

	}
}