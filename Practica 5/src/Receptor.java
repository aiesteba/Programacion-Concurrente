

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Receptor extends Thread {

	private Socket sc;
	private String usuario_fichero;
	private String usuario_peticion;
	

	public Receptor(Socket sc, String usuario_fichero, String usuario_peticion) {
		super();
		this.sc = sc;
		this.usuario_fichero = usuario_fichero;
		this.usuario_peticion = usuario_peticion;
	}
	
	public void run() {
		try {		
			
			//creamos los flujos del canal
			ObjectOutputStream fout = new ObjectOutputStream(sc.getOutputStream());
			ObjectInputStream fin  = new ObjectInputStream(sc.getInputStream());
			
			//Mandamos mensaje pidiendo el fichero 
			MensajePedirFicheroP2P m = new MensajePedirFicheroP2P(usuario_peticion, usuario_fichero);
			fout.writeObject(m);
			fout.flush();
			
			//Escuchamos el canal hasta que encontramos un mensaje
			Mensaje m1 =  (Mensaje) fin.readObject();
			if (m1.getTipo() == TipoMensaje.ENVIARFICHERO) {
				String fichero = ((MensajeEnviarFichero) m1).getNombreFichero();
				String contenido = ((MensajeEnviarFichero) m1).getContenidoFichero();
				InterfazUsuario.mostrar("Se ha descargado el fichero " + fichero + " exitosamente.");
			}
			
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
