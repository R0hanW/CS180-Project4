package backend;

public class Network {
    private ProgramManager manager;

    public Network(){
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addUser(String name, String username, String password, boolean isTeacher){
    /*    Server server = new Server();
        Client client = new Client(name, 4242);
        Thread serverThread = new Thread (server);
        Thread clientThread = new Thread (client);

        serverThread.start();
        clientThread.start();
       */
        Server[] servers = {new Server(4242), new Server(4243), new Server(4244), new Server(4245)};
        Client[] clients = {
                new Client(name,4242),
                new Client(username,4243),
                new Client(password,4244),
                new Client(String.valueOf(isTeacher),4245)
        };
        Thread[] serverThreads = new Thread[4];
        Thread[] clientThreads = new Thread[4];
        for (int i = 0; i < 4; i ++){
            serverThreads[i] = new Thread(servers[i]);
            clientThreads[i] = new Thread(clients[i]);
        }

        for (int i = 0; i < 4; i++){
            serverThreads[i].start();
            clientThreads[i].start();
        }
         /*
        for (int i =0; i < 4; i++) {
            new Thread (servers[i]).start();
            new Thread (clients[i]).start();
        }
         */

        try {
            for (int i = 0; i < 4; i++) {
                serverThreads[i].join();
                clientThreads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        manager.addUser(
                new User(servers[0].getInput(),
                        servers[1].getInput(),
                        servers[2].getInput(),
                        Boolean.getBoolean(servers[3].getInput())
                )
        );
        //servers[0].resetPortNum();
    }

}
