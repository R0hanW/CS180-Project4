package backend;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client implements Runnable{
    private Socket socket;
    private String input;
    private int portNum;

    public void run(){
        //System.out.println(portNum);
        connect("localhost",portNum);
        sendToServer(this.input);
        close();
    }

    public Client(String input, int portNum){
        this.input = input;
        this.portNum = portNum;

    }
    public String getInput(){
        return input;
    }

    //connect
    public void connect(String serverName, int port){
        try {
            socket = new Socket(serverName, port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //  System.out.println("clientconnect");
    }

    //close
    public void close() {
        try {
            socket.close();
        } catch (IOException e) {

        }
        // System.out.println("clientclose");
    }

    //send string to server
    public void sendToServer(String input) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        writer.write(input);
        writer.println();
        writer.flush();
        writer.close();
        // System.out.println("sendtoserver");
    }
}
