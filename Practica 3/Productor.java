package productor_consumidor;

import java.util.concurrent.Semaphore;

public class Productor extends Thread {
	
	private Semaphore empty;
	private Semaphore full;
	private Semaphore mutex;
	private Producto p;
	private Buffer b;
	
	public Productor(Semaphore empty, Semaphore full, Semaphore mutex, Buffer b, Producto p) {
		super();
		this.empty = empty;
		this.full = full;
		this.mutex = mutex;
		this.b = b;
		this.p = p;
	}
	
	public void run() {
		
		while(true) {
			try { empty.acquire(); } 
			catch (InterruptedException e) { e.printStackTrace(); }
			
			try { mutex.acquire(); } 
			catch (InterruptedException e) { e.printStackTrace(); }
			
			b.almacenar(p);
			mutex.release();
			full.release();
		}
	}
}
