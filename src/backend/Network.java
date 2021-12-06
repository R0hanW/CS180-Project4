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
        Server server = new Server();
        Client client = new Client(name, 4242);
        new Thread (server).start();
        new Thread (client).start();
        /*
        Server[] servers = {new Server(), new Server(), new Server(), new Server()};
        Client[] clients = {
                new Client(name,4242),
                new Client(username,4243),
                new Client(password,4244),
                new Client(String.valueOf(isTeacher),4245)
        };
        for (int i =0; i < 4; i++) {
            new Thread (servers[i]).start();
            new Thread (clients[i]).start();
        }
         */
        manager.addUser(
                new User(server.getInput(),
                        username,
                        password,
                        isTeacher));
        //servers[0].resetPortNum();
    }

}
