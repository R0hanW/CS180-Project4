import java.util.ArrayList;

public class User {
    private String name; 
    private String username;
    private String password;
    private ArrayList<Comment> comments = new ArrayList<Comment>(); 
    private ArrayList<Post> posts = new ArrayList<Post>();
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

    public ArrayList<Comment> getComments(){
        return this.comments;
    }

    public boolean isTeacher() {
        return this.isTeacher;
    }
    public void removeComments(){
        comments.stream().forEach(comment -> comment.removeComment());
    }
    public ArrayList<Post> getPosts(){
        return this.posts;
    }
    public void removePosts(){
        posts.stream().forEach(post -> post.removePost());
    }
    public void updatePosts(){
        // Grabs from the Users.txt file (only the posts from the user themselves)
    }
    public boolean equals(Object o){
        if(!(o instanceof User)) return false;
        User obj = (User) o;
        return obj.getName().equals(this.name) && obj.getPassword().equals(this.password) && obj.getUsername().equals(this.username) 
            && obj.getComments().equals(this.comments) && obj.getPosts().equals(this.posts);
    }

}
