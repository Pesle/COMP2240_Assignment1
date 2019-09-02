/*
 *  ----C3282137----
 *  Ryan Jobse
 *  SENG2200 S1 2019
 *  Assignment 3
 *  
 *  Schedule.java
 *  Manages the current time and what items are currently being "produced"
 */

import java.util.PriorityQueue;
import java.util.Comparator;
import java.util.Queue;

public class Schedule {
	//Current time for the whole application
	private double currentTime;
	//CPU queue
	private Queue<CPU> cpuQueue;
	
	Schedule(int maxQ){
		//Sort the queue by items with the shortest duration remaining
		this.cpuQueue = new PriorityQueue<CPU>(maxQ, new ItemComparator());
		this.currentTime = 0;
	}
	
	//Add a Process to the cpu queue
	public void addProcess(double duration, Process process) {
		cpuQueue.add(new CPU(duration, process));
	}
	
	//Process the cpu
	public Process processCPU() {
		//Pull the top item from the queue
		CPU nextItem = this.cpuQueue.poll();
		
		//Add the item duration to the current time
		currentTime += nextItem.getCurrentDuration();
		
		//Go through the queue and update all of the items durations
		for (CPU p : this.cpuQueue){
	        p.decrementDuration(nextItem.getCurrentDuration());
	    }
		//Finish processing that process
	    nextItem.getProcess().finishProcessing();
	    return nextItem.getProcess();
	}
	
	public double getCurrentTime() {
		return currentTime;
	}
	
	public int getSize() {
		return cpuQueue.size();
	}
}

//Comparator used to sort the Priority Queue
class ItemComparator implements Comparator<CPU>{

	@Override
	public int compare(CPU item1, CPU item2) {
		if(item1.getCurrentDuration() < item2.getCurrentDuration()) {
			return -1;
		}else if(item1.getCurrentDuration() > item2.getCurrentDuration()) {
			return 1;
		}else {
			return 0;
		}
	}
}
