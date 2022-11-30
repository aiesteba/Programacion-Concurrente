package condicionCarrera;

public class Decrementador extends Thread {
	private int id, N;
	private Entero x;
	private BakerySimple b;
	
	public Decrementador (String str, int id, int N, Entero x, BakerySimple b) {
		super(str);
		this.id = id;
		this.N = N;
		this.x = x;
		this.b = b;
	}
	
	public void run() {
		for (int i = 0; i < N; ++i) {
			b.lockP2();
			x.decrementar();
			b.unlockP2();
		}
	}
}
