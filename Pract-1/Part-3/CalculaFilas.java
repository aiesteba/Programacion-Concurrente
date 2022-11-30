package MultMatrices3;

public class CalculaFilas extends Thread {

	private int N, i;
	private int[] A;
	private int[][] B;
	MatrizC C;
	
	public CalculaFilas(String str, int[] A, int[][] B, MatrizC C) {
		super(str);
		this.i = Integer.valueOf(str);
		this.N = A.length;
		this.A = A;
		this.B = B;
		this.C = C;
	}
	
	public void run() {
		for (int j = 0; j < N; ++j) {
			for (int k = 0; k < N; ++k) {
				int x = C.get(i,j);
				C.modificar(i, j, x + A[k]*B[k][j]);
			}
		}
	}
}
