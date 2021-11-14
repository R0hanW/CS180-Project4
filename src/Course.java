import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Objects;

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
        out += "END OF COURSE\n";
        return out;
    }

    public void displayCourse(){
        //System.out.println(name + "\n");
        for (int i = 0; i < posts.size(); i++) {
            System.out.println("["+ (i+1) + "]" + posts.get(i).getTopic());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) &&
                Objects.equals(owner, course.owner) &&
                Objects.equals(posts, course.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, posts);
    }
}