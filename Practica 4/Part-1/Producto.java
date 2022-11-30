package productor_consumidor;

public class Producto {
	
	private Entero e;
	
	public Producto(Entero e) {
		this.e = e;
	}
	
	public void incrementar() {
		e.incrementar();
	}
	public void print() {
		e.print();
	}
	
}
