import java.util.LinkedList;
import java.util.Queue;

public class FB extends Algorithm{
	
	private int quantum;
	private final int MAX_PRIORITY = 6;
	
	private int currentPriority = 0;
	private Algorithm[] queueList;
	private Queue<Process>[] processQueueList;
	
	public FB(Queue<Process> processList, int dispatchTime, int quantum) {
		super("FB", processList, dispatchTime);
		queueList = new Algorithm[MAX_PRIORITY+1]; 
		processQueueList = new Queue[MAX_PRIORITY+1]; 
		for(int i = 0; i < MAX_PRIORITY-1; i++) {
			Queue<Process> newQueue = new LinkedList<Process>();
			processQueueList[i] = newQueue;
			queueList[i] = new FCFS(newQueue, dispatchTime);
		}
		Queue<Process> newQueue = new LinkedList<Process>();
		processQueueList[MAX_PRIORITY] = newQueue;
		queueList[MAX_PRIORITY] = new RR(newQueue, dispatchTime, quantum);
		
	}

	public boolean process() {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
