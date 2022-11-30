package productor_consumidor;

import java.util.ArrayList;
import java.util.concurrent.Semaphore;


public class MiMainPC {

	public static void main(String[] args) {
		
		int N = Integer.valueOf(args[0]), //N productores 
				M = Integer.valueOf(args[1]), //M consumidores
				TAM = Integer.valueOf(args[2]); //tam es el tamaño del buffer
		
		//Nuestros productores y consumidores
		ArrayList<Productor> ps = new ArrayList<Productor>();
		ArrayList<Consumidor> cs = new ArrayList<Consumidor>();
		
		Semaphore mutexC = new Semaphore(1);
		Semaphore mutexP = new Semaphore(1);
		Semaphore full = new Semaphore(0);
		Semaphore empty = new Semaphore(TAM); 
		
		Buffer b = new Buffer(TAM); //Almacén donde guardamos los productos
		
		for (int i = 0; i < N; ++i) {
			Producto p = new Producto(new Entero(i));
			Productor P = new Productor(empty, full, mutexP, b, p);
			ps.add(P);
		}
		
		for (int i = 0; i < M; ++i) {
			Consumidor C = new Consumidor(empty, full, mutexC, b);
			cs.add(C);
		}

		for (int i = 0; i < N; ++i) {
			ps.get(i).start();
		}
		
		for (int i = 0; i < M; ++i) {
			cs.get(i).start();
		}
		
		for (int i = 0; i < N; ++i) {
			try {
				ps.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < M; ++i) {
			try {
				cs.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}

}
