import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
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
	
	public Algorithm(String name, Queue<Process> processList, int dispatchTime) {
		this.processQueue = new PriorityQueue<Process>(new ProcessComparator());
		Iterator<Process> it = processList.iterator();
		while (it.hasNext()) {
			processQueue.add(it.next());
		}
		this.completedProcesses = new ArrayList<Process>();
		this.name = name;
		this.state = State.IDLE;
		this.dispatchTime = dispatchTime;
		runTime = 0;
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
	
	public void check() {
		System.out.println(processQueue.size());
		if(processQueue.size() > 0) {
			state = State.BUSY;
		}else {
			state = State.FINISHED;
		}
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
		return process.getStartTime()-process.getArrive();
	}
	
	public void begin() {
		state = State.BUSY;
		while(state == State.BUSY) {
			Process cur = processQueue.poll();
			runningProcess = new Process(cur.getID(), cur.getArrive(), cur.getExecSize(), cur.getTimeRemaining());
			if(!process()) {
				processQueue.add(runningProcess);
			}
			completedProcesses.add(runningProcess);
			check();
		}
	}
	
	public abstract boolean process();

}