import java.net.Socket;

public class MensajeConfirmacionDescarga extends Mensaje {

	private String fichero;
	
	public MensajeConfirmacionDescarga(String origen, String destino, String f) {
		super(TipoMensaje.CONFDESCARGA, origen, destino);
		this.fichero = f;
	}

	@Override
	public String mensaje() {
		return getOrigen() + " ha descargado con éxito el fichero " + fichero;
	}
	
	public String getFichero() {
		return fichero;
	}
	
}
