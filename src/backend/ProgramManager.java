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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class ProgramManager {
    private static ProgramManager instance = null;
    private ArrayList<User> users = new ArrayList<User>();
    // private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<Course>();
    static User currUser;

    //creates one synchronized ProgramManager object that can be called anywhere in code using ProgramManager.get();
    //can read more here https://stackoverflow.com/questions/18125106/make-global-instance-of-class-java/18125286
    public static synchronized ProgramManager get() throws Exception{
        if(instance == null) {
            instance = new ProgramManager();
        }
        return instance;
    }
    public ProgramManager(ArrayList<User> users, ArrayList<Course> courses) {
        this.users = users;
        this.courses = courses;
    }
    
    public ProgramManager() throws Exception {
        readUserFile();
        readFile();
    }

    public User getCurrUser() {
        return currUser;
    }

    public void setCurrUser(User currUser){
        ProgramManager.currUser = currUser;
    }
    public ArrayList<Course> getCourses() {
        return courses;
    }

    

    public void writeFile() throws Exception {// works
        //write from arraylist to txt files
        //updates the txt files
        //if writing to the user storage file
        File f = new File("Users.txt");
        PrintWriter pw = new PrintWriter(new FileWriter(f, false));
        for (int i = 0; i < users.size(); i++) {    //loop to print a user to each line in file
            pw.println(users.get(i).toString());
        }
        pw.flush();
        //writes to Courses.txt
        pw = new PrintWriter(new FileWriter(new File("Courses.txt"), false));
        for (Course course : courses) pw.println(course.toString());
        pw.close();
        // } else {	//if writing to post storage file
        // 	File f = new File("Posts.txt");
        // 	try (PrintWriter pw = new PrintWriter(new FileWriter(f, false))) {
        //     	for (int i = 0; i < users.size(); i++) {	//loop to print a post to each line in file
        //     		pw.println(posts.get(i).toString());
        //     	}
        //     } catch (IOException e) {	//exception handling
        //     	e.printStackTrace();
        //     }
        // }
    }

    public void readFile() throws Exception {
        //reads from Courses.txt (won't work unless users has already been read)
        BufferedReader reader = new BufferedReader(new FileReader("Courses.txt"));
        String message;
        Course course = null;
        Post post = null;
        Comment comment = null;
        String[] messageArr;
        while ((message = reader.readLine()) != null) {
            if (message.contains("Course")) {
                //finds everything after colon in message
                message = message.substring(message.indexOf(":") + 1);
                //.split(",") returns an array that splits the message by comma
                course = new Course(message.split(",")[0], findUser(message.split(",")[1]), Boolean.parseBoolean(message.split(",")[2]));
            } else if (message.contains("Post")) {
                message = message.substring(message.indexOf(":") + 1);
                messageArr = message.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                messageArr[1] = messageArr[1].substring(1, messageArr[1].length() - 1);
                messageArr[2] = messageArr[2].substring(1, messageArr[2].length() - 1);
                post = new Post(findUser(messageArr[0]), course, messageArr[1], messageArr[2], messageArr[3]);
            } else if (message.contains("pollOption")) {
                message = message.substring(message.indexOf(":") + 1);
                post.addPollOption(message, false);
            } else if (message.contains("pollResult")) {
                message = message.substring(message.indexOf(":") + 1);
                post.addPollResult(Integer.parseInt(message));
            } else if (message.contains("userPollVote")) {
                message = message.substring(message.indexOf(":") + 1);
                post.addUserVoter(findUser(message));
            } else if (message.contains("Comment")) {
                message = message.substring(message.indexOf(":") + 1);
                messageArr = message.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                messageArr[1] = messageArr[1].substring(1, messageArr[1].length() - 1);
                comment = new Comment(findUser(messageArr[0]), post, messageArr[1], messageArr[2],
                        Integer.parseInt(messageArr[3]), Double.parseDouble(messageArr[4]));
                post.addComment(comment);
            } else if (message.contains("Reply")) {
                message = message.substring(message.indexOf(":") + 1);
                messageArr = message.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
                messageArr[1] = messageArr[1].substring(1, messageArr[1].length() - 1);
                System.out.println(Arrays.toString(messageArr));
                comment = new Comment(findUser(messageArr[0]), post, messageArr[1], messageArr[2],
                        Integer.parseInt(messageArr[3]), Double.parseDouble(messageArr[4]));
            } else if (message.contains("Upvote")) {
                message = message.substring(message.indexOf(":") + 1);
                comment.addUserUpvote(findUser(message));

            } else if (message.equals("END OF POST")) {
                course.addPost(post);
            } else if (message.equals("END OF COURSE")) {
                courses.add(course);
            }
        }
        reader.close();
    }

    public void readUserFile() { // works
        //parse through the lines in the file to the Array list
        //updates the arraylist
        File f = new File("Users.txt");
        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
            String line = bfr.readLine();
            if (line == null) {
                return;
            }
            while (line != null) {
                int numQuotes = 0;
                int[] quoteIndices = new int[8];    //each line should have 8 quotation marks
                //one pair for each field of a user
                for (int i = 0; i < line.length(); i++) {
                    if (line.charAt(i) == '"') {
                        numQuotes++;
                        if (numQuotes < 9) {
                            quoteIndices[numQuotes - 1] = i;
                        }
                    }
                }

                if (numQuotes != 8) {    //checking for storage format error
                    System.out.println("Internal storage error. Your account may have been deleted.");
                    try (PrintWriter pw = new PrintWriter(new FileWriter(f, false))) {
                        for (int i = 0; i < users.size(); i++) {
                            pw.println(users.get(i).toString());    //salvages user info before error
                        }
                    } catch (IOException e) {    //exception handling
                        e.printStackTrace();
                    }
                    return;
                }
                //adds new user to list of users
                users.add(new User(line.substring(quoteIndices[0] + 1, quoteIndices[1]),
                        line.substring(quoteIndices[2] + 1, quoteIndices[3]),
                        line.substring(quoteIndices[4] + 1, quoteIndices[5]),
                        (line.substring(quoteIndices[6] + 1, quoteIndices[7]).toLowerCase().equals("true"))));
                line = bfr.readLine();
            }
        } catch (IOException e) { //exception handling
            e.printStackTrace();
        }
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

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
    }

    public void modifyCourse(Course oldCourse, String name) {
        oldCourse.setName(name);

    }

    public void addUser(User user) { // works
        users.add(user);
    }

    public boolean removeUser(User user) { // works
        user.removeComments();
        users.remove(user);
        return false; //if user asked to remove doesnt exist
    }

    public void modifyUser(User oldUser, String name, String username, String password, boolean isTeacher) { // works
        oldUser.setName(name);
        oldUser.setUsername(username);
        oldUser.setPassword(password);
        oldUser.setIsTeacher(isTeacher);// an object from the array has to be passed
        // (or another reference object in the array)
    }

    public User findUser(String username) { // works
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username)) {
                return users.get(i);
            }
        }
        return null;
        //return users.stream().filter(user -> user.getUsername() == username).findAny().orElse(null);
    }

    // //returns list of comments sorted by votes in descending order
    // public ArrayList<Comment> sortByVotes(int courseNumber) {
    //     ArrayList<Comment> commentList = new ArrayList<Comment>();
    //     Course course = courses.get(courseNumber);
    //     course.getPosts().stream().forEach(post -> commentList.addAll(post.getComments()));
    //     if(commentList.size() < 2) return commentList;
    //     commentList.sort(Comparator.comparing(Comment::getVotes));
    // 	return commentList;
    // }

    // public ArrayList<Comment> sortByAuthor(Course course){
    //     ArrayList<Comment> out = new ArrayList<Comment>(0);
    //     course.getPosts().stream().forEach(post -> out.addAll(post.getComments()));
    //     if(out.size() <= 1) return out;
    //     Collections.sort(out, (c1, c2) -> c1.getOwner().getName().compareTo(c2.getOwner().getName()));
    //     return out;
    // }
}