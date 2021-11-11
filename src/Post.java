import java.util.ArrayList;

public class Post {
    private User owner;
    private String content;
    private String topic;
    private Course course;
    private ArrayList<Comment> comments = new ArrayList<Comment>();

    public Post(User owner, Course course, String content, String topic){
        this.owner = owner;
        this.content = content;
        this.topic = topic;
    }

    public Post(){
        this.owner = new User();
        this.content = "";
        this.topic = "";
    }

    public String getContent(){
        return this.content;
    }

    public void setContent(String content){
        this.content = content;
    }

    public String getTopic(){
        return this.topic;
    }

    public void setTopic(String topic){
        this.topic = topic;
    }
    
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addComment(User author, String content){
        comments.add(new Comment(author, this, content));
    }

    public void removeComment(Comment comment){
        comments.remove(comment);
    }

    public void removePost(){
        course.removePost(this);
    }

    public String toString(){
        String out = String.format("Post:%s,%s,%s\nComments:\n", owner.getUsername(), content, topic);
        for(Comment comment: comments) out += String.format("%s\n", comment.toString());
        return out;
    }

    public void displayPost(){
        //TODO
    }

    
}
