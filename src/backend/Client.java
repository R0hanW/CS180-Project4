package backend;
import java.io.*;
import java.net.*;

public class Client {
	public Socket socket;
	//connect
	public void connect(String serverName, int port) throws UnknownHostException, IOException, ClassNotFoundException {
		socket = new Socket(serverName, port);
	}
	
	//close
	public void close() throws IOException {
		socket.close();
	}
	
	//send string to server
	public void sendToServer(String input) throws IOException {
		PrintWriter writer = new PrintWriter(socket.getOutputStream());
		writer.write(input);
		writer.println();
		writer.flush();
		writer.close();
	}
}
