
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

/*
*CLASE OYENTE SERVIDOR
*/

public class OS extends Thread {
		
	private static final int puerto_maquina = 1024;
	//
	private boolean conexion;
	//Flujos de entrada y salida
	private ObjectInputStream fin;
	private ObjectOutputStream fout;
	//Entero est�tico para ir asignando puertos a emisores cuando sea necesario
	private static AtomicInteger puerto = new AtomicInteger(1035);
		
	public OS(ObjectInputStream fin, ObjectOutputStream fout, String nombre_usuario) {
		super();
		this.fin = fin;
		this.fout = fout;
		conexion =  true;
	}
	
	public void run() {
		while (conexion) {
			try {
				
				Mensaje m = (Mensaje) fin.readObject();				
				switch (m.getTipo()) {
				case CONFCONEXION:
					InterfazUsuario.mostrar(m.mensaje());
					break;
						
				case CONFCERRAR:
					InterfazUsuario.mostrar(m.mensaje());
					conexion = false;
					break;	
					
				case ERROR:
					InterfazUsuario.mostrar(m.mensaje());
					break;
					
				case CONFPEDIRLISTA:
					InterfazUsuario.mostrar(m.mensaje());
					String s = ((MensajeConfirmacionLista) m).listaToString();
					InterfazUsuario.mostrar(s);
					break;
						
				case PREPARADOSC:
					InterfazUsuario.mostrar(m.mensaje());
					//Creamos un proceso receptor que espere a que le llegue el fichero que ha pedido el usuario
					MensajePreparadoSC m1 = ((MensajePreparadoSC) m);
					Socket sc = new Socket("localhost", m1.getPuerto());
					Receptor r = new Receptor(sc, m1.getUFichero(), m1.getDestino());
					r.start();					
					try {
						r.join();
						sc.close();
						MensajeConfirmacionDescarga m2 = new MensajeConfirmacionDescarga(m.getDestino(), "Servidor", m1.getFichero());
						fout.writeObject(m2);
						fout.flush();
				
					} catch (InterruptedException e2) { e2.printStackTrace(); }
					break;
						
				case EMITIRF:
					InterfazUsuario.mostrar(m.mensaje());
					MensajeEmitirFichero m2 = ((MensajeEmitirFichero) m);
					//Creamos proceso Emisor que envíe el fichero solicitado con un puerto
					int p = asignarPuerto();
					ServerSocket ss = new ServerSocket(p);
					Emisor e = new Emisor(m2.getNombreFichero(), ss);
					e.start();
					//Mandamos un mensaje al servidor para que avise de que está preparado para el envío
					MensajePreparadoCS m3 = new MensajePreparadoCS(m2.getDestino(),m2.getOrigen(), p, 
							                                       m2.getUsuario_peticion(), m2.getNombreFichero());
					fout.writeObject(m3);
					fout.flush();
					
					try {
						e.join();
						ss.close();
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
					break;
						
				default:
					break;
				}
				
				
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	
		}
	}
	
	private static int asignarPuerto() {
		return puerto.getAndAdd(1);
	}
	
	
}
