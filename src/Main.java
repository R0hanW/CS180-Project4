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
                new Thread(network).start();
                new Thread(new MainFrame()).start();
                //new MainFrame();
            }
        });
    }
}
