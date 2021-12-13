package backend;

import java.util.*;
import java.io.*;
import java.net.*;
public class ClientThread implements Runnable {
	private Socket socket;

	public ClientThread(Socket socket) {
		this.socket = socket;
	}

	//looked at https://stackoverflow.com/questions/12895450/sending-an-arrayliststring-from-the-server-side-to-the-client-side-over-tcp-us
	@Override
	public void run(){
		ObjectOutputStream output = null;
		ObjectInputStream input = null;
		try {
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream((socket.getInputStream()));
			Object o = input.readObject();
			if (o instanceof Collection) {
				if (((List) o).get(0) instanceof User) {
					RunningServer.users = (ArrayList<User>)o;
					RunningServer.writeFile();
					RunningServer.update();
					output.writeObject(RunningServer.users);
				} else if (((List) o).get(0) instanceof Course) {
					RunningServer.courses = (ArrayList<Course>)o;
					RunningServer.writeFile();
					RunningServer.update();
					output.writeObject(RunningServer.courses);
				} else {
					Object o2 = input.readObject();
					Object o3 = input.readObject();
					RunningServer.users = (ArrayList<User>)o2;
					RunningServer.courses = (ArrayList<Course>)o3;
					RunningServer.writeFile();
					RunningServer.update();
					output.writeObject(RunningServer.users);
					output.writeObject(RunningServer.courses);
				}
			} else {
				output.writeObject(RunningServer.users);
				output.writeObject(RunningServer.courses);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
					//socket.close();
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
