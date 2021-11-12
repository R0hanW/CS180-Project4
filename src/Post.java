import java.util.ArrayList;
import java.time.format.DateTimeFormatter;  

public class Post {
    private User owner;
    private String content;
    private String topic;
    private Course course;
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private String timestamp;

    public Post(User owner, Course course, String content, String topic){
        this.owner = owner;
        this.content = content;
        this.topic = topic;
        //get current timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        timestamp = java.time.LocalDateTime.now().format(formatter);
    }

    public Post(User owner, Course course, String content, String topic, String timestamp){
        this.owner = owner;
        this.content = content;
        this.topic = topic;
        this.timestamp = timestamp;
    }

    public Post(){
        this.owner = new User();
        this.content = "";
        this.topic = "";
    }

    public User getOwner(){
        return this.owner;
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
    
    public String getTimestamp(){
        return this.timestamp;
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

    public Course getCourse(){
        return this.course;
    }

    public void removePost(){
        course.removePost(this);
    }

    public String toString(){
        String out = String.format("Post:%s,%s,%s,%s\nComments:\n", owner.getUsername(), content, topic, timestamp);
        for(Comment comment: comments) out += String.format("%s\n", comment.toString());
        return out;
    }

    public void displayPost(){
        //TODO
    }

    public boolean equals(Object o){
        if(!(o instanceof Post)) return false;
        Post obj = (Post) o;
        return obj.getOwner().equals(this.owner) && obj.getContent().equals(this.content) && obj.getTopic().equals(this.topic) && obj.getCourse().equals(this.course);
    } 
}
