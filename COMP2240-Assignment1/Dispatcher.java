import java.util.ArrayList;

public class Dispatcher {
	
	private ArrayList<Process> processList;
	private double dispatchTime;
	
	Dispatcher(){
		this.processList = new ArrayList<Process>();
		this.dispatchTime = 0;
	}
	
	public void addProcess(String id, double arrive, double execSize) {
		processList.add(new Process(id, arrive, execSize));
	}
	
	public void setDispatchTime(double dispatchTime) {
		this.dispatchTime = dispatchTime;
	}
}
