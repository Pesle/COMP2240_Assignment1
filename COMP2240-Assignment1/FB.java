/*
 *  ----C3282137----
 *  Ryan Jobse
 *  COMP2240 S2 2019
 *  Assignment 1
 *  
 *  FB.java
 *  FB Scheduling algorithm
 */

import java.util.LinkedList;
import java.util.Queue;

public class FB extends Algorithm<Process>{
	
	//Variables
	private int quantum;
	private final int MAX_PRIORITY = 6;
	private int currentPriority = 0;
	
	//Priority queues
	private Queue<Process>[] priorityQueues;
	
	//Initialize
	public FB(Queue<Process> processList, int dispatchTime, int quantum) {
		super("FB (constant)", (LinkedList<Process>) processList, dispatchTime);
		
		//Create priority queues
		priorityQueues = new Queue[MAX_PRIORITY];
		for(int i = 0; i < MAX_PRIORITY; i++) {
			priorityQueues[i] = new LinkedList<Process>();
		}
		this.quantum = quantum;
	}
	
	//Override the algorithms begin
	@Override
	public void begin() {
		state = State.BUSY;
		//Loop while the algorithm isnt finished
		while(state != State.FINISHED) {
			
			//Run dispatcher, and if processList is empty and algorithm is idling, then finished
			if(dispatcher(0) == false && state == State.IDLE) {
				state = State.FINISHED;
				break;
				
			//If the current priority queue has data in it, process it
			}else if(priorityQueues[currentPriority].size() > 0) {
				state = State.BUSY;
				Process cur = priorityQueues[currentPriority].poll();
				
				//Copy the first process in the queue into the runningProcess
				runningProcess = cur.copy();
				
				//Run the algorithms process and check if it finished the process
				if(!process()) {
					
					//If the process wasnt finished, add back into then next priority queue
					priorityQueues[currentPriority+1].add(runningProcess);
				}
				
				//Add process execution to the completedProcesses list
				completedProcesses.add(runningProcess);
				
			//If the current priority queue is empty, go to the next one
			}else if(currentPriority < MAX_PRIORITY-1) {
				
				//If the first queue has data in it, go back to it
				if(findQueue() == 0) {
					currentPriority = 0;
					
				//Otherwise go through the list
				}else {
					currentPriority++;
				}
				
			}else {
				
				//If the currentPriority gets to the last queue and there is no data, 
				// check to make sure all queues are empty before idling
				int newPriority = findQueue();
				if(newPriority == -1) {
					state = State.IDLE;
					runTime++;
				}else {
					currentPriority = newPriority;
				}
			}
		}
	}
	
	//No need to override as this requires the priority queue to enter the data into
	public boolean dispatcher(int priority) {
		//Make sure that the processList isnt empty
		if(processList.size() > 0) {
			int size = new Integer(processList.size());
			for(int i = 0; i < size; i++) {
				Process cur = processList.poll();
				
				//If current arrival time is equal or less than the runTime, then add to the processQueue
				if(cur.getArrive() <= runTime) {
					priorityQueues[priority].add(cur);
				}else {
					processList.push(cur);
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean process() {

		//If the current running process arrives later then the current runTime, run it now
		if(runTime < runningProcess.getArrive()) {
			runTime = runningProcess.getArrive();
		}
		
		//Add dispatch time
		runTime += dispatchTime;
		
		//Set the start time of the process
		runningProcess.setStartTime(runTime);
		
		//Check if time remaining is less than or equal to quantum
		if(runningProcess.getTimeRemaining() <= quantum) {
			
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
		
		//Reset arrive time to endTime
		runningProcess.setArrive(runTime);
		return false;
	}
	
	//Go through priority queues to find data
	private int findQueue() {
		for(int i = 0; i < MAX_PRIORITY; i++) {
			if(priorityQueues[i].size() > 0) {
				return i;
			}
		}
		return -1;
	}
	
	
}
