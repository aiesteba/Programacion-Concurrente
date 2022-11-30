package generalización;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class LockTicket extends Lock {
	volatile private int next;
	private AtomicInteger number;
	volatile private MiLista turn;

	public LockTicket(int N) {
		super(N);
		this.number = new AtomicInteger(1);
		this.next = 1;
		this.turn = new MiLista();
		for (int i = 0; i < this.N; i++) {
			  turn.add(0);
		}
	}
	
	public void lock(int i) {
		turn.set(i, number.getAndAdd(1));
		while (turn.get(i) != next) {}
		//turn = turn;
	}
	
	public void unlock(int i) {
		next = next + 1;
	}
	
}
