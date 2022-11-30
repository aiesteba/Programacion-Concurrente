package CondicionCarrera2;

public class Incrementador extends Thread {
	private int N;
	private Entero x;
	
	public Incrementador (String str, int N, Entero x) {
		super(str);
		this.N = N;
		this.x = x;
	}
	
	public void run() {
		for (int i = 0; i < N; ++i)
			x.incrementar();
	}

}
