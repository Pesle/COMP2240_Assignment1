import java.util.Queue;

public class FCFS extends Algorithm{
	
	public FCFS(Queue<Process> queue, int dispatchTime) {
		super("FCFS", queue, dispatchTime);
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

