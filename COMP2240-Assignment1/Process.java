/*
 *  ----C3282137----
 *  Ryan Jobse
 *  COMP2240 S2 2019
 *  Assignment 1
 *  
 *  Process.java
 *  Process stores information 
 *  about each process
 */

public class Process  {

//Variables
	protected String ID;
	protected int arrive;
	protected int execSize;
	protected int turnAroundTime;
	protected int startTime;
	protected int waitingTime;
	protected int timeRemaining;
	
//Initializes
	Process(String ID, int arrive, int execSize){
		this.ID = ID;
		this.arrive = arrive;
		this.execSize = execSize;
		this.timeRemaining = execSize;
		this.turnAroundTime = 0;
		this.waitingTime = 0;
		this.startTime = 0;
	}
	
	Process(String ID, int arrive, int execSize, int timeRemaining){
		this.ID = ID;
		this.arrive = arrive;
		this.execSize = execSize;
		this.timeRemaining = timeRemaining;
		this.turnAroundTime = 0;
		this.waitingTime = 0;
		this.startTime = 0;
	}

//Setters and Getters
	public int getArrive() {
		return arrive;
	}

	public void setArrive(int arrive) {
		this.arrive = arrive;
	}

	public int getExecSize() {
		return execSize;
	}

	public void setExecSize(int execSize) {
		this.execSize = execSize;
	}

	public int getTurnAroundTime() {
		return turnAroundTime;
	}

	public void setTurnAroundTime(int turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}

	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}
	
	public int getWaitingTime() {
		return waitingTime;
	}
	
	public void setWaitingTime(int waitingTime) {
		this.waitingTime = new Integer(waitingTime);
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}
	
	public int getTimeRemaining() {
		return timeRemaining;
	}
	
	public void setTimeRemaining(int timeRemaining) {
		this.timeRemaining = timeRemaining;
	}
	
//Copy the current process without extra information
	public Process copy() {
		return new Process(ID, arrive, execSize, timeRemaining);
	}
	
//Copy the full process with all information
	public Process fullCopy() {
		Process newProcess = new Process(ID, arrive, execSize, timeRemaining);
		newProcess.setTurnAroundTime(turnAroundTime);
		newProcess.setWaitingTime(waitingTime);
		newProcess.setStartTime(startTime);
		return newProcess;
	}

}
