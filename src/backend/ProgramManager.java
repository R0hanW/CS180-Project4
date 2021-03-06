/**
 * ProgramManager.java
 * <p>
 * Handles processing of the program. Includes the list of users and courses in the program, which are
 * initialized on program start up by reading from storage files. Handles adding courses and users
 *
 * @author Aidan Chen, Thanmaya Pattanashetty, Rohan Wadhwa Purdue CS180
 * @version 11/15/21
 */
package backend;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ProgramManager {
    private static ProgramManager instance = null;
    private ArrayList<User> users = new ArrayList<User>();
    // private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<Course>();
    static User currUser;
    private Course currCourse;
    private Post currPost;
    private Comment currComment;

    Object obj;

    public void run() {
        try {
            //writeFile();
            //readFile();
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
		/*while (true) {
            try {
                synchronized (obj) {
                    //readUserFile();
                    //readFile();
                    //if (MainFrame.get().getPanelName() == "Main" || 
                    		  //MainFrame.get().getPanelName() == "Course" || 
                    		  //MainFrame.get().getPanelName() == "Post") {
                    	//MainFrame.get().switchPanel(MainFrame.get().getPanelName());
                    	//MainFrame.get().update();
                    //}

                }
            } catch (Exception e) {

            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("refresh");

        }*/
    }

    public void update() throws IOException {
        Socket socket = new Socket("localhost", 4040);
        ObjectOutputStream output = null;
        ObjectInputStream input = null;
        output = new ObjectOutputStream(socket.getOutputStream());
        input = new ObjectInputStream((socket.getInputStream()));
        output.writeObject(users);
        output.writeObject(courses);
        socket.close();
    }

    //creates one synchronized ProgramManager object that can be called anywhere in code using ProgramManager.get();
    //can read more here https://stackoverflow.com/questions/18125106/make-global-instance-of-class-java/18125286
    public static synchronized ProgramManager get() throws Exception {
        if (instance == null) {
            instance = new ProgramManager();
        }
        return instance;
    }

    public ProgramManager(ArrayList<User> users, ArrayList<Course> courses) {
        this.users = users;
        this.courses = courses;
    }

    public ProgramManager() throws Exception {
        //readUserFile();
        Socket socket = new Socket("localhost", 4040);
        ObjectOutputStream output = null;
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        output = new ObjectOutputStream(socket.getOutputStream());
        output.writeObject("initialize");
        users = (ArrayList<User>) input.readObject();
        courses = (ArrayList<Course>) input.readObject();
        //System.out.println("initialized");
        for (int i = 0; i < users.size(); i++) {
           // System.out.println(users.get(i).toString());
        }
        for (int i = 0; i < courses.size(); i++) {
            //System.out.println(courses.get(i).toString());
        }
        socket.close();
    }

    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser) {
        ProgramManager.currUser = currUser;
    }

    public Course getCurrCourse() {
        return findCourse(currCourse.getName());
    }

    public void setCurrCourse(Course currCourse) {
        this.currCourse = currCourse;
    }

    public Post getCurrPost() {
        return getCurrCourse().findPost(currPost);
        // return currPost;
    }

    public void setCurrPost(Post currPost) {
        this.currPost = currPost;
    }

    public Comment getCurrComment() {
        return getCurrPost().findComment(currComment);
        //return currComment;
    }

    public void setCurrComment(Comment currComment) {
        this.currComment = currComment;
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public synchronized void writeCourseFile() throws Exception {// works
        //write from arraylist to txt files
        //updates the txt files
        //if writing to the user storage file
        Socket socket = new Socket("localhost", 4040);
        ObjectOutputStream output = null;
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        output = new ObjectOutputStream(socket.getOutputStream());
        output.writeObject(courses);
        courses = (ArrayList<Course>) input.readObject();
        socket.close();
    }

    public synchronized void writeUserFile() {// works
        //write from arraylist to txt files
        //updates the txt files
        //if writing to the user storage file
        try {
            Socket socket = new Socket("localhost", 4040);
            ObjectOutputStream output = null;
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());
            output.writeObject(new ArrayList<Integer>());
            output.writeObject(users);
            users = (ArrayList<User>) input.readObject();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public synchronized void readFile() throws Exception {
        //reads from Courses.txt (won't work unless users has already been read)
        //users.clear();
        //courses.clear();
        Socket socket = new Socket("localhost", 4040);
        ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        output.writeObject("refresh");
        users = (ArrayList<User>) input.readObject();
        courses = (ArrayList<Course>) input.readObject();
        socket.close();
    }

    public void readUserFileImport(String filename, boolean isPost, Course course, Post post, User user)
            throws FileNotFoundException {
        File f = new File(filename);
        if (isPost) {
            try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
                String line = bfr.readLine();
                while (line != null) {
                    if (line.contains("|")) {
                        String[] text = line.split("\\|");
                        course.posts.add(new Post(user, course, text[1], text[0]));
                    } else {
                        course.posts.add(new Post(user, course, line, "New Post"));
                    }
                    line = bfr.readLine();
                }
            } catch (FileNotFoundException e) {
                throw e;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            String text = "";
            try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
                String line = bfr.readLine();
                while (line != null) {
                    text += line;
                    post.getComments().add(new Comment(user, post, text));
                    line = bfr.readLine();
                }
            } catch (FileNotFoundException e) {
                throw e;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Successfully created.");
    }

    public synchronized void addCourse(Course course) throws Exception {
        courses.add(course);
        writeCourseFile();
		/*Socket socket = new Socket("localhost", 4040);
		ObjectOutputStream output = null;
		ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
		output = new ObjectOutputStream(socket.getOutputStream());
		output.writeObject(courses);
		courses = (ArrayList<Course>)input.readObject();
		socket.close();*/
    }

    public synchronized void removeCourse(Course course) throws Exception {
        courses.remove(course);
        writeCourseFile();
    }

    public synchronized void modifyCourse(Course oldCourse, String name) throws Exception {
        oldCourse.setName(name);
        writeCourseFile();

    }

    public synchronized void addUser(User user) throws Exception { // works
        users.add(user);
        Socket socket = new Socket("localhost", 4040);
        ObjectOutputStream output = null;
        ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
        output = new ObjectOutputStream(socket.getOutputStream());
        output.writeObject(users);
        users = (ArrayList<User>) input.readObject();
        socket.close();
    }

    public synchronized void removeUser(User user) throws IOException { // works
        user.removeComments();
        users.remove(user);
        Socket socket = new Socket("localhost", 4040);
        ObjectOutputStream output = null;
        output = new ObjectOutputStream(socket.getOutputStream());
        output.writeObject(users);
        socket.close();
        //return false; //if user asked to remove doesnt exist
    }

    public synchronized void modifyUser(User oldUser, String name, String username, String password,
                                        boolean isTeacher) throws IOException { // works
        oldUser.setName(name);
        oldUser.setUsername(username);
        oldUser.setPassword(password);
        oldUser.setIsTeacher(isTeacher);// an object from the array has to be passed
        Socket socket = new Socket("localhost", 4040);
        ObjectOutputStream output = null;
        output = new ObjectOutputStream(socket.getOutputStream());
        output.writeObject(users);
        socket.close();
        // (or another reference object in the array)
    }

    public User findUser(String username) { // works
        // for (int i = 0; i < users.size(); i++) {
        //     if (users.get(i).getUsername().equals(username)) {
        //         return users.get(i);
        //     }
        // }
        // return null;
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }

    public Course findCourse(String courseName) {
        return courses.stream().filter(course -> course.getName().equals(courseName)).findFirst().orElse(null);
    }
	/*
    private void receiveFromGui(String send){
        Client client = new Client(send);
        Server server = new Server();

        new Thread (server).start();
        new Thread (client).start();


    }
	 */
}
