import java.util.LinkedList;
import java.util.Queue;

public class RR extends Algorithm<Process>{
	
	private int quantum;
	
	public RR(Queue<Process> processList, int dispatchTime, int quantum) {
		super("RR", (LinkedList<Process>) processList, dispatchTime);
		this.quantum = quantum;
	}
	
	public boolean process() {
		//System.out.println(runningProcess.getID() + " " + runTime + " - " + runningProcess.getTimeRemaining());
		if(runTime < runningProcess.getArrive()) {
			runTime = runningProcess.getArrive();
		}
		runTime += dispatchTime;
		runningProcess.setStartTime(runTime);
		int next = quantum;
		if(processQueue.size() == 0 && processList.size() == 0) {
			runTime += runningProcess.getTimeRemaining();
			runningProcess.setTimeRemaining(0);
			runningProcess.setWaitingTime(waitingTime(runningProcess));
			runningProcess.setTurnAroundTime(runTime - runningProcess.getArrive());
			System.out.println(runningProcess.getID() + " " + runTime);
			return true;
		}else if(runningProcess.getTimeRemaining() <= next) {
			runTime += runningProcess.getTimeRemaining();
			runningProcess.setTimeRemaining(0);
			runningProcess.setWaitingTime(waitingTime(runningProcess));
			runningProcess.setTurnAroundTime(runTime - runningProcess.getArrive());
			return true;
		}
		runTime += next;
		runningProcess.setWaitingTime(waitingTime(runningProcess));
		runningProcess.setTimeRemaining(runningProcess.getTimeRemaining()-next);
		runningProcess.setTurnAroundTime(runTime - runningProcess.getArrive());
		runningProcess.setArrive(runTime);
		//System.out.println(runningProcess.getID() + " " + runTime + " - " + runningProcess.getTimeRemaining());
		return false;
	}
}
