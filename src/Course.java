import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

    public void setName(String name){
        this.name = name;
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
        String out = String.format("Course:%s,%s\n", name, owner.getUsername());
        for(Post post: posts) out += String.format("%s\nEND OF POST\n", post.toString());
        out += "END OF COURSE";
        return out;
    }

    public void displayCourse(){
        System.out.println(name + "\n");
        for (Post p : posts) {
            System.out.println(p.getTopic());
        }
    }

    public void displayDashboard(boolean sortByVotes){
        ArrayList<Comment> commentList = new ArrayList<Comment>();
        posts.stream().forEach(post -> commentList.addAll(post.getComments()));
        // if(commentList.size() <= 1) return commentList;
        if(sortByVotes) commentList.sort(Comparator.comparing(Comment::getVotes));
        else Collections.sort(commentList, (c1, c2) -> c1.getOwner().getName().compareTo(c2.getOwner().getName()));
        System.out.printf("Dashboard for %s\n", name);
        commentList.stream()
            .forEach(comment -> 
            { 
                System.out.printf("(Post: %s)   ");
                comment.displayComment(false);
            });
    }

    public boolean equals(Object o){
        if(!(o instanceof Course)) return false;
        Course obj = (Course) o;
        return obj.getName().equals(this.name) && obj.getOwner().equals(this.owner) && obj.getPosts().equals(this.posts);
    }
}