

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MensajePreparadoSC extends Mensaje {
	
	private String usuario_fichero;
	private int puerto;
	private String fichero;

	public MensajePreparadoSC(String origen, String destino, String usuario_fichero, int puerto, String f) {
		super(TipoMensaje.PREPARADOSC, origen, destino);
		this.puerto = puerto;
		this.usuario_fichero = usuario_fichero;
		this.fichero = f;
	}

	@Override
	public String mensaje() {
		return getOrigen() + " indica a " + getDestino() + " que " + usuario_fichero + " está preparado para mandar el fichero.";
	}
		
	public String getUFichero() {
		return usuario_fichero;
	}
	
	public String getFichero() {
		return fichero;
	}

	public int getPuerto() {
		return puerto;
	}
	
	
}
