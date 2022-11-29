

public class MensajeConfirmacionCerrar extends Mensaje {

	public MensajeConfirmacionCerrar(String origen, String destino) {
		super(TipoMensaje.CONFCERRAR, origen, destino);
	}

	public String mensaje() {
		return "Conexion cerrada entre " + getOrigen() + " y "+ getDestino();
	}
}
