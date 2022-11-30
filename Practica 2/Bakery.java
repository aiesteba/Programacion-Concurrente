package generalización;
import java.util.ArrayList;
import java.util.Collections;

public class Bakery {
	private int P;
	volatile private ArrayList<Integer> turnos;
	//private int max;
	
	public Bakery(int P) {
		super();
		this.P = P;
		turnos = new ArrayList<Integer>();
		for (int i = 0; i < P; i++) {
			  turnos.add(0);
		}
		//this.max = 0;
	}
	
	public void lock(int i) {
		turnos.set(i, 1);
		Integer max = Collections.max(turnos);
		turnos.set(i, max + 1);
		
		for (int j = 0; j < P; ++j) {
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
