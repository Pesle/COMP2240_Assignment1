
public class NRRProcess extends Process{
	
	private static final int START_QUANTUM = 4;
	private static final int MINIMUM_QUANTUM = 2;
	
	private int quantum;
	
	NRRProcess(String ID, int arrive, int execSize) {
		super(ID, arrive, execSize);
		this.quantum = START_QUANTUM;
	}
	
	NRRProcess(String ID, int arrive, int execSize, int timeRemaining, int quantum) {
		super(ID, arrive, execSize, timeRemaining);
		this.quantum = quantum;
	}

	void decreaseQuantum() {
		System.out.print(quantum + " ");
		if(quantum > MINIMUM_QUANTUM) {
			this.quantum--;
			System.out.print(quantum + "\n");
		}
	}
	
	int getQuantum() {
		return quantum;
	}
	
	@Override
	public NRRProcess copy() {
		return new NRRProcess(ID, arrive, execSize, timeRemaining, quantum);
	}
}
