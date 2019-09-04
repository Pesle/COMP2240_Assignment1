import java.util.Iterator;
import java.util.Queue;

public class RR extends Algorithm{
	
	private int quantum = 4;
	
	public RR(Queue<Process> processList, int dispatchTime) {
		super("RR", processList, dispatchTime);
	}
	
	public void begin() {
		state = State.BUSY;
		boolean finished = true;
			
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
