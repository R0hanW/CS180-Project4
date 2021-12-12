/**
 * Course.java
 * <p>
 * Represents a course in the discussion forum. Has a list of posts inside of the the course and includes a name
 * and owner. A user can add, modify, or remove posts from here.
 *
 * @author Rohan Wadwha Purdue CS180
 * @version 11/15/21
 */

package backend;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Course implements Serializable {
    private String name;
    private User owner;
    private Boolean studentsCanCreatePosts;

    ArrayList<Post> posts = new ArrayList<Post>();

    public Course(String name, User owner) {
        this.name = name;
        this.owner = owner;
        this.studentsCanCreatePosts = false;
    }

    public Course(String name, User owner, Boolean studentsCanCreatePosts) {
        this.name = name;
        this.owner = owner;
        this.studentsCanCreatePosts = studentsCanCreatePosts;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return this.owner;
    }

    public boolean studentsCanCreatePosts() {
        return studentsCanCreatePosts;
    }

    public void setStudentsCanCreatePosts(boolean studentsCanCreatePosts) {
        this.studentsCanCreatePosts = studentsCanCreatePosts;
    }

    public ArrayList<Post> getPosts() {
        return this.posts;
    }

    public void addPost(Post post) {
        posts.add(post);
    }

    public boolean addPost(Post post, User user) {
        if (!user.isTeacher() || !studentsCanCreatePosts) return false;
        posts.add(post);
        return true;
    }

    public boolean removePost(Post post, User user) {
        if (!user.isTeacher()) return false;
        posts.remove(post);
        return true;
    }

    public void removePost(Post post) {
        posts.remove(post);
    }

    public boolean modifyPost(Post oldPost, String content, String topic, User user) {
        if (!user.isTeacher()) return false;
        posts.set(posts.indexOf(oldPost), new Post(user, this, content, topic));
        return true;
    }

    public String toString() {
        String author;
        if (owner.getUsername() == null) {
            author = "Deleted user";
        } else {
            author = owner.getUsername();
        }
        String out = String.format("Course:%s,%s,%b\n", name, author, studentsCanCreatePosts);
        for (Post post : posts) out += String.format("%s\nEND OF POST\n", post.toString());
        out += "END OF COURSE";
        return out;
    }

    public boolean displayCourse() {
        //System.out.println(name + "\n");
        if (posts.size() == 0) {
            System.out.println("There are no posts currently in this course");
            return false;
        }
        for (int i = 0; i < posts.size(); i++) {
            System.out.println("[" + (i + 1) + "]" + posts.get(i).getTopic());
        }
        return true;
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

    public Post findPost(Post post){
        for (int i =0; i < posts.size(); i ++){
            if (posts.get(i).getContent().equals(post.getContent()) && posts.get(i).getTimestamp().equals(post.getTimestamp())){
                System.out.println();
                return posts.get(i);
            }
        }
        return null;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, owner, posts);
    }
}