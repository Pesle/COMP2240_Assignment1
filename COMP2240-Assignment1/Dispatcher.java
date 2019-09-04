import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

public class Dispatcher {
	
	private Queue<Process> processList;
	
	private ArrayList<Algorithm> algorithmList;
	
	private int dispatchTime;
	
	public Dispatcher(){
		this.processList = new PriorityQueue<Process>(new ProcessComparator());
		this.algorithmList = new ArrayList<Algorithm>();
		this.dispatchTime = 0;
	}
	
	public void setup() {
		algorithmList.add(new FCFS(processList, dispatchTime));
		algorithmList.add(new RR(processList, dispatchTime));
		//algorithmList.add(new FB((ArrayList<Process>)processList.clone(), dispatchTime));
		//algorithmList.add(new NRR((ArrayList<Process>)processList.clone(), dispatchTime));
	}
	
	public void begin() {
		Iterator<Algorithm> it = algorithmList.iterator();
		while (it.hasNext()) {
			it.next().begin();
		}
	}
	
	public void results() {
		Iterator<Algorithm> it = algorithmList.iterator();
		while (it.hasNext()) {
			System.out.print(it.next().toString());
		}
	}
	
	public void addProcess(String id, int arrive, int execSize) {
		processList.add(new Process(id, arrive, execSize));
	}
	
	public void setDispatchTime(int dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
}

//Comparator used to sort the Priority Queue
class ProcessComparator implements Comparator<Process>{

	@Override
	public int compare(Process p1, Process p2) {
		if(p1.getArrive() < p2.getArrive()) {
			return -1;
		}else if(p1.getArrive() > p2.getArrive()) {
			return 1;
		}else {
			return 0;
		}
	}
}
