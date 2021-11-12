import java.util.ArrayList;

public class Course {
    String name;
    User owner;

    ArrayList<Post> posts = new ArrayList<Post>();
    
    public Course(String name, User owner){
        this.name = name;
        this.owner = owner;  
    }

    public String getName(){
        return this.name;
    }

    public User getOwner(){
        return this.owner;
    }

    public ArrayList<Post> getPosts(){
        return this.posts;
    }

    public void addPost(Post post){
        posts.add(post);
    }

    public boolean addPost(Post post, User user){ 
        if(!user.isTeacher()) return false;
        posts.add(post);
        return true;
    }
    public boolean removePost(Post post, User user){
        if(!user.isTeacher()) return false;
        posts.remove(post);
        return true;
    }

    public void removePost(Post post){
        posts.remove(post);
    }

    public boolean modifyPost(Post oldPost, String content, String topic, User user){
        if(!user.isTeacher()) return false;
        posts.set(posts.indexOf(oldPost), new Post(user, this, content, topic));
        return true;
    }

    public String toString(){
        String out = String.format("Course:%s,%s", name, owner.getUsername());
        for(Post post: posts) out += String.format("%s\nEND OF POST\n", post.toString());
        out += "END OF COURSE\n";
        return out;
    }

    public void displayCourse(){
        System.out.println(name + "\n");
        for (Post p : posts) {
            System.out.println(p.getTopic());
        }
    }

    public boolean equals(Object o){
        if(!(o instanceof Course)) return false;
        Course obj = (Course) o;
        return obj.getName().equals(this.name) && obj.getOwner().equals(this.owner) && obj.getPosts().equals(this.posts);
    }
}