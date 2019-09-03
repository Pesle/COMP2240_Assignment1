
public class Process {

	private String ID;
	private double arrive;
	private double execSize;
	private double turnAroundTime;
	private double startTime;
	
	
	Process(String ID, double arrive, double execSize){
		this.ID = ID;
		this.arrive = arrive;
		this.execSize = execSize;
		this.turnAroundTime = -1;
		this.startTime = -1;
	}

	public double getArrive() {
		return arrive;
	}

	public void setArrive(double arrive) {
		this.arrive = arrive;
	}

	public double getExecSize() {
		return execSize;
	}

	public void setExecSize(double execSize) {
		this.execSize = execSize;
	}

	public double getTurnAroundTime() {
		return turnAroundTime;
	}

	public void setTurnAroundTime(double turnAroundTime) {
		this.turnAroundTime = turnAroundTime;
	}

	public double getStartTime() {
		return startTime;
	}

	public void setStartTime(double startTime) {
		this.startTime = startTime;
	}
	
	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}
	
	
	

}
