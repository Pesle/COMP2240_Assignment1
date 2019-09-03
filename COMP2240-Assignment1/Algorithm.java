import java.util.ArrayList;

public abstract class Algorithm {
	protected String name;
	
	protected double dispatchTime;
	protected double runTime;
	protected State state;
	
	protected Process runningProcess;
	protected ArrayList<Process> processList;
	
	public Algorithm(String name, ArrayList<Process> processList, double dispatchTime) {
		this.processList = processList;
		this.name = name;
		this.state = State.IDLE;
		this.dispatchTime = dispatchTime;
		runTime = 0;
	}
	
	public String getName() {
		return name;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
	public abstract void begin();
	
	public abstract void process();
}
