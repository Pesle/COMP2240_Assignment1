import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class NRR extends Algorithm<NRRProcess>{	
	
	public NRR(Queue<Process> cloneQueue, int dispatchTime) {
		super("NRR", changeQueue(cloneQueue), dispatchTime);
	}
	
	private static LinkedList<NRRProcess> changeQueue(Queue<Process> queue){
		LinkedList<NRRProcess> newProcessList = new LinkedList<NRRProcess>();
		Iterator<Process> it = queue.iterator();
		while (it.hasNext()) {
			Process cur = it.next();
			newProcessList.add(new NRRProcess(cur.getID(), cur.getArrive(), cur.getExecSize()));
		}
		return newProcessList;
	}
	
	public boolean process() {
		//System.out.println(runningProcess.getID() + " " + runTime + " - " + runningProcess.getTimeRemaining());
		if(runTime < runningProcess.getArrive()) {
			runTime = runningProcess.getArrive();
		}
		runTime += dispatchTime;
		runningProcess.setStartTime(runTime);
		int next = runningProcess.getQuantum();
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
		runningProcess.decreaseQuantum();
		runningProcess.setArrive(runTime);
		//System.out.println(runningProcess.getID() + " " + runTime + " - " + runningProcess.getTimeRemaining());
		return false;
	}
}
