/*
 *  ----C3282137----
 *  Ryan Jobse
 *  COMP2240 S2 2019
 *  Assignment 1
 *  
 *  Dispatcher.java
 *  Manages the algorithms and processes
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dispatcher {
	
	//List that stores processes from the file
	private Queue<Process> processList;
	
	//List of algorithms to run
	private ArrayList<Algorithm> algorithmList;
	
	//Dispatch time from file
	private int dispatchTime;
	
	//Initialize
	public Dispatcher(){
		this.processList = new PriorityQueue<Process>(new ProcessComparator());
		this.algorithmList = new ArrayList<Algorithm>();
		this.dispatchTime = 0;
	}
	
	//Add all algorithms to list with a copy of the process list, dispatch time and quantums
	public void setup() {
		algorithmList.add((Algorithm<Process>) 		new FCFS(cloneQueue(processList), dispatchTime));
		algorithmList.add((Algorithm<Process>)		new RR(cloneQueue(processList), dispatchTime, 4));
		algorithmList.add((Algorithm<Process>) 		new FB(cloneQueue(processList), dispatchTime, 4));
		algorithmList.add((Algorithm<NRRProcess>) 	new NRR(cloneQueue(processList), dispatchTime));
	}
	
	//Loop through algorithms and run them.
	public void begin() {
		Iterator<Algorithm> it = algorithmList.iterator();
		while (it.hasNext()) {
			it.next().begin();
		}
	}
	
	//Loop through the algorithms and display their results
	public void results() {
		Iterator<Algorithm> it = algorithmList.iterator();
		while (it.hasNext()) {
			System.out.print(it.next().toString());
		}
		
		//Display the summary information
		System.out.println("Summary\nAlgorithm	Average Turnaround Time	 Average Waiting Time");
		it = algorithmList.iterator();
		while (it.hasNext()) {
			Algorithm cur = it.next();
			System.out.format("%-16s%-25.2f%-16.2f\n", cur.getName(), cur.getAverageTurnaround(), cur.getAverageWait());
		}
	}
	
	//Add process to list
	public void addProcess(String id, int arrive, int execSize) {
		processList.add(new Process(id, arrive, execSize));
	}
	
	//Set dispatch time
	public void setDispatchTime(int dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
	
	//Create a copy of a queue
	public Queue<Process> cloneQueue(Queue<Process> queue){
		Queue<Process> newQueue = new LinkedList<Process>();
		Iterator<Process> it = queue.iterator();
		while (it.hasNext()) {
			Process cur = it.next();
			newQueue.add(new Process(cur.getID(), cur.getArrive(), cur.getExecSize()));
		}
		return newQueue;
	}
}

//Comparator used to sort the Priority Queues
class ProcessComparator<T extends Process> implements Comparator<T>{

	@Override
	public int compare(T px, T py) {
		//Check if px arrives sooner
		if(px.getArrive() < py.getArrive()) {
			return -1;
			
		//Check if py arrives sooner
		}else if(px.getArrive() > py.getArrive()) {
			return 1;
			
		}else {
			//Check if px is a higher ID
			if(Integer.parseInt(px.getID().substring(1)) > Integer.parseInt(py.getID().substring(1))) {
				return 1;
				
			//Check if py is a higher ID
			}else if(Integer.parseInt(px.getID().substring(1)) < Integer.parseInt(py.getID().substring(1))) {
				return -1;
				
			}else {	
				return 0;
			}
		}
	}
}
