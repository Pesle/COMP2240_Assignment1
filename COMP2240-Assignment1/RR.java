import java.util.Queue;

public class RR extends Algorithm{
	
	private final int QUANTUM = 4;
	
	public RR(Queue<Process> processList, int dispatchTime) {
		super("RR", processList, dispatchTime);
	}
	
	public boolean process() {
		if(runTime < runningProcess.getArrive()) {
			runTime = runningProcess.getArrive();
		}
		runTime += dispatchTime;
		runningProcess.setStartTime(runTime);
		if(runningProcess.getTimeRemaining() <= QUANTUM) {
			runTime += runningProcess.getExecSize();
			runningProcess.setTimeRemaining(0);
			runningProcess.setTurnAroundTime(runTime - runningProcess.getArrive());
			return true;
		}
		runTime += QUANTUM;
		runningProcess.setTimeRemaining(runningProcess.getTimeRemaining()-QUANTUM);
		return false;
	}
}
