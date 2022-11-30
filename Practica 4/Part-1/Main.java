package productor_consumidor;
import java.util.ArrayList;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		int N = Integer.valueOf(args[0]), //N productores 
			M = Integer.valueOf(args[1]), //M consumidores
			TAM = Integer.valueOf(args[2]); //tam es el tamaño del buffer
		
		//Nuestros productores y consumidores
		ArrayList<Productor> ps = new ArrayList<Productor>();
		ArrayList<Consumidor> cs = new ArrayList<Consumidor>();
		
		BufferMonitor b = new BufferMonitor(TAM); //Almacén donde guardamos los productos


		
		for (int i = 0; i < N; ++i) {
			Producto p = new Producto(new Entero(i));
			Productor P = new Productor(b, p);
			ps.add(P);
		}
		
		for (int i = 0; i < M; ++i) {
			Consumidor C = new Consumidor(b);
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
