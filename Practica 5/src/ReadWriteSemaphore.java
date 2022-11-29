import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;

public class ReadWriteSemaphore {
	
	/*
	 * CLASE PARA ASEGURAR LA EXCLUSIÓN MUTUA AL LEER/ESCRIBIR 
	 * EN LAS TABLAS DEL SERVIDOR (info, usuarios)
	 * Utilizamos la técnica de paso de testigo con semáforos, 
	 * concretamente para el problema de readers-writers
	 * */
	
	private int nr, nw, dr, dw;
	private Semaphore r,w;
	private BakeryLock e; //mutex empleado para proteger las variables compartidas nw,nr,dw,dr
	
	public ReadWriteSemaphore(BakeryLock e, Semaphore w, Semaphore r, int nw, int nr, int dw, int dr) {
		this.e = e;
		this.r = r;
		this.w = w;
		this.dw = dw;
		this.dr = dr;
		this.nr = nr;
		this.nw = nw;
	}
	
	public void preLectura(int id) throws InterruptedException {
		e.lock(id);
		if (nw > 0) {
			dr++;
			e.unlock(id);
			r.acquire();
		}
		nr++;
		if (dr > 0) {
			dr--;
			r.release();
		}
		else
			e.unlock(id);
	}
	
	public void postLectura(int id) throws InterruptedException {
		e.lock(id);
		nr--;
		if (nr == 0 && dw > 0) {
			dw--;
			w.release();
		}
		else
			e.unlock(id);
	}
	
	public void preEscritura(int id) throws InterruptedException {
		e.lock(id);
		if (nr > 0 || nw > 0) {
			dw++;
			e.unlock(id);
			w.acquire();
		}
		nw++;
		e.unlock(id);
	}
	
	public void postEscritura(int id) throws InterruptedException {
		e.lock(id);
		nw--;
		if (dr > 0) {
			dr--;
			r.acquire();
		}
		else if (dw > 0) {
			dw--;
			w.release();
		}
		else
			e.unlock(id);
	}
}
