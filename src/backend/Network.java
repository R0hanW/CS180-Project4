/***
 * This class which is part of the backend, helps in setting up the Network and concurrency of the project
 * @author Team 043
 * @version 12/13/2021
 *
 */
package backend;

import java.io.IOException;

import gui.MainFrame;

public class Network implements Runnable {
    private ProgramManager manager;
    private static Object obj = new Object();

    public void run() {
        while (true) {
            try {
                synchronized (obj) {
                    manager.readFile();
                }
            } catch (Exception e) {

            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           // System.out.println("refresh");

        }
    }

    public Network() {
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
