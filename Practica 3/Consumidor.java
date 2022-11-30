package productor_consumidor;

import java.util.concurrent.Semaphore;

public class Consumidor extends Thread {
	
	private Semaphore empty;
	private Semaphore full;
	private Semaphore mutex;
	private Buffer b;
	
	public Consumidor(Semaphore empty, Semaphore full, Semaphore mutex, Buffer b) {
		super();
		this.empty = empty;
		this.full = full;
		this.mutex = mutex;
		this.b = b;
	}
	
	public void run() {
		
		while(true) {
			try { full.acquire(); } 
			catch (InterruptedException e) { e.printStackTrace(); }
			
			try { mutex.acquire(); } 
			catch (InterruptedException e) { e.printStackTrace(); }
			
			b.extraer();
			mutex.release();
			empty.release();
		}
	}
}
