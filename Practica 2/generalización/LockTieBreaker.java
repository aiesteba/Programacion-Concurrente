package generalización;
import java.util.ArrayList;

public class LockTieBreaker extends Lock {
	private MiLista in, last;

	public LockTieBreaker(int N) {
		super(N);
		this.in = new MiLista();
		this.last = new MiLista();
		for (int i = 0; i < N; i++) {
			in.add(0);
			last.add(0);
		}
	}
	
	public void lock(int i) {
		for (int j = 1; j < N; ++j) {
			in.set(i, j);
			last.set(j, i);
			for (int k = 1; k < N; ++k) {
				if (i != k)
					while (in.get(k) >= in.get(i) && last.get(j) == i);
			}
		}
	}
	
	public void unlock(int i) {
		in.set(i, 0);
	}
}
