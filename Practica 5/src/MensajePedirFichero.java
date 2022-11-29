

public class MensajePedirFichero extends Mensaje {
	
	private String nombreFichero;
	
	public MensajePedirFichero(String origen, String destino, String nombreFichero) {
		super(TipoMensaje.PEDIRFICHERO, origen, destino);
		this.nombreFichero = nombreFichero;
	}

	public String mensaje() {
		return getOrigen() + " pide el fichero " + nombreFichero;
	}
	
	public String getNombreFichero() {
		return nombreFichero;
	}
}
