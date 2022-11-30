package generalización;

import java.util.ArrayList;

public class MiLista {
	private ArrayList<Entero> l;
	
	public MiLista() {
		this.l = new ArrayList<Entero>();
	}
	
	public int max() {
		int max = l.get(0).intValue();
		for (int i = 0; i < this.l.size(); ++i) {
			if (l.get(i).intValue() > max) {
				max = l.get(i).intValue();
			}
		}
		return max;
	}
	
	public void add(int value) {
		l.add(new Entero(value));
	}
	
	public int get(int index) {
		return l.get(index).intValue();
	}
	
	public void set(int index, int value) {
		l.set(index, new Entero(value));
	}

}
