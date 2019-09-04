import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;

public abstract class Algorithm {
	protected String name;
	
	protected int dispatchTime;
	protected int runTime;
	protected State state;
	
	protected Process runningProcess;
	protected Queue<Process> processList;
	protected ArrayList<Process> completedProcesses;
	
	public Algorithm(String name, Queue<Process> processList, int dispatchTime) {
		this.processList = processList;
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
	
	public abstract void begin();
	
	public abstract void process();

}