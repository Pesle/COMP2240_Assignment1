/*
 *  ----C3282137----
 *  Ryan Jobse
 *  COMP2240 S2 2019
 *  Assignment 1
 *  
 *  NRRProcess.java
 *  Extension of Process that adds 
 *  Quantums to each process
 */

public class NRRProcess extends Process{
	
//Final Variables
	private static final int START_QUANTUM = 4;
	private static final int MINIMUM_QUANTUM = 2;
	
//Variables
	private int quantum;
	
//Initialize
	NRRProcess(String ID, int arrive, int execSize) {
		super(ID, arrive, execSize);
		this.quantum = START_QUANTUM;
	}
	
	NRRProcess(String ID, int arrive, int execSize, int timeRemaining, int quantum) {
		super(ID, arrive, execSize, timeRemaining);
		this.quantum = quantum;
	}

//Decrease quantum until minimum
	void decreaseQuantum() {
		if(quantum > MINIMUM_QUANTUM) {
			this.quantum--;
		}
	}
	
//Return Quantum
	int getQuantum() {
		return quantum;
	}
	
//Override copy to return NRR Process
	@Override
	public NRRProcess copy() {
		return new NRRProcess(ID, arrive, execSize, timeRemaining, quantum);
	}

//Override copy to return NRR Process
	@Override
	public NRRProcess fullCopy() {
		NRRProcess newProcess = new NRRProcess(ID, arrive, execSize);
		newProcess.setTimeRemaining(timeRemaining);
		newProcess.setTurnAroundTime(turnAroundTime);
		newProcess.setWaitingTime(waitingTime);
		newProcess.setStartTime(startTime);
		return newProcess;
	}
}
