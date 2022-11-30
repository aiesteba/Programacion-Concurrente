package generalización;

import java.util.ArrayList;

public class miMainGeneral {

	public static void main(String[] args) {
		Entero n = new Entero(0);
		int N = Integer.valueOf(args[0]), M = Integer.valueOf(args[1]);
		
		ArrayList<IncrementadorGeneral> inc = new ArrayList<IncrementadorGeneral>();
		ArrayList<DecrementadorGeneral> dec = new ArrayList<DecrementadorGeneral>();
	
		Bakery b = new Bakery(2*M); 
		
		for (int i = 0; i < M; ++i) {
			IncrementadorGeneral I = new IncrementadorGeneral(String.valueOf(i), i, N, n, b);
			DecrementadorGeneral D = new DecrementadorGeneral(String.valueOf(i), M + i, N, n, b);
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

