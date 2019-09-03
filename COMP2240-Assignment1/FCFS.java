import java.util.ArrayList;
import java.util.Iterator;

public class FCFS extends Algorithm{
	
	public FCFS(ArrayList<Process> processList, double dispatchTime) {
		super("FCFS", processList, dispatchTime);
	}
	
	public void begin() {
		state = State.BUSY;
		Iterator<Process> it = processList.iterator();
		while (it.hasNext()) {
			runningProcess = it.next();
			process();
		}
		state = State.FINISHED;
	}
	
	public void process() {
		runTime += dispatchTime;
		runningProcess.setStartTime(runTime);
		runTime += runningProcess.getExecSize();
		runningProcess.setTurnAroundTime(runTime);
	}
}

