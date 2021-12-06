package backend;
import java.io.*;
import java.net.*;

public class Server implements Runnable{
    private ProgramManager manager;
    private ServerSocket serverSocket;
    private Socket socket;
    private String input;
    private static int portNum = 4241;

    public void run(){
        acceptClient();
        this.input = receiveFromClient();
        close();
        try {
            //manager.writeFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Server() {

        try {
            serverSocket = new ServerSocket(portNum);
        } catch (IOException e) {
            e.printStackTrace();
        }
        portNum ++;
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
    /*
    public void addUser(User user){
        Server[] servers = {new Server(), new Server(), new Server(), new Server()};
        for (int i =0; i < 4; i++) {
            new Thread (this).start();
        }

    }
     */

    public void resetPortNum(){
        portNum = 4242;
    }

    public String getInput() {
        return input;
    }
}
