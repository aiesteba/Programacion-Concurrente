
public class MensajeError extends Mensaje {

	private String msg;
	
	public MensajeError( String origen, String destino, String msg) {
		super(TipoMensaje.ERROR, origen, destino);
		this.msg = msg;
	}

	@Override
	public String mensaje() {
		return msg;
	}

}
