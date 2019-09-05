import java.util.Queue;

public class FB extends Algorithm{
	
	private int quantum;
	private final int MAX_PRIORITY = 6;
	private int currentPriority = 0;
	
	private Queue<Process>[] priorityQueues;
	
	public FB(Queue<Process> processList, int dispatchTime, int quantum) {
		super("FB", processList, dispatchTime);
		priorityQueues = new Queue[MAX_PRIORITY-1];
		for(int i = 0; i < MAX_PRIORITY; i++)
		this.quantum = quantum;
	}
	
	@Override
	public void begin() {
		//dispatcher();
		printQueue(processList);
		state = State.BUSY;
		while(state != State.FINISHED) {
			
			//System.out.println("Process Queue: " + processQueue.size() + (" Process List: " + processList.size()));
			
			if(dispatcher(0) == false && state == State.IDLE) {
				state = State.FINISHED;
				System.out.println("FINISHED");
				break;
				
			}else if(priorityQueues[currentPriority].size() > 0) {
				state = State.BUSY;
				Process cur = processQueue.poll();
				runningProcess = new Process(cur.getID(), cur.getArrive(), cur.getExecSize(), cur.getTimeRemaining());
				System.out.println("BUSY " + cur.getID());
				if(!process()) {
					priorityQueues[currentPriority+1].add(runningProcess);
					//printQueue(processQueue);
					//System.out.println(runningProcess.getID() + " - " +runningProcess.getArrive() + " <=x " +runTime);
				}else {
					//System.out.println(runningProcess.getID() + " Completed");
				}
				completedProcesses.add(runningProcess);
				
			}else{
				System.out.println("IDLE");
				state = State.IDLE;
				runTime++;
			}
		}
	}
	
	public boolean dispatcher(int priority) {
		if(processList.size() > 0) {
			int size = new Integer(processList.size());
			for(int i = 0; i < size; i++) {
				Process cur = processList.poll();
				if(cur.getArrive() <= runTime) {
					priorityQueues[priority].add(cur);
					//printQueue(processQueue);
					//System.out.println(cur.getID() + " - " +cur.getArrive() + " <= " +runTime);
				}else {
					processList.push(cur);
				}
			}
			return true;
		}
		return false;
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
