

public class MensajeConfirmacionConexion extends Mensaje {

	public MensajeConfirmacionConexion(String origen, String destino) {
		super(TipoMensaje.CONFCONEXION, origen, destino);
	}

	public String mensaje() {
		return "Conexion confirmada entre " + getOrigen() + " y " + getDestino();
	}

}
