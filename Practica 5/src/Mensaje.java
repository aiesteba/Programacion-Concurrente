
import java.io.Serializable;

public abstract class Mensaje implements Serializable {

	private TipoMensaje tipo;
	private String origen;
	private String destino;
	
	public Mensaje(TipoMensaje tipo, String origen, String destino) {
		this.tipo = tipo;
		this.origen = origen;
		this.destino = destino;
	}
	
	public TipoMensaje getTipo() {
		return tipo;
	}
	
	public String getDestino() {
		return destino;
	}
	
	public String getOrigen() {
		return origen;
	}
	
	public abstract String mensaje();
	
}
