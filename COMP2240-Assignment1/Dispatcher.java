import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
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
		algorithmList.add((Algorithm<Process>) new FCFS(cloneQueue(processList), dispatchTime));
		algorithmList.add((Algorithm<Process>) new RR(cloneQueue(processList), dispatchTime, 4));
		algorithmList.add((Algorithm<Process>) new FB(cloneQueue(processList), dispatchTime, 4));
		algorithmList.add((Algorithm<NRRProcess>) new NRR(cloneQueue(processList), dispatchTime));
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
	
	public Queue<Process> cloneQueue(Queue<Process> queue){
		Queue<Process> newQueue = new LinkedList<Process>();
		Iterator<Process> it = queue.iterator();
		while (it.hasNext()) {
			Process cur = it.next();
			newQueue.add(new Process(cur.getID(), cur.getArrive(), cur.getExecSize()));
		}
		return newQueue;
	}
}

//Comparator used to sort the Priority Queue
class ProcessComparator<T extends Process> implements Comparator<T>{

	@Override
	public int compare(T px, T py) {
		//Check if px arrives sooner
		if(px.getArrive() < py.getArrive()) {
			return -1;
			
		//Check if py arrives sooner
		}else if(px.getArrive() > py.getArrive()) {
			return 1;
			
		}else {
			//Check if px is a higher ID
			if(Integer.parseInt(px.getID().substring(1)) > Integer.parseInt(py.getID().substring(1))) {
				return 1;
				
			//Check if py is a higher ID
			}else if(Integer.parseInt(px.getID().substring(1)) < Integer.parseInt(py.getID().substring(1))) {
				return -1;
				
			}else {	
				return 0;
			}
		}
	}
}
