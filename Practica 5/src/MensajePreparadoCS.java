
import java.io.ObjectInputStream;
import java.util.concurrent.atomic.AtomicInteger;

public class MensajePreparadoCS extends Mensaje {
	
	private String usuario_peticion;
	private int puerto;
	private String fichero;

	public MensajePreparadoCS(String origen, String destino, int puerto, String u, String fichero) {
		super(TipoMensaje.PREPARADOCS, origen, destino);
		this.usuario_peticion = u;
		this.puerto = puerto;
		this.fichero = fichero;
	}

	@Override
	public String mensaje() {
		return getOrigen() + " indica a " + getDestino() + " que está preparado para mandar la información solicitada";
	}

	public String getUsuarioPeticion() {
		return usuario_peticion;
	}
	
	public String getFichero() {
		return fichero;
	}
	
	public int getPuerto() {
		return puerto;
	}
}
