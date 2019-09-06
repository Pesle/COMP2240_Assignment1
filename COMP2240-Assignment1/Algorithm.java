import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public abstract class Algorithm<T extends Process> {
	protected String name;
	
	protected int dispatchTime;
	protected int runTime;
	protected State state;
	
	protected T runningProcess;
	protected ArrayList<T> completedProcesses;
	protected Queue<T> processQueue;
	protected LinkedList<T> processList;
	
	public Algorithm(String name, LinkedList<T> processList, int dispatchTime) {
		this.processQueue = new PriorityQueue<T>(new ProcessComparator());
		
		this.processList =  processList;
		
		this.completedProcesses = new ArrayList<T>();
		this.name = name;
		this.state = State.IDLE;
		this.dispatchTime = dispatchTime;
		this.runTime = 0;
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
	
	public String toString() {
		String result = name + ":\n";
		Iterator<T> it = completedProcesses.iterator();
		while (it.hasNext()) {
			T cur = it.next();
			result += "T"+ cur.getStartTime() + ": " + cur.getID()+"\n";
		}
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
	
	public int waitingTime(Process process) {
		return process.getStartTime()-process.getArrive();
	}
	
	public ArrayList<T> joinProcessData(ArrayList<T> list){
		ArrayList<T> newList = new ArrayList<T>();
		Iterator<T> it = list.iterator();
		while (it.hasNext()) {
			T cur = it.next();
			T found = listContains(newList, cur.getID());
			if(found != null){
				found.setTurnAroundTime(found.getTurnAroundTime() + cur.getTurnAroundTime());
				found.setWaitingTime(found.getWaitingTime() + cur.getWaitingTime());
			}else {
				newList.add(cur);
			}
		}
		return newList;
	}
	
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
	
	public boolean dispatcher() {
		if(processList.size() > 0) {
			int size = new Integer(processList.size());
			for(int i = 0; i < size; i++) {
				T cur = processList.poll();
				if(cur.getArrive() <= runTime) {
					processQueue.add(cur);
					//printQueue(processQueue);
					//System.out.println(cur.getID() + " - " +cur.getArrive() + " <= " +runTime);
				}else {
					processList.push(cur);
				}
			}
			return true;
		}
		return false;
	}
	
	public void begin() {
		//dispatcher();
		state = State.BUSY;
		while(state != State.FINISHED) {
			
			//System.out.println("Process Queue: " + processQueue.size() + (" Process List: " + processList.size()));
			
			if(dispatcher() == false && state == State.IDLE) {
				state = State.FINISHED;
				System.out.println("FINISHED");
				break;
				
			}else if(processQueue.size() > 0) {
				state = State.BUSY;
				T cur = processQueue.poll();
				runningProcess = (T) cur.copy();
				System.out.println("BUSY " + cur.getID());
				if(!process()) {
					processQueue.add(runningProcess);
					//printQueue(processQueue);
					//System.out.println(runningProcess.getID() + " - " +runningProcess.getArrive() + " <=x " +runTime);
				}else {
					//System.out.println(runningProcess.getID() + " Completed");
				}
				completedProcesses.add(runningProcess);
				
			}else{
				System.out.println("IDLE");
				state = State.IDLE;
				runTime++;
			}
		}
	}
	
	public abstract boolean process();

}