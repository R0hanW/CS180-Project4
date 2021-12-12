import gui.MainFrame;
import backend.Network;

public class Main {
    private static Main test = new Main();
    private static Network network = new Network();
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() 
        {
            public void run() 
            {
                new Thread(new MainFrame()).start(); // updates gui
                new Thread(network).start(); // refreshes arraylists
                // new Thread(server).start; // always wait for data
                /*
                gui have a client object to send data to server
                server -> network (synchronize write/read)
                 */
                //new MainFrame();
            }
        });
    }
}
