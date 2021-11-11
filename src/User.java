import java.util.ArrayList;

public class User {
    private String name;
    private String userType; // student or teacher
    private String username;
    private String password;
    private ArrayList<Post> posts = new ArrayList<Post>();

    public User() {
        name = "";
        username = "";
        password = "";
    }
    
    public User(String name, String userType, String username, String password) {
    	this.name = name;
    	this.userType = userType;
    	this.password = password;
    	this.username = username;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void updatePosts(){
        // Grabs from the Users.txt file (only the posts from the user themselves)
    }

}
