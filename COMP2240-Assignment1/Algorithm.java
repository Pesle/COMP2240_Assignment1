/*
 *  ----C3282137----
 *  Ryan Jobse
 *  COMP2240 S2 2019
 *  Assignment 1
 *  
 *  Algorithm.java
 *  Base for the Algorithms to run
 *  and dispatch processes
 */

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

//This is used as a Generic Process class for NRR as it requires a subclass of Process
public abstract class Algorithm<T extends Process> {
	
//Variables
	protected String name;
	protected int dispatchTime;
	protected int runTime;
	protected State state;
	protected T runningProcess;		//Current running process
	
//Lists
	protected ArrayList<T> completedProcesses;	//List of process executions once they are completed
	protected Queue<T> processQueue;			//List of processes in queue to be processed
	protected LinkedList<T> processList;		//List of processes
	
//Initialize
	public Algorithm(String name, LinkedList<T> processList, int dispatchTime) {
		this.processQueue = new PriorityQueue<T>(new ProcessComparator());
		this.processList =  processList;
		this.completedProcesses = new ArrayList<T>();
		this.name = name;
		this.state = State.IDLE;
		this.dispatchTime = dispatchTime;
		this.runTime = 0;
	}
	
//Setters and Getters
	public String getName() {
		return name;
	}
	
	public void setState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
//Get average turnaround time
	public double getAverageTurnaround() {
		double averageTurnaround = 0.0;
		
		//Go through the joined list and add turnaround times
		ArrayList<T> joined = joinProcessData(completedProcesses);
		Iterator<T> it1 = joined.iterator();
		while (it1.hasNext()) {
			T cur = it1.next();
			averageTurnaround += cur.getTurnAroundTime();
		}
		
		//Divide the turnaround times by the size
		return averageTurnaround/ joined.size();
	}
	
//Get average wait time
	public double getAverageWait() {
		double averageWait = 0.0;
		
		//Go through the joined list and add wait times
		ArrayList<T> joined = joinProcessData(completedProcesses);
		Iterator<T> it1 = joined.iterator();
		while (it1.hasNext()) {
			T cur = it1.next();
			averageWait += cur.getWaitingTime();
		}
		
		//Divide the wait times by the size
		return averageWait/ joined.size();
	}
	
//Return information about the algorithm
	public String toString() {
		String result = name + ":\n";
		
		//Get process executions and show start times
		Iterator<T> it = completedProcesses.iterator();
		while (it.hasNext()) {
			T cur = it.next();
			result += "T"+ cur.getStartTime() + ": " + cur.getID()+"\n";
		}
		
		//Get joined processes and display information
		result += "\nProcess	Turnaround Time	Waiting Time\n";
		ArrayList<T> joined = joinProcessData(completedProcesses);
		Iterator<T> it1 = joined.iterator();
		while (it1.hasNext()) {
			T cur = it1.next();
			result += cur.getID() + "	" + cur.getTurnAroundTime() + "		" + cur.getWaitingTime() + "\n";
		}
		result += "\n";
		return result;
	}
	
	//Calculate the waiting time
	public int waitingTime(Process process) {
		return process.getStartTime()-process.getArrive();
	}
	
	//Join data for processes that have been processed
	public ArrayList<T> joinProcessData(ArrayList<T> list){
		
		//New list
		ArrayList<T> newList = new ArrayList<T>();
		Iterator<T> it = list.iterator();
		while (it.hasNext()) {
			T cur = it.next();
			
			//Check if the current process is already in the new list
			T found = listContains(newList, cur.getID());
			if(found != null){
				
				//If found already, add current information to it
				found.setTurnAroundTime(found.getTurnAroundTime() + cur.getTurnAroundTime());
				found.setWaitingTime(found.getWaitingTime() + cur.getWaitingTime());
			}else {
				
				//If not, add a copy of the process to the new list
				newList.add((T) cur.fullCopy());
			}
		}
		return newList;
	}
	
	//Check if a list contains a process
	private T listContains(ArrayList<T> list, String ID) {
		Iterator<T> it = list.iterator();
		while (it.hasNext()) {
			T cur = it.next();
			if(cur.getID().contentEquals(ID)) {
				return cur;
			}
		}
		return null;
	}
	
	//Dispatches processes from the process list to the process queue when they arrive
	public boolean dispatcher() {
		//Make sure that the processList isnt empty
		if(processList.size() > 0) {
			int size = new Integer(processList.size());
			for(int i = 0; i < size; i++) {
				T cur = processList.poll();
				
				//If current arrival time is equal or less than the runTime, then add to the processQueue
				if(cur.getArrive() <= runTime) {
					processQueue.add(cur);
				}else {
					processList.push(cur);
				}
			}
			return true;
		}
		return false;
	}
	
	public void begin() {
		state = State.BUSY;
		
		//Loop while the algorithm isnt finished
		while(state != State.FINISHED) {
			
			//Run dispatcher, and if processList is empty and algorithm is idling, then finished
			if(dispatcher() == false && state == State.IDLE) {
				state = State.FINISHED;
				break;
				
			//If the processQueue has data in it, process it
			}else if(processQueue.size() > 0) {
				state = State.BUSY;
				T cur = processQueue.poll();
				
				//Copy the first process in the queue into the runningProcess
				runningProcess = (T) cur.copy();
				
				//Run the algorithms process and check if it finished the process
				if(!process()) {
					
					//If the process wasnt finished, add back into the processQueue
					processQueue.add(runningProcess);
				}
				
				//Add process execution to the completedProcesses list
				completedProcesses.add(runningProcess);
				
			}else{
				//If idle, increase runTime
				state = State.IDLE;
				runTime++;
			}
		}
	}
	
	public abstract boolean process();

}