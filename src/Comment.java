import java.util.ArrayList;
import java.time.format.DateTimeFormatter;    

public class Comment {
    private User owner;
    private Post post;
    private String content;
    private String timestamp;
    private ArrayList<Comment> replies = new ArrayList<Comment>();
   
    public Comment(User owner, Post post, String content){
        owner = this.owner;
        post = this.post;
        content = this.content;
        //get current timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        timestamp = java.time.LocalDateTime.now().format(formatter);
        if(replies == null) post.addComment(this);
    }

    public Comment(User owner, Post post, String content, String timestamp){
        owner = this.owner;
        post = this.post;
        content = this.content;
        timestamp = this.timestamp;
    }

    public User getOwner() {
        return this.owner;
    }

    public Post getPost() {
        return this.post;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArrayList<Comment> getReplies() {
        return this.replies;
    }

    public void addReply(Comment reply){
        replies.add(reply);
    }

    public void removeReply(Comment reply){
        replies.remove(reply);
    }

    public void removeComment(){
        post.removeComment(this);
    }

    public void displayComment(){
        System.out.println(owner.getName() + "\t" + timestamp);
        System.out.println(content);
        for(Comment r : replies){
            System.out.println(r);
        }
        System.out.println();
    }

    public String toString(){
       String out = String.format("%s,%s,%s,%s\nReplies:");
       for(Comment reply: replies) out += String.format("%s;", reply.toString());
       return out;
    }
}
