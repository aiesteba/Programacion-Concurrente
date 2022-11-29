public class BakeryLock {
	private MiLista turnos;
	
	
	public BakeryLock() {
		super();
		turnos = new MiLista();
	}
	
	public void lock(int i) {
		turnos.set(i, 1);
		int max = turnos.max();
		turnos.set(i, max + 1);
		
		for (int j = 0; j < turnos.size(); ++j) {
			if (i != j) {
				while (turnos.get(j) != 0 && op(turnos.get(i), turnos.get(j), i,j)) {}
			}
		}
		
	}
	
	public void addCliente() {
		turnos.add(0);
	}
	
	public void unlock(int i) {
		turnos.set(i, 0);
	}
	
	private boolean op(int ti, int tj, int i, int j) {
		return (ti > tj) || ((ti == tj) && (i > j));
	}
	
}