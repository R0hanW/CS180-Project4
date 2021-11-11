import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
public class ProgramManager {
    private ArrayList<User> users = new ArrayList<>();
    // private ArrayList<Post> posts = new ArrayList<>();
    private ArrayList<Course> courses = new ArrayList<>();
    public void writeFile(boolean isUser){// aidan
        //write from arraylist to txt files
        //updates the txt files
    	if (isUser) {	//if writing to the user storage file
    		File f = new File("Users.txt");
    		try (PrintWriter pw = new PrintWriter(new FileWriter(f, false))) {
            	for (int i = 0; i < users.size(); i++) {	//loop to print a user to each line in file
            		pw.println(users.get(i).toString());
            	}
            } catch (IOException e) {	//exception handling
            	e.printStackTrace();
            }
        }
        //writes to Courses.txt
        PrintWriter pw;
        try {
            pw = new PrintWriter(new FileWriter(new File("Courses.txt")), true);
            for(Course course: courses) pw.println(course.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void readFile(){
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
    public void readUserFile(){ // aidan
        //parse through the lines in the file to the Array list
        //updates the arraylist
    	File f = new File("Users.txt");
        try (BufferedReader bfr = new BufferedReader(new FileReader(f))) {
        	String line = bfr.readLine();
        	while (line != null) {
        		int numQuotes = 0;
        		int[] quoteIndices = new int[8];	//each line should have 8 quotation marks
        											//one pair for each field of a user
        		for(int i = 0; i < line.length(); i++) {
        			if (line.charAt(i) == '"') {
        				numQuotes++;
        				if (numQuotes < 9) {
            				quoteIndices[numQuotes] = i;        					
        				}
        			}
        		}
        		
        		if (numQuotes != 8) {	//checking for user format error
        			System.out.println("Internal storage error. Your account may have been deleted.");
        	        try (PrintWriter pw = new PrintWriter(new FileWriter(f, false))) {
        	        	for (int i = 0; i < users.size(); i++) {
        	        		pw.println(users.get(i).toString());	//salvages user info before error
        	        	}
        	        } catch (IOException e) {	//exception handling
        	        	e.printStackTrace();
        	        }
        			return;
        		}
        		
        		//adds new user to list of users
        		users.add(new User(line.substring(quoteIndices[0] + 1, quoteIndices[1]),
        				line.substring(quoteIndices[2] + 1, quoteIndices[3]), 
        				line.substring(quoteIndices[4] + 1, quoteIndices[5]),
        				line.substring(quoteIndices[6] + 1, quoteIndices[7])));
        		line = bfr.readLine();
        	}
        } catch (FileNotFoundException e) {		//exception handling
        	e.printStackTrace();
        } catch (IOException e) {
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
		users.add(user);
		writeFile(true);
    }
    public boolean removeUser(User user){ // Thanmaya
		for (int i = 0; i <users.size(); i++){
			if(users.get(i).equals(user)){
				users.remove(i);
				writeFile(true);
				return true;
			}
		}
		return false; //if user asked to remove doesnt exist
    }
    public void modifyUser(User oldUser, String name, String username, String usertype, String password){ // Thanmaya
		oldUser.setName(name);
		oldUser.setUsername(username);
		oldUser.setPassword(password);
		writeFile(true); // an object from the array has to be passed (or another reference object in the array)
    }

    public User findUser(String username){
        return (User) users.stream().filter(user -> user.getUsername() == username);
    }
}
