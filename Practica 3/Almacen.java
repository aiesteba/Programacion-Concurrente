package productor_consumidor;

public interface Almacen {
/**
* Almacena (como ultimo) un producto en el almacen. Si no hay
* hueco el proceso que ejecute el metodo bloquear´a hasta que lo
* haya.
*/
public void almacenar(Producto producto);
/**
* Extrae el primer producto disponible. Si no hay productos el
* proceso que ejecute el m´etodo bloquear´a hasta que se almacene un
* dato.
*/
public Producto extraer();
}