import java.util.Iterator;
import java.util.Queue;

public class FCFS extends Algorithm{
	
	public FCFS(Queue<Process> queue, int dispatchTime) {
		super("FCFS", queue, dispatchTime);
	}
	
	public void begin() {
		state = State.BUSY;
		Iterator<Process> it = processList.iterator();
		while (it.hasNext()) {
			Process cur = it.next();
			runningProcess = new Process(cur.getID(), cur.getArrive(), cur.getExecSize());
			process();
			completedProcesses.add(runningProcess);
		}
		state = State.FINISHED;
	}
	
	public void process() {
		if(runTime < runningProcess.getArrive()) {
			runTime = runningProcess.getArrive();
		}
		runTime += dispatchTime;
		runningProcess.setStartTime(runTime);
		runTime += runningProcess.getExecSize();
		runningProcess.setTurnAroundTime(runTime - runningProcess.getArrive());
	}
}

