package condicionCarrera;

public class Incrementador extends Thread {
	private int id, N;
	private Entero x;
	private BakerySimple b;
	
	public Incrementador(String str, int id, int N, Entero x, BakerySimple b) {
		super(str);
		this.id = id;
		this.N = N;
		this.x = x;
		this.b = b;
	}
	
	public void run() {
		for (int i = 0; i < N; ++i) {
			b.lockP1();
			x.incrementar();
			b.unlockP1();
		}
	}

}
