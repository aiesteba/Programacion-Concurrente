package productor_consumidor;

import java.util.ArrayList;

public class BufferMonitor implements Almacen {
	
	private ArrayList<Producto> buff;
	private int tam;
	private int ini, fin, count; //count -> posiciones reales ocupadas
	
	public BufferMonitor(int tam) {
		this.tam = tam;
		buff = new ArrayList<Producto>(tam);
		ini = 0;
		fin = 0;
		count = 0;
	}
	
	synchronized public void almacenar(Producto producto) {
		while (count == tam) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		buff.add(fin, producto);	
		
		System.out.println("Almacenado producto");
		producto.print();
	
		fin = (fin + 1) % tam;
		count = count + 1;
		
		notifyAll();
	}


	synchronized public Producto extraer() {
		while (count == 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		Producto p = buff.get(ini);	
		
		System.out.println("Extraido producto");
		p.print();
		
		ini = (ini + 1) % tam;
		count = count - 1;
		notifyAll();
		
		return p;
	}

}
