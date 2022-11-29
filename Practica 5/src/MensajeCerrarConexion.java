
public class MensajeCerrarConexion extends Mensaje {

	public MensajeCerrarConexion(String origen, String destino) {
		super(TipoMensaje.CERRARCONEXION, origen, destino);
	}

	public String mensaje() {
		return "Peticion de cierre de conexion entre " + getOrigen() + " y " + getDestino();
	}

}
