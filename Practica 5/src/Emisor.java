

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Emisor extends Thread {
		
	private String nombreFichero;
	private ServerSocket ss;
	
	public Emisor(String nf, ServerSocket ss) {
		this.ss = ss;
		this.nombreFichero = nf;
	}
	
	public void run() {
		
		try {
			Socket s = ss.accept();
			
			ObjectOutputStream fout = new ObjectOutputStream(s.getOutputStream());
			ObjectInputStream fin  = new ObjectInputStream(s.getInputStream());
			
			Mensaje m1 =  (Mensaje) fin.readObject();
			if(m1.getTipo() == TipoMensaje.PEDIRFICHEROC) {				
				//leer fichero del usuario
				
				String contenido = LeeArchivo(nombreFichero);
				
				MensajeEnviarFichero m = new MensajeEnviarFichero(m1.getDestino(), m1.getOrigen(), contenido, nombreFichero);
				fout.writeObject(m);
				fout.flush();
			}
			
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public String LeeArchivo(String archivo) throws FileNotFoundException, IOException { 
    	String linea, texto;
    	texto = "";
        FileReader f = new FileReader(archivo); 
        BufferedReader b = new BufferedReader(f); 
        while((linea = b.readLine())!=null) { 
        	texto = texto + linea; 
        } 
        b.close(); 
        return texto;
	} 
}
