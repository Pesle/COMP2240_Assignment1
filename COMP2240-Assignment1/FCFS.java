import java.util.LinkedList;
import java.util.Queue;

public class FCFS extends Algorithm<Process>{
	
	public FCFS(Queue<Process> queue, int dispatchTime) {
		super("FCFS", (LinkedList<Process>) queue, dispatchTime);
	}
	
	public boolean process() {
		if(runTime < runningProcess.getArrive()) {
			runTime = runningProcess.getArrive();
		}
		runTime += dispatchTime;
		runningProcess.setStartTime(runTime);
		runTime += runningProcess.getExecSize();
		runningProcess.setTurnAroundTime(runTime - runningProcess.getArrive());
		runningProcess.setWaitingTime(waitingTime(runningProcess));
		return true;
	}
}

