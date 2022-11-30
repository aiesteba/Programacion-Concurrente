package generalización;

public class IncrementadorGeneral extends Thread {
	private int id, N;
	private Entero x;
	private Bakery b;
	
	public IncrementadorGeneral(String str, int id, int N, Entero x, Bakery b) {
		super(str);
		this.id = id;
		this.N = N;
		this.x = x;
		this.b = b;
	}
	
	public void run() {
		for (int i = 0; i < N; ++i) {
			b.lock(id);
			x.incrementar();
			b.unlock(id);
		}
	}

}
