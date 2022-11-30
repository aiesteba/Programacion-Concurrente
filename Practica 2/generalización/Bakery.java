package generalización;

public class Bakery extends Lock {
	private MiLista turnos;
	
	public Bakery(int N) {
		super(N);
		turnos = new MiLista();
		for (int i = 0; i < this.N; i++) {
			  turnos.add(0);
		}
	}
	
	public void lock(int i) {
		turnos.set(i, 1);
		int max = turnos.max();
		turnos.set(i, max + 1);
		
		for (int j = 0; j < this.N; ++j) {
			if (i != j) {
				while (turnos.get(j) != 0 && op(turnos.get(i), turnos.get(j), i,j)) {}
			}
		}
		
	}
	
	public void unlock(int i) {
		turnos.set(i, 0);
	}
	
	private boolean op(int ti, int tj, int i, int j) {
		return (ti > tj) || ((ti == tj) && (i > j));
	}
	
}
