
public class Process {

	private String ID;
	private int arrive;
	private int execSize;
	private int turnAroundTime;
	private int startTime;
	private int endTime;
	private int timeRemaining;
	
	
	Process(String ID, int arrive, int execSize){
		this.ID = ID;
		this.arrive = arrive;
		this.execSize = execSize;
		this.timeRemaining = execSize;
		this.turnAroundTime = 0;
		this.endTime = 0;
		this.startTime = 0;
	}
	
	Process(String ID, int arrive, int execSize, int timeRemaining){
		this.ID = ID;
		this.arrive = arrive;
		this.execSize = execSize;
		this.timeRemaining = timeRemaining;
		this.turnAroundTime = -1;
		this.startTime = -1;
	}

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
	
	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
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
	
	public boolean isDone() {
		assert getTimeRemaining() >= 0; //Time remaining should not be negative
		
		return (getTimeRemaining() == 0) ? true : false;
	}
	

}
