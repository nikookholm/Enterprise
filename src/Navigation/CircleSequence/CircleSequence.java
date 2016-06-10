package Navigation.CircleSequence;

import java.util.ArrayList;

public class CircleSequence {
	ArrayList<String> circleSequence;
	
	public CircleSequence(int amountOfCircles){
		circleSequence = new ArrayList<String>();
		for(int i = 0; i < amountOfCircles; i++){
			if(i<10){
				circleSequence.add("P.0" + i);
			}
			circleSequence.add("P." + i);
		}
	}
	
	public ArrayList<String> getCircleSequence(){
		return circleSequence;
	}
}
