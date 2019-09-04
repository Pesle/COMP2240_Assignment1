import java.util.ArrayList;
import java.util.Comparator;
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
		this.processList = new LinkedList<Process>();
		Iterator<Process> it = processList.iterator();
		while (it.hasNext()) {
			this.processList.add(it.next());
		}
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
		it = completedProcesses.iterator();
		while (it.hasNext()) {
			Process cur = it.next();
			result += cur.getID() + "	" + cur.getTurnAroundTime() + "		" + waitingTime(cur) + "\n";
		}
		result += "\n";
		return result;
	}
	
	public int waitingTime(Process process) {
		System.out.println(process.getID() + ": " + process.getStartTime() + " - " + process.getArrive() + " = " + (process.getStartTime()-process.getArrive()) + " : " + (process.getExecSize()-process.getTimeRemaining()));
		int result = 0;
		if(process.getStartTime() > process.getArrive())
			result = process.getStartTime()-process.getArrive();
		else
			result = (process.getExecSize()-process.getTimeRemaining())-process.getStartTime();
		return result;
	}
	
	
	public boolean dispatcher() {
		if(processList.size() > 0) {
			int size = new Integer(processList.size());
			for(int i = 0; i < size; i++) {
				Process cur = processList.poll();
				if(cur.getArrive() <= runTime) {
					processQueue.add(cur);
					printQueue(processQueue);
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
				//System.out.println(cur.getID());
				if(!process()) {
					processQueue.add(runningProcess);
					printQueue(processQueue);
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