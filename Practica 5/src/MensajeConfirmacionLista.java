

import java.util.Map;

public class MensajeConfirmacionLista extends Mensaje {

	private String infoPrint;

	public MensajeConfirmacionLista(String origen, String destino, String infoPrint) {
		super(TipoMensaje.CONFPEDIRLISTA, origen, destino);
		this.infoPrint = infoPrint;
	}

	public String mensaje() {
		return getOrigen() + " envia la lista de usuarios conectados a "+ getDestino();
	}
	
	//mostramos cuantos usuarios hay, sus nombres e informacion
	public String listaToString() {
		return infoPrint;
	}
	
	
}
