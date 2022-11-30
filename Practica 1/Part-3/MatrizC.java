package MultMatrices3;

public class MatrizC {
	 private final int N;             // number of columns
	 private final int[][] data;   // N-by-N array
	 
	// create N-by-N matrix of 0's
	 public MatrizC(int N) {
	     this.N = N;
	     data = new int[N][N];
	 }

	 public int get(int i, int j) {
		 return data[i][j];
	 }
	 public void modificar(int i, int j, int x) {
		 data[i][j] = x;
	 }
	 
	 public void print() {
		 for (int x=0; x < N; x++) {
			  System.out.print("|");
			  for (int y=0; y < N; y++) {
			    System.out.print (data[x][y]);
			    if (y!=N-1) System.out.print("\t");
			  }
			  System.out.println("|");
			}
	 }
	 
}
