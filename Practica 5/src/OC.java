
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.Semaphore;

public class OC extends Thread {
	
	//atributos para la concurrencia en OC
	private ReadWriteSemaphore semaphore; //Exclusión mutua para la tabla 'info'
	private ReadWriteMonitor monitor; //Exclusión mutua para la tabla 'usuarios'

	private ObjectInputStream fin;
	private ObjectOutputStream fout;
		
	//tabla con los usuarios que contienen cada información
	private Map<String, ArrayList<String>> info;
	private Map<String, Par> usuarios;
	
	private int id;
		
	public OC(int id,ObjectInputStream fin, ObjectOutputStream fout, Map<String, ArrayList<String>> info,  Map<String, Par> usuarios,
			   ReadWriteSemaphore s, 
			   ReadWriteMonitor m) {
		super();
		this.id = id;
		this.fin = fin;
		this.fout = fout;
		this.info = info;
		this.usuarios = usuarios;
		
		//concurrencia
		this.monitor = m;
		this.semaphore = s;
	}
	
	
	public void run() {
		boolean conectado = true; 
		
		while (conectado) {
			try {
				//Leemos el mensaje que ha escuchado por el canal
				Mensaje m =  (Mensaje) fin.readObject();
				String origen = m.getOrigen();
				String destino = m.getDestino();
				
				InterfazUsuario.mostrar(m.mensaje());
				
				//Realizamos las operaciones necesarias según el tipo de mensaje
				switch (m.getTipo()) { 
				
				case CONEXION:
					conexion(m,origen,destino);
					break;
					
				case PEDIRLISTA:
					pedirLista(origen, destino);
					break;
					
				case CERRARCONEXION:
					cerrarConexion(origen, destino);
					conectado = false;
					break;
					
				case PEDIRFICHERO:
					pedirFichero(m, origen, destino);
					break;
					
				case PREPARADOCS:
					preparadoCS(m, origen, destino);
					break;
					
				case CONFDESCARGA:
					confirmacionDescarga(m, origen, destino);
					
				default:
					break;		
				}
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SocketException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
				
	}
	
	


	/*
	 * M�TODOS CON LA FUNCIONALIDAD DEL PROCESO SEGÚN EL TIPO DE MENSAJE
	 */
	
	//Al tener una descarga éxito, tenemos que mostrar en la tabla que un usuario (origen) ahora posee otro fichero más
	private void confirmacionDescarga(Mensaje m, String origen, String destino) throws InterruptedException {
		semaphore.preEscritura(id);
		String descarga = ((MensajeConfirmacionDescarga) m).getFichero();
		info.get(origen).add(descarga); 
		semaphore.postEscritura(id);
	}
	
	//Para establecer la conexión entre un nuevo usuario y el servidor
	private void conexion(Mensaje m, String origen , String destino) throws Exception, IOException, InterruptedException {
		//registramos al usuario en el sistema
		monitor.preLectura();
		if (usuarios.containsKey(origen)) {
			monitor.postLectura();
			MensajeError me = new MensajeError(destino,origen, "Ya existe un usuario con ese nombre en el sistema.");
			fout.writeObject(me);
			fout.flush();
		}
		else {
			monitor.postLectura();
			monitor.preEscritura();
			usuarios.put(origen, new Par(fin,fout));
			monitor.postEscritura();
						
			//guardamos la información del usuario 'origen' en las tablas
			ArrayList<String> infoc = ((MensajeConexion) m).getInfo();
			semaphore.preEscritura(id);
			info.put(origen, infoc);
			semaphore.postEscritura(id);
			
			//Mandamos al usuario 'origen' un mensaje confirmando la conexión entre ambos
			MensajeConfirmacionConexion m1 = new MensajeConfirmacionConexion(destino, origen);
			fout.writeObject(m1);
			fout.flush();
		}
	
	}
	
	//Cerramos conexión entre el usuario 'origen' y el servidor
	private void cerrarConexion(String origen, String destino) throws Exception {
		semaphore.preEscritura(id);
		//Eliminamos su informaci�n de las tablas
		if (info.remove(origen)!= null) {
			monitor.preEscritura();
			//Desconectamos al usuario
			 if(usuarios.remove(origen)!= null) {
				 //No ha habido ningún problema elimnando la informaci�n, as� que manda mensaje al usuario confirmando la desconexión
				MensajeConfirmacionCerrar m3 = new MensajeConfirmacionCerrar(destino,origen);
			        fout.writeObject(m3);
				fout.flush();
			 }
			 else throw new Exception("Error al cerrar conexi�n");
			 monitor.postEscritura();
		}
		else throw new Exception("Error al cerrar conexi�n");
		semaphore.postEscritura(id);
	}
	
	//El usuario 'origen' ha pedido la lista de usuarios conectados al sistema
	private void pedirLista(String origen, String destino) throws IOException, InterruptedException {
		//Escribimos la información
		semaphore.preLectura(id);
		String infoPrint = info();
		semaphore.postLectura(id);

		//El servidor manda un mensaje con la lista de usuarios
		MensajeConfirmacionLista m2 = new MensajeConfirmacionLista(destino, origen, infoPrint);
		fout.writeObject(m2);
		fout.flush();
	}

	//El usuario 'origen' ha pedido descargar un fichero 
	private void pedirFichero(Mensaje m, String origen, String destino) throws Exception {
		String fichero = ((MensajePedirFichero) m).getNombreFichero();
				
		//Recorremos la tabla 'info' hasta que encuentra a un usuario que posee el fichero solicitado
		String usuario_fichero = " ";
		boolean found = false;
		semaphore.preLectura(id);		
		for(String key : info.keySet()) {
			for (String f : info.get(key)) {
				if (f.equals(fichero)) {
					usuario_fichero = key;
					found = true;
				}
				if (found) break;
			}
			if (found) break;
		}
		semaphore.postLectura(id);
		
		
		//buscamos su flujo de entrada 
		ObjectOutputStream foutC2 = null;
		monitor.preLectura();
		if (!usuario_fichero.equals(" ")) {
		    foutC2 = usuarios.get(usuario_fichero).getFout();
			//Si hemos encontrado al usuario, el servidor le manda un mensaje para que lo emita
			if (foutC2 != null) {
				monitor.postLectura();
				MensajeEmitirFichero m4 = new MensajeEmitirFichero(destino, usuario_fichero, fichero, m.getOrigen());
				foutC2.writeObject(m4);
				foutC2.flush();
			}
			else {
				monitor.postLectura();
				throw new Exception("Error al buscar flujo del usuario al pedir fichero");
			}
		}
		else {
			monitor.postLectura();
			MensajeError me = new MensajeError(destino,origen, "El fichero " + fichero + " no se pudo encontrar en el sistema.");
			fout.writeObject(me);
			fout.flush();
		}
		
		
	}
	
	//El usuario 'origen' está preparado para enviar el fichero que posee que le han pedido
	private void preparadoCS(Mensaje m, String origen, String destino) throws Exception {
		MensajePreparadoCS mm = (MensajePreparadoCS) m;
		int puerto = mm.getPuerto();
		String usuario_peticion = mm.getUsuarioPeticion();
		String usuario_fichero = m.getOrigen();
		
		monitor.preLectura();
		//Buscamos el flujo de salida del usuario que ha pedido el fichero
		ObjectOutputStream fout1 = usuarios.get(usuario_peticion).getFout();
		monitor.postLectura();
	
		//Mandamos un mensaje al usuario de la petición desde el servidor para que se prepare para la descarga
		MensajePreparadoSC m5 = new MensajePreparadoSC(destino, usuario_peticion, usuario_fichero, puerto, mm.getFichero());
		fout1.writeObject(m5);
		fout1.flush();
	}
	
	
	
	private String info() {
		String s = "En el sistema hay "+ info.size() + " usuarios: " + "\n";
		for(String key : info.keySet()) {
			s = s + key + ":  " + info.get(key).toString() + "\n";
		}
		return s;
	}
	
}
