/*
 *  ----C3282137----
 *  Ryan Jobse
 *  COMP2240 S2 2019
 *  Assignment 1
 *  
 *  RR.java
 *  RR Scheduling algorithm
 */

import java.util.LinkedList;
import java.util.Queue;

public class RR extends Algorithm<Process>{
	
	private int quantum;
	
//Initialize
	public RR(Queue<Process> processList, int dispatchTime, int quantum) {
		super("RR", (LinkedList<Process>) processList, dispatchTime);
		this.quantum = quantum;
	}
	
//Process to be run for each process
	public boolean process() {
		
		//If the current running process arrives later then the current runTime, run it now
		if(runTime < runningProcess.getArrive()) {
			runTime = runningProcess.getArrive();
		}
		
		//Add dispatch time
		runTime += dispatchTime;
		
		//Set Start time to runtime
		runningProcess.setStartTime(runTime);
		
		//Check if process is last to be processed
		if(processQueue.size() == 0 && processList.size() == 0) {
			
			//Add time remaining to runTime
			runTime += runningProcess.getTimeRemaining();
			
			//Empty time remaining
			runningProcess.setTimeRemaining(0);
			
			//Set waiting time and turnaround time
			runningProcess.setWaitingTime(waitingTime(runningProcess));
			runningProcess.setTurnAroundTime(runTime - runningProcess.getArrive());
			return true;
			
		//Check if time remaining is less than or equal to quantum
		}else if(runningProcess.getTimeRemaining() <= quantum) {
			
			//Add time remaining to runTime
			runTime += runningProcess.getTimeRemaining();
			
			//Empty time remaining
			runningProcess.setTimeRemaining(0);
			
			//Set waiting time and turnaround time
			runningProcess.setWaitingTime(waitingTime(runningProcess));
			runningProcess.setTurnAroundTime(runTime - runningProcess.getArrive());
			return true;
		}
		
		//If quantum is less than time remaining
		//Add quantum to runtime
		runTime += quantum;
		
		//Set wait time
		runningProcess.setWaitingTime(waitingTime(runningProcess));
		
		//Set time remaining to old time remaining minus quantum
		runningProcess.setTimeRemaining(runningProcess.getTimeRemaining()-quantum);
		
		//Set turnaround time
		runningProcess.setTurnAroundTime(runTime - runningProcess.getArrive());
		
		//Set arrive time to endTime
		runningProcess.setArrive(runTime);
		return false;
	}
}
