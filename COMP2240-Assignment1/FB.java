import java.util.Queue;

public class FB extends Algorithm{
	
	public FB(Queue<Process> processList, int dispatchTime) {
		super("FB", processList, dispatchTime);
	}
	
	public boolean process() {
		return true;
	}
}