package condicionCarrera;

public class BakerySimple {
	private int t1, t2;
	
	public BakerySimple() {
		super();
		this.t1 = 0;
		this.t2 = 0;
	}
	
	public void lockP1() {
		t1 = 1;
		t1 = t2 + 1;
		
		while (t2 != 0 && t1 > t2);

	}
	
	public void lockP2() {
		t2 = t1 + 1;
		
		while (t1 != 0 && t1 <= t2);

	}
	
	public void unlockP1() {
		t1 = 0;
	}
	
	public void unlockP2() {
		t2 = 0;
	}
	
}
