import java.util.Queue;

public class NRR extends Algorithm{
	
	public NRR(Queue<Process> processList, int dispatchTime) {
		super("NRR", processList, dispatchTime);
	}
	
	public boolean process() {
		return true;
	}
}
