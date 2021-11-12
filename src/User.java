import java.util.ArrayList;

public class User {
    private String name; 
    private String username;
    private String password;
    private ArrayList<Comment> comments = new ArrayList<Comment>(); 
    private boolean isTeacher;

    public User() {
        name = "";
        username = "";
        password = "";
    }

    public User(String name, String username, String password, boolean isTeacher){
        this.name = name;
        this.username = username;
        this.password = password;
        this.isTeacher = isTeacher;
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
    public boolean isTeacher() {
        return this.isTeacher;
    }
    public void setIsTeacher(boolean isTeacher){this.isTeacher = isTeacher;}
    public void addComment(Comment comment){
        comments.add(comment);
    }
    public void removeComments(){
        comments.stream().forEach(comment -> comment.removeComment());
    }
    public String toString() {
    	return String.format("User{name=\"%s\",username=\"%s\",password=\"%s\",isTeacher=\"%b\"}", 
    			name, username, password, isTeacher);
    }

}
