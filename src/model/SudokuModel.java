package model;

//
// This class describes the Model part of my MVC project.
//
public class SudokuModel{

	// timer's values
	private int timeMin = 0;
	private int timeSec = 0; 

	// returning minutes value of the timer
	public int getTimeMin(){
		return timeMin;
	}
	
	// returning seconds value of the timer
	public int getTimeSec(){
		return timeSec;
	}
	
	// setting minutes and seconds value of the timer in the beginning
	public void setTime(int value){
		timeMin = value;
		timeSec = value;
	}
	
	// incrementing timer's values
	public void incTime(){
		timeSec++;
		
		if(timeSec%60 == 0){
			timeMin++;
			timeSec = 0;
		}
	}
}
