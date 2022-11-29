

public class MensajeEmitirFichero extends Mensaje {
	
	
	private String fichero;
	private String usuario_peticion;
	
	public MensajeEmitirFichero(String origen, String destino, String fichero, String usuario_peticion) {
		super(TipoMensaje.EMITIRF, origen, destino);
		this.fichero = fichero;
		this.usuario_peticion = usuario_peticion;
	}

	public String mensaje() {
		return getOrigen() + " le pide a " + getDestino() + " que emita el fichero " + fichero + " que ha pedido " + usuario_peticion;
	}
	
	public String getUsuario_peticion() {
		return usuario_peticion;
	}
	
	public String getNombreFichero() {
		return fichero;
	}
	
}