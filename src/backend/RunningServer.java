package backend;

import java.net.*;
import java.io.*;
import java.util.*;

public class RunningServer {
	public static ArrayList<User> users = new ArrayList<User>();
	public static ArrayList<Course> courses = new ArrayList<Course>();
	
	//https://www.oracle.com/java/technologies/jpl2-socket-communication.html
	public static void main(String[] args) throws Exception{
		readUserFile();
		readFile();
		ServerSocket serverSocket = null;
    	try {
    		serverSocket = new ServerSocket(4040);
			serverSocket.setReuseAddress(true);
    		while(true) {
    			Socket socket = serverSocket.accept();
    			ClientThread client = new ClientThread(socket);
    			new Thread(client).start();
    		}
    	} catch (IOException e) {
    		e.printStackTrace();
    	} finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	public static synchronized void readFile() throws Exception {
        //reads from Courses.txt (won't work unless users has already been read)
        courses.clear();
        BufferedReader reader = new BufferedReader(new FileReader("Courses.txt"));
        String message;
        Course course = null;
        Post post = null;
        Poll poll = null;
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
            } else if(message.contains("Poll")) {
                poll = new Poll();
            } else if(message.contains("pollOption")) {
                message = message.substring(message.indexOf(":") + 1);
                poll.addPollOption(message, false);
            } else if (message.contains("pollResult")) {
                message = message.substring(message.indexOf(":") + 1);
                poll.addPollResult(Integer.parseInt(message));
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

            } else if(message.equals("END OF POLL")) {
                post.addPoll(poll);
            } else if (message.equals("END OF POST")) {
                course.addPost(post);
            } else if (message.equals("END OF COURSE")) {
                courses.add(course);
            }
        }
        reader.close();
    }
	
	public static synchronized void readUserFile() { // works
        //parse through the lines in the file to the Array list
        //updates the arraylist
        users.clear();
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
	
    public synchronized static void writeFile() throws Exception {// works
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

    }
    
    public static User findUser(String username) { // works
        // for (int i = 0; i < users.size(); i++) {
        //     if (users.get(i).getUsername().equals(username)) {
        //         return users.get(i);
        //     }
        // }
        // return null;
        return users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElse(null);
    }
    
    public static void update() {
    	readUserFile();
    	try {
			readFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
}
