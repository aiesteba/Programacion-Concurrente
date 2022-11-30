package MultMatrices3;

import java.util.ArrayList;

public class miMain3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int N = Integer.valueOf(args[0]);
		
		int[][] A = new int[N][N];
		int[] Af = new int[N];
		int[][] B = new int[N][N];
		MatrizC C = new MatrizC(N);
		
		for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                A[i][j] = (int) Math.random()*100+1;
		
		for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                B[i][j] = (int) (Math.random()*100+1);
		
		
		ArrayList<CalculaFilas> f = new ArrayList<CalculaFilas>();
		
		for (int i = 0; i < N; ++i) {
			Af = A[i];
			CalculaFilas cf = new CalculaFilas(String.valueOf(i), Af, B, C);
			f.add(cf);
		}
		
		for (int i = 0; i < N; ++i) {
			f.get(i).start();
		}
		
		for (int i = 0; i < N; ++i)
			try {
				f.get(i).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		C.print();
	}

}
