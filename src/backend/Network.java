package backend;

import java.io.IOException;

import gui.MainFrame;

public class Network implements Runnable{
    private ProgramManager manager;
    private static Object obj =  new Object();

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

        synchronized (obj) {
            try {
            	manager.writeUserFile();
                manager.writeCourseFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void addCourse(String name, User owner, boolean studentsCanCreatePosts){
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

        try {
            for (int i = 0; i < 2; i++) {
                serverThreads[i].join();
                clientThreads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(servers[0].getInput());

        try {
			manager.addCourse(
			        new Course(servers[0].getInput(), owner,
			                Boolean.parseBoolean(servers[1].getInput())
			        )
			);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        //writeFile();

        synchronized (obj) {
            try {
            	manager.writeUserFile();
                manager.writeCourseFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void addPost(User owner, Course course, String content, String topic){
        Server[] servers = {new Server(4242), new Server(4243)};
        Client[] clients = {
                new Client(content,4242),
                new Client(topic,4243),
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

        try {
            for (int i = 0; i < 2; i++) {
                serverThreads[i].join();
                clientThreads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
			manager.findCourse(course.getName()).addPost(new Post(
			        owner, course,servers[0].getInput(), servers[1].getInput()
			));
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        synchronized (obj) {
            try {
                manager.writeCourseFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void addPost(Course course, Post post){
        try {
			manager.findCourse(course.getName()).addPost(post);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        synchronized (obj) {
            try {
                manager.writeUserFile();
                manager.writeCourseFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void addComment(Course course, Post post, Comment comment){
        ((manager.findCourse(course.getName())).findPost(post)).addComment(comment);
        synchronized (obj) {
            try {
                manager.writeUserFile();
                manager.writeCourseFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void addReply(Course course, Post post, Comment comment, Comment reply){
        ((manager.findCourse(course.getName())).findPost(post)).findComment(comment).addReply(reply);
        synchronized (obj) {
            try {
                manager.writeUserFile();
                manager.writeCourseFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void writeFile(){
        synchronized (obj) {
            try {
                manager.writeUserFile();
                manager.writeCourseFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
