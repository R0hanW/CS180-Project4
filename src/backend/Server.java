package backend;
import java.io.*;
import java.net.*;

public class Server implements Runnable{
    //private ProgramManager manager;
    private ServerSocket serverSocket;
    private Socket socket;
    private String input;

    public void run(){
        acceptClient();
        this.input = receiveFromClient();
        close();
    }
    public Server() {
        try {
            serverSocket = new ServerSocket(4242);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void acceptClient(){
        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //   System.out.println("acccClient");
    }
    public String receiveFromClient(){
        String line = "";
        try {
            BufferedReader bfr = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            line = bfr.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println("receiveClient");
        return line;
    }

    public void close(){
        try {
            serverSocket.close();
            socket.close();
        } catch (IOException e) {

        }
        // System.out.println("closeServer");
    }


    public String getInput() {
        return input;
    }
}
