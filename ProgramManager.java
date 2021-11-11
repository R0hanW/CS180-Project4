import java.util.ArrayList;

public class ProgramManager {
    private ArrayList<User> users = new ArrayList<>();
    private ArrayList<Post> posts = new ArrayList<>();

    public void writeFile(){
        //write from arraylist to txt files
        //updates the txt files
    }
    public void readFile(){
        //parse through the lines in the file to the Array list
        //updates the arraylist
    }
    public void addPost(Post post){
        //adds a post
        //updates arraylist and file
    }
    public void removePost(Post post){
        //removes from the array list
        //updates arraylist and file
    }
    public void modifyPost(Post post){
        //modifies post
        //updates arraylist and file
    }
    public void addUser(User user){
        //adds a user
        //updates arraylist and file
    }
    public void removeUser(User user){
        //removes from array list
        //updates arraylist and file
    }
    public void modifyUser(User user){
        //modifies user
        //updates arraylist and file
    }
}
