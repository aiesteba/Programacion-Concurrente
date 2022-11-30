package condicionCarrera;

public class Decrementador extends Thread {
	private int id, N;
	private EnteroMonitor x;
	
	public Decrementador (String str, int id, int N, EnteroMonitor x) {
		super(str);
		this.id = id;
		this.N = N;
		this.x = x;
	}
	
	public void run() {
		for (int i = 0; i < N; ++i) {
			x.decrementar();
		}
	}
}
