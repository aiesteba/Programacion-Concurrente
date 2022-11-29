

public class MensajePedirFicheroP2P extends Mensaje {
	
	
	public MensajePedirFicheroP2P(String origen, String destino) {
		super(TipoMensaje.PEDIRFICHEROC, origen, destino);
	}

	public String mensaje() {
		return getOrigen() + " pide el fichero  a " + getDestino();
	}

	
}
