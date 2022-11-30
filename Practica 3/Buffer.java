package productor_consumidor;

import java.util.ArrayList;

public class Buffer implements Almacen {
	
	private ArrayList<Producto> buff;
	private int tam;
	private int ini, fin;
	
	public Buffer(int tam) {
		this.tam = tam;
		buff = new ArrayList<Producto>(tam);
		ini = 0;
		fin = 0;
	}
	
	public void almacenar(Producto producto) {
		/*debug
		System.out.println("Almacenado: ");
		producto.print();
		*/
		buff.add(fin, producto);
		fin = (fin + 1) % tam;
	}


	public Producto extraer() {
		Producto p = buff.get(ini);
		ini = (ini + 1) % tam;
		/*debug
		System.out.println("Extraido: ");
		p.print();
		*/
		return p;
	}

}
