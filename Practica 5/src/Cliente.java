//EDURNE RUIZ Y AITOR ESTEBAN

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;


public class Cliente {

	//Atributos compartidos para todos los clientes
	private static final int puerto_maquina = 1024;
	private static String ipMaquina = "localhost";
	
	//Entero estático para ir asignando puertos a emisores cuando sea necesario
	private static AtomicInteger puerto = new AtomicInteger(1035);
		
	private String nombre_usuario;
	private ArrayList<String> ficheros; //información del cliente

	//Para establecer las conexiones
	private Socket sc;
	private ObjectInputStream fin;
	private ObjectOutputStream fout;
	
	public Cliente() {
		super();
		ficheros = new ArrayList<String>();
	}
	
	public void run() {
		
		//pedimos por teclado el nombre del usuario y su fichero
		InterfazUsuario.mostrar("Introduce tu nombre de usuario: ");
		nombre_usuario = InterfazUsuario.pedirString();
		
		InterfazUsuario.mostrar("Introduce el nombre de tu fichero: ");
		String f = InterfazUsuario.pedirString();
		ficheros.add(f);
		
		//socket para la comunicación con el servidor
		//creamos los flujos del canal
		try {
			sc = new Socket(ipMaquina, puerto_maquina);
			fout = new ObjectOutputStream(sc.getOutputStream());
			fin  = new ObjectInputStream(sc.getInputStream());
		} catch (UnknownHostException e2) {
			e2.printStackTrace();
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		
		
		MensajeConexion m = new MensajeConexion(nombre_usuario, "Servidor", ficheros);
		try {
			fout.writeObject(m);
			fout.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	
		
		//creamos el proceso que escucha al servidor
		OS os = new OS(fin, fout, nombre_usuario);
		os.start();
		
		//Menu para que el usuario escoga lo que quiere hacer
		try {
			while(menu());
			os.join();
			sc.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private boolean menu() throws IOException {
		int opcion = InterfazUsuario.menuUsuario();
		boolean conectado = true;
		
		switch (opcion) {
		case 1:
			MensajePedirLista m0 = new MensajePedirLista(nombre_usuario, "Servidor");
			fout.writeObject(m0);	
			fout.flush();
			break;
		case 2:
			InterfazUsuario.mostrar("Introduce el nombre del fichero que deseas: ");
			String fichero = InterfazUsuario.pedirString();
			MensajePedirFichero m1 = new MensajePedirFichero(nombre_usuario, "Servidor",fichero);
			fout.writeObject(m1);
			fout.flush();
			break;
		case 3:
			MensajeCerrarConexion m2 = new MensajeCerrarConexion(nombre_usuario, "Servidor");
			fout.writeObject(m2);
			fout.flush();
			
			conectado = false;
			break;
		default:
			InterfazUsuario.mostrar("Opcion no valida");
			break;
		}
		return conectado;
	}
	
	public static void main(String[] args) {
		Cliente c = new Cliente();
		c.run();
	}

}
