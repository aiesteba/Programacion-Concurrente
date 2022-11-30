package condicionCarrera;

public class EnteroMonitor {
	private int n;
	
	public EnteroMonitor(int n) {
		this.n = n;
	}
	
	synchronized public void incrementar() {
		n++;
	}
	
	synchronized public void decrementar() {
		n--;
	}
	
	public void print() {
		System.out.println(n);
	}
}
