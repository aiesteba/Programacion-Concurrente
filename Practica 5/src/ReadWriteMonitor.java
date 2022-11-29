import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/*
 * CLASE PARA IMPLEMENTAR UN MONITOR DE LECTORES-ESCRITORES
 * Usado para garantizar la exclusión mutua cuando el Oyente intenta
 * acceder a las tablas del Servidor 
 * Utilizamos Lock&Conditions
 */


public class ReadWriteMonitor {

	private int nr, nw;
	private Lock l; 
	private Condition read, write;
	
	
	public ReadWriteMonitor() {
		nr = 0; nw = 0;
		l = new ReentrantLock();
		read = l.newCondition();
		write = l.newCondition();
	}
	
	public void preLectura() {
		l.lock();
		while(nw > 0) {
			try {
				read.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		nr++;
		l.unlock();
	}
	
	public void postLectura() {
		l.lock();
		nr--;
		if(nr == 0) {
			write.signal();
		}
		l.unlock();
	}
	
	public void preEscritura() {
		l.lock();
		while(nr > 0 || nw > 0) {
			try {
				write.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		nw++;		
		l.unlock();
	}
	
	public void postEscritura() {
		l.lock();
		nw--;
		write.signal();
		read.signalAll();
		l.unlock();
	}
	
	
}
