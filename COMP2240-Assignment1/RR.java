import java.util.Queue;

public class RR extends Algorithm{
	
	private final int QUANTUM = 4;
	
	public RR(Queue<Process> processList, int dispatchTime) {
		super("RR", processList, dispatchTime);
	}
	
	public boolean process() {
		//System.out.println(runningProcess.getID() + " " + runTime + " - " + runningProcess.getTimeRemaining());
		if(runTime < runningProcess.getArrive()) {
			runTime = runningProcess.getArrive();
		}
		runTime += dispatchTime;
		runningProcess.setStartTime(runTime);
		int next = QUANTUM;
		if(processQueue.size() == 0) {
			if(processList.size() > 0) {
				int nextArrive =  processList.getFirst().getArrive();
				//System.out.println(nextArrive + " > " + (runTime + QUANTUM));
				if((runTime + QUANTUM) < nextArrive){
					next = nextArrive;
				}
			}
		}
//		if(processQueue.size() == 0) {
//			runTime += runningProcess.getTimeRemaining();
//			runningProcess.setTimeRemaining(0);
//			runningProcess.setEndTime(runTime);
//			runningProcess.setTurnAroundTime(runTime - runningProcess.getArrive());
//			System.out.println(runningProcess.getID() + " " + runTime);
//			return true;
//		}else 
		if(runningProcess.getTimeRemaining() <= next) {
			runTime += runningProcess.getTimeRemaining();
			runningProcess.setTimeRemaining(0);
			runningProcess.setTurnAroundTime(runTime - runningProcess.getArrive());
			runningProcess.setEndTime(runTime);
			//System.out.println(runningProcess.getID() + " " + runTime);
			return true;
		}
		runTime += next;
		runningProcess.setTimeRemaining(runningProcess.getTimeRemaining()-next);
		runningProcess.setEndTime(runTime);
		//System.out.println(runningProcess.getID() + " " + runTime + " - " + runningProcess.getTimeRemaining());
		return false;
	}
}
