package productor_consumidor;

public class Consumidor extends Thread {
	private BufferMonitor b;
	
	public Consumidor(BufferMonitor b) {
		super();
		this.b = b;
	}
	
	public void run() {
		while(true) {
			Producto p = b.extraer();
		}
	}
}
