/*
 *  ----C3282137----
 *  Ryan Jobse
 *  COMP2240 S2 2019
 *  Assignment 1
 *  
 *  FCFS.java
 *  FCFS Scheduling algorithm
 */

import java.util.LinkedList;
import java.util.Queue;

public class FCFS extends Algorithm<Process>{
	
//Initialize
	public FCFS(Queue<Process> queue, int dispatchTime) {
		super("FCFS", (LinkedList<Process>) queue, dispatchTime);
	}
	
//Process to be run for each process
	public boolean process() {
		
		//If the current running process arrives later then the current runTime, run it now
		if(runTime < runningProcess.getArrive()) {
			runTime = runningProcess.getArrive();
		}
		
		//Add dispatch time
		runTime += dispatchTime;
		
		//Set the start time of the process
		runningProcess.setStartTime(runTime);
		
		//Add the exec size of the process to runtime
		runTime += runningProcess.getExecSize();
		
		//Set the total turnaround time for the process
		runningProcess.setTurnAroundTime(runTime - runningProcess.getArrive());
		
		//Set the total waiting time for the process
		runningProcess.setWaitingTime(waitingTime(runningProcess));
		return true;
	}
}

