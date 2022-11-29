//AITOR ESTEBAN Y EDURNE RUIZ

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Servidor {
	
	private static final int PUERTO = 1024;
	
	//atributos para la concurrencia en OC
	private int nr, nw, dr, dw;
	private Semaphore e,r,w;
	private BakeryLock b;
	
	private static AtomicInteger id = new AtomicInteger(); 
	
	private ServerSocket ss;
	
	//tabla con la informacion de fin y fout de cada usuario
	private Map<String, Par> usuarios;
	
	//tabla con los usuarios que contienen cada información
	private Map<String, ArrayList<String>> info;
	
	public Servidor() {
		super();
		usuarios = new HashMap<String,Par>();
		info = new HashMap<String, ArrayList<String>>();
		
		//concurrencia
		nr = 0; nw = 0; dr= 0;dw = 0;
		e = new Semaphore(1); //mutex
		r = new Semaphore(0);
		w = new Semaphore(0);
		b = new BakeryLock();
	}
	
	public void run() {
		try {
			ss = new ServerSocket(PUERTO);
			
			while (true) {
				Socket s = ss.accept();
				ObjectOutputStream fout = new ObjectOutputStream(s.getOutputStream());
				ObjectInputStream fin  = new ObjectInputStream(s.getInputStream());
				
				//se le pasa la informacion compartida que habra que garantizar corrección en el acceso concurrente
				b.addCliente();
				ReadWriteSemaphore sem = new ReadWriteSemaphore(b,w,r,nw,nr,dw,dr);
				ReadWriteMonitor m = new ReadWriteMonitor();
				
				int idc = asignarId();
				
				OC oc = new OC(idc, fin, fout, info, usuarios, sem, m);
				oc.start();	
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) { e.printStackTrace(); }
		}
	}
	
	private static int asignarId() {
		return id.getAndAdd(1);
	}
	
	public static void main(String[] args) {
		Servidor s = new Servidor();
		s.run();
	}

}
