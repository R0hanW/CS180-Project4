package backend;

public class Network implements Runnable{
    private ProgramManager manager;
    private static Object obj =  new Object();

    public void run() {
        while (true) {
            try {
                synchronized (obj) {
                    manager.readFile();
                    manager.readUserFile();
                }
            } catch (Exception e) {

            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("refresh");

        }
    }

    public Network(){
        try {
            manager = ProgramManager.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addUser(String name, String username, String password, boolean isTeacher){
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
        synchronized (obj) {
            try {
                manager.writeFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void addCourse(String name, User owner, boolean studentsCanCreatePosts){
    /*    Server server = new Server();
        Client client = new Client(name, 4242);
        Thread serverThread = new Thread (server);
        Thread clientThread = new Thread (client);

        serverThread.start();
        clientThread.start();
       */
        Server[] servers = {new Server(4242), new Server(4243)};
        Client[] clients = {
                new Client(name,4242),
                new Client(String.valueOf(studentsCanCreatePosts),4243)
        };
        Thread[] serverThreads = new Thread[2];
        Thread[] clientThreads = new Thread[2];
        for (int i = 0; i < 2; i ++){
            serverThreads[i] = new Thread(servers[i]);
            clientThreads[i] = new Thread(clients[i]);
        }

        for (int i = 0; i < 2; i++){
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
            for (int i = 0; i < 2; i++) {
                serverThreads[i].join();
                clientThreads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        manager.addCourse(
                new Course(servers[0].getInput(), owner,
                        Boolean.getBoolean(servers[1].getInput())
                )
        );
        synchronized (obj) {
            try {
                manager.writeFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //servers[0].resetPortNum();
    }
    public void addPost(Course course, Post post){

        manager.findCourse(course.getName()).addPost(post);
        System.out.println(course);
        System.out.println("array list course: ");
        System.out.println(manager.findCourse(course.getName()));
      //  System.out.println(course == manager.findCourse(course.getName()));
        writeFile();

    }
    public void writeFile(){
        synchronized (obj) {
            try {
                manager.writeFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
