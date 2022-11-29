import java.util.ArrayList;

public class MensajeConexion extends Mensaje {
	
	private ArrayList<String> informacion_usuario;
	
	public MensajeConexion(String origen, String destino, ArrayList<String> informacion) {
		super(TipoMensaje.CONEXION, origen, destino);
		informacion_usuario = informacion;
	}

	public String mensaje() {
		return "Conexion establecida entre "  + getOrigen() + " y " + getDestino();
	}
	
	public ArrayList<String> getInfo() {
		return informacion_usuario;
	}
	

}
