import java.util.Queue;

public class NRR extends Algorithm{
	
	private 
	
	public NRR(Queue<Process> processList, int dispatchTime) {
		super("NRR", processList, dispatchTime);
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

class NRRProcess{
	
	private static final int START_QUANTUM = 4;
	
	private int quantum;
	private Process process;
	
	NRRProcess(Process process) {
		this.process = process;
		this.quantum = START_QUANTUM;
	}
	
	void setQuantum(int quantum) {
		this.quantum = quantum;
	}
	
	int getQuantum() {
		return quantum;
	}
	
	Process getProcess() {
		return process;
	}
	
	void setProcess(Process process) {
		this.process = process;
	}
	
}
