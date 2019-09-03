import java.util.ArrayList;
import java.util.Iterator;

public class Dispatcher {
	
	private ArrayList<Process> processList;
	private ArrayList<Process> arrivedProcessList;
	
	private ArrayList<Algorithm> algorithmList;
	
	private double dispatchTime;
	
	public Dispatcher(){
		this.processList = new ArrayList<Process>();
		this.algorithmList = new ArrayList<Algorithm>();
		this.dispatchTime = 0;
	}
	
	public void setup() {
		algorithmList.add(new FCFS((ArrayList<Process>)processList.clone(), dispatchTime));
		//algorithmList.add(new RR((ArrayList<Process>)processList.clone(), dispatchTime));
		//algorithmList.add(new FB((ArrayList<Process>)processList.clone(), dispatchTime));
		//algorithmList.add(new NRR((ArrayList<Process>)processList.clone(), dispatchTime));
	}
	
	public void begin() {
		Iterator<Algorithm> it = algorithmList.iterator();
		while (it.hasNext()) {
			it.next().begin();
		}
	}
	
	public void addProcess(String id, double arrive, double execSize) {
		processList.add(new Process(id, arrive, execSize));
	}
	
	public void setDispatchTime(double dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
}
