package generalización;

public class LockGenerator {
	
	public static Lock parseType(int type, int N) {
		if (type == 0)
			return new Bakery(N);
		else if (type == 1)
			return new LockTicket(N);
		else if (type == 2)
			return new LockTieBreaker(N);
		return null;
	}

}
