import java.util.ArrayList;
import java.io.*;
import java.time.format.DateTimeFormatter;    

public class Comment {
    private User owner;
    private Post post;
    private String content;
    private String timestamp;
    private int votes;
    private double grade;
    private ArrayList<Comment> replies = new ArrayList<Comment>();
    
    public Comment(User owner, Post post, String content){
        this.owner = owner;
        this.post = post;
        this.content = content;
        //get current timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM HH:mm");
        timestamp = java.time.LocalDateTime.now().format(formatter);
        if(replies == null) post.addComment(this);
    }
    
    public Comment(User owner, Post post, String content, String timestamp){
        this.owner = owner;
        this.post = post;
        this.content = content;
        this.timestamp = timestamp;
        this.votes = 0;
    }
    
    public Comment(User owner, Post post, String content, String timestamp, int votes, double grade){
        this.owner = owner;
        this.post = post;
        this.content = content;
        this.timestamp = timestamp;
        this.votes = votes;
        this.grade = grade;
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
    
    public void addVote() {
    	votes++;
    }
    
    public int getVotes() {
    	return votes;
    }
    
    public void setGrade(double grade) {
    	this.grade = grade;
    }
    
    public double getGrade() {
    	return grade;
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
