import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public abstract class Algorithm {
	protected String name;
	
	protected int dispatchTime;
	protected int runTime;
	protected State state;
	
	protected Process runningProcess;
	protected ArrayList<Process> completedProcesses;
	protected Queue<Process> processQueue;
	protected LinkedList<Process> processList;
	
	public Algorithm(String name, Queue<Process> processList, int dispatchTime) {
		this.processQueue = new PriorityQueue<Process>(new ProcessComparator());
		this.processList = (LinkedList<Process>) processList;
		
		this.completedProcesses = new ArrayList<Process>();
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
		Iterator<Process> it = completedProcesses.iterator();
		while (it.hasNext()) {
			Process cur = it.next();
			result += "T"+ cur.getStartTime() + ": " + cur.getID()+"\n";
		}
		result += "\nProcess	Turnaround Time	Waiting Time\n";
		ArrayList<Process> joined = joinProcessData(completedProcesses);
		Iterator<Process> it1 = joined.iterator();
		while (it1.hasNext()) {
			Process cur = it1.next();
			result += cur.getID() + "	" + cur.getTurnAroundTime() + "		" + cur.getWaitingTime() + "\n";
		}
		result += "\n";
		return result;
	}
	
	public int waitingTime(Process process) {
		return process.getStartTime()-process.getArrive();
	}
	
	public ArrayList<Process> joinProcessData(ArrayList<Process> list){
		ArrayList<Process> newList = new ArrayList<Process>();
		Iterator<Process> it = list.iterator();
		while (it.hasNext()) {
			Process cur = it.next();
			Process found = listContains(newList, cur.getID());
			if(found != null){
				found.setTurnAroundTime(found.getTurnAroundTime() + cur.getTurnAroundTime());
				found.setWaitingTime(found.getWaitingTime() + cur.getWaitingTime());
			}else {
				newList.add(cur);
			}
		}
		return newList;
	}
	
	private Process listContains(ArrayList<Process> list, String ID) {
		Iterator<Process> it = list.iterator();
		while (it.hasNext()) {
			Process cur = it.next();
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
				Process cur = processList.poll();
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
	
	public void printQueue(Queue<Process> queue) {
		Iterator<Process> it = queue.iterator();
		while (it.hasNext()) {
			Process cur = it.next();
			System.out.print(cur.getID() + "("+ cur.getArrive()  +") , ");
		}
		System.out.print("\n");
	}
	
	public void begin() {
		//dispatcher();
		printQueue(processList);
		state = State.BUSY;
		while(state != State.FINISHED) {
			
			//System.out.println("Process Queue: " + processQueue.size() + (" Process List: " + processList.size()));
			
			if(dispatcher() == false && state == State.IDLE) {
				state = State.FINISHED;
				System.out.println("FINISHED");
				break;
				
			}else if(processQueue.size() > 0) {
				state = State.BUSY;
				Process cur = processQueue.poll();
				runningProcess = new Process(cur.getID(), cur.getArrive(), cur.getExecSize(), cur.getTimeRemaining());
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