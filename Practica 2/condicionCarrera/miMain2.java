package condicionCarrera;

import java.util.ArrayList;

public class miMain2 {

	public static void main(String[] args) {
		Entero n = new Entero(0);
		int N = Integer.valueOf(args[0]), M = Integer.valueOf(args[1]);
		
		ArrayList<Incrementador> inc = new ArrayList<Incrementador>();
		ArrayList<Decrementador> dec = new ArrayList<Decrementador>();
	
		BakerySimple b = new BakerySimple(); 
		
		for (int i = 0; i < M; ++i) {
			Incrementador I = new Incrementador(String.valueOf(i), i, N, n, b);
			Decrementador D = new Decrementador(String.valueOf(i), M + i, N, n, b);
			inc.add(I);
			dec.add(D);
		}
		
		for (int i = 0; i < M; ++i) {
			inc.get(i).start();
			dec.get(i).start();
		}
		
		for (int i = 0; i < M; ++i) {
			try {
				inc.get(i).join();
				dec.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		n.print();
	}
}

