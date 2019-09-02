
public class CPU {
	//Variables
	private double duration;
	private double currentDuration;
	private Process process;
	
	CPU(double duration, Process process){
		this.duration = duration;
		this.currentDuration = duration;
		this.process = process;
	}
	
	//Decrease the current duration
	public void decrementDuration(double duration) {
		currentDuration -= duration;
	}
	
	public double getDuration() {
		return duration;
	}
	public double getCurrentDuration() {
		return currentDuration;
	}
	public Process getProcess() {
		return process;
	}
}
