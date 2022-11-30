package productor_consumidor;

public class Productor extends Thread {
	private int id;
	private Producto p;
	private BufferMonitor b;
	
	public Productor(BufferMonitor b, Producto p) {
		super();
		this.b = b;
		this.p = p;
	}
	
	public void run() {
		while(true) {
			b.almacenar(p);
		}
	}
}
