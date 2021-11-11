import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
public class ProgramManager {
    private ArrayList<User> users = new ArrayList<>();
    // private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();
    
    public void writeFile(){// aidan

        //writes to Courses.txt
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileWriter(new File("Courses.txt")), true);
            for(Course course: courses) pw.println(course.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void readFile(){ // aidan
        //parse through the lines in the file to the Array list
        //updates the arraylist

        //reads from Courses.txt (won't work unless users has already been read)
        try {
            BufferedReader reader = new BufferedReader(new FileReader("Courses.txt"));
            String message;
            Course course = null;
            Post post = null;
            Comment comment = null;
            String[] messageArr;
            while((message = reader.readLine()) != null){
                if(message.contains("Course")) {
                    //finds everything after colon in message
                    message = message.replaceAll(".*:", "");
                    //.split(",") returns an array that splits the message by comma
                    course = new Course(message.split(",")[0], findUser(message.split(",")[1]));
                }
                else if(message.contains("Post")) {
                    message = message.replaceAll(".*:", "");
                    messageArr = message.split(",");
                    post = new Post(findUser(messageArr[0]), course, messageArr[1], messageArr[2]);
                }
                else if(message.contains("Comment")){
                    message = message.replaceAll(".*:", "");
                    messageArr = message.split(",");
                    comment = new Comment(findUser(messageArr[0]), post, messageArr[2], messageArr[3]);
                    post.addComment(comment);
                }
                else if(message.equals("END OF POST")){
                    course.addPost(post);
                }
                else if(message.equals("END OF COURSE")){
                    course = null;
                }
                else{
                    //should never reach this else statement, for debugging purposes
                    System.out.println("AAAAAAAAAH");
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        }
        

    }
    public void addCourse(){

    }
    public void removeCourse(){

    }
    public void modifyCourse(){

    }
    public void addUser(User user){ // Thanmaya
        //adds a user
        //updates arraylist and file
    }
    public void removeUser(User user){ // Thanmaya
        //removes from array list
        //updates arraylist and file
    }
    public void modifyUser(User user){ // Thanmaya
        //modifies user
        //updates arraylist and file
    }

    public User findUser(String username){
        return (User) users.stream().filter(user -> user.getUsername() == username);
    }
}
