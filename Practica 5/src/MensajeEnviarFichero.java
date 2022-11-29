

public class MensajeEnviarFichero extends Mensaje {

	private String contenidoFichero, fichero;
	
	public MensajeEnviarFichero(String origen, String destino, String contenido, String fichero) {
		super(TipoMensaje.ENVIARFICHERO, origen, destino);
		contenidoFichero = contenido;
		this.fichero = fichero;
	}

	public String mensaje() {
		return getOrigen() + " envia el fichero a "+ getDestino();
	}
	
	public String getContenidoFichero() {
		return contenidoFichero;
	}
	
	public String getNombreFichero() {
		return fichero;
	}
}
