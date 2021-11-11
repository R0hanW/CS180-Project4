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
    private ArrayList<Post> posts = new ArrayList<>();

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
    	} else {	//if writing to post storage file
    		File f = new File("Posts.txt");
    		try (PrintWriter pw = new PrintWriter(new FileWriter(f, false))) {
            	for (int i = 0; i < users.size(); i++) {	//loop to print a post to each line in file
            		pw.println(posts.get(i).toString());
            	}
            } catch (IOException e) {	//exception handling
            	e.printStackTrace();
            }
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
    public void addPost(Post post){ // Thanmaya
        //adds a post
        //updates arraylist and file
    }
    public void removePost(Post post){// Thanmaya
        //removes from the array list
        //updates arraylist and file
    }
    public void modifyPost(Post post){// Thanmaya
        //modifies post
        //updates arraylist and file
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
}
