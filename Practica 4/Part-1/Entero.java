package productor_consumidor;

public class Entero {
	private int n;
	
	public Entero(int n) {
		this.n = n;
	}
	
	public void incrementar() {
		n++;
	}
	
	public void decrementar() {
		n--;
	}
	
	public void print() {
		System.out.println(n);
	}
	
	public int intValue() {
		return n;
	}
}
