/*
 *  ----C3282137----
 *  Ryan Jobse
 *  COMP2240 S2 2019
 *  Assignment 1
 *  
 *  NRR.java
 *  NRR Scheduling algorithm
 */

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class NRR extends Algorithm<NRRProcess>{	
	
//Initialize
	public NRR(Queue<Process> cloneQueue, int dispatchTime) {
		super("NRR", changeQueue(cloneQueue), dispatchTime);
	}
	
//Convert the queue of processes to a queue of NRR processes
	private static LinkedList<NRRProcess> changeQueue(Queue<Process> queue){
		
		//Copy process queue into a NRR process queue
		LinkedList<NRRProcess> newProcessList = new LinkedList<NRRProcess>();
		Iterator<Process> it = queue.iterator();
		while (it.hasNext()) {
			Process cur = it.next();
			newProcessList.add(new NRRProcess(cur.getID(), cur.getArrive(), cur.getExecSize()));
		}
		return newProcessList;
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
		
		//Set next value to the current processes quantum
		int next = runningProcess.getQuantum();
		
		//Check if process is last to be processed
		if(processQueue.size() == 0 && processList.size() == 0) {

			//Add time remaining to runTime
			runTime += runningProcess.getTimeRemaining();
			
			//Empty time remaining
			runningProcess.setTimeRemaining(0);
			
			//Set wait time and turnaround time
			runningProcess.setWaitingTime(waitingTime(runningProcess));
			runningProcess.setTurnAroundTime(runTime - runningProcess.getArrive());
			return true;
			
		//Check if time remaining is less than or equal to quantum
		}else if(runningProcess.getTimeRemaining() <= next) {

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
		runTime += next;
		
		//Set wait time
		runningProcess.setWaitingTime(waitingTime(runningProcess));
		
		//Set time remaining to old time remaining minus quantum
		runningProcess.setTimeRemaining(runningProcess.getTimeRemaining()-next);
		
		//Set turnaround time
		runningProcess.setTurnAroundTime(runTime - runningProcess.getArrive());
		
		//Decrease quantum
		runningProcess.decreaseQuantum();
		
		//Set arrive time to endTime
		runningProcess.setArrive(runTime);
		return false;
	}
}
