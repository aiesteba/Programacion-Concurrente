
public class MensajePedirLista extends Mensaje {

	public MensajePedirLista(String origen, String destino) {
		super(TipoMensaje.PEDIRLISTA, origen, destino);
	}

	public String mensaje() {
		return getOrigen() + " ha pedido la lista de usuarios";
	}
}
