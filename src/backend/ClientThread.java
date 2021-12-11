package backend;

import java.util.*;
import java.io.*;
import java.net.*;
public class ClientThread implements Runnable {
	private Socket socket;

	public ClientThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		ObjectOutputStream output = null;
		ObjectInputStream input = null;
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream((socket.getInputStream()));

			//if((ArrayList<User>)input.readObject() instanceof ArrayList<User>) {
			Object o = input.readObject();
			RunningServer.users = (ArrayList<User>)o;
			//RunningServer.writeFile();
			//RunningServer.update();
			//}
			//if((ArrayList<Course>)input.readObject() instanceof ArrayList<Course>) {
			o = input.readObject();
			RunningServer.courses = (ArrayList<Course>)o;
			//RunningServer.writeFile();
			//RunningServer.update();
			//}
			for (int i = 0; i < RunningServer.users.size(); i++) {
				System.out.println(RunningServer.users.get(i).toString());
			}
			for (int i = 0; i < RunningServer.courses.size(); i++) {
				System.out.println(RunningServer.courses.get(i).toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
				if (input != null) {
					input.close();
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
	}
}
