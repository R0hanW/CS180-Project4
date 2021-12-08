/**
 * Comment.java
 * <p>
 * Represents a comment in our discussion forum. Keeps track of an owner, the post it belongs to, its text
 * content, a timestamp, a grade, its upvotes, and its own list of comments.
 *
 * @author Rohan Wadwha Purdue CS180
 * @version 11/15/21
 */
package backend;
import java.util.ArrayList;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Comment {
    private User owner;
    private Post post;
    private String content;
    private String timestamp;
    private int votes;
    private double grade;
    private ArrayList<Comment> replies = new ArrayList<Comment>(0);
    private ArrayList<User> userUpvotes = new ArrayList<User>();

    public Comment(User owner, Post post, String content) {
        this.owner = owner;
        this.post = post;
        this.content = content;
        //get current timestamp
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");
        timestamp = java.time.LocalDateTime.now().format(formatter);
        if (replies == null) post.addComment(this);
    }

    public Comment(User owner, Post post, String content, String timestamp) {
        this.owner = owner;
        this.post = post;
        this.content = content;
        this.timestamp = timestamp;
        this.votes = 0;
    }

    public Comment(User owner, Post post, String content, String timestamp, int votes, double grade) {
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

    public void setPost(Post post) {
        this.post = post;
    }

    public ArrayList<Comment> getComments() {
        return replies;
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

    public void removeVote() {
        votes--;
    }
    
    public int getVotes() {
        return votes;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void addUserUpvote(User user) {
        userUpvotes.add(user);
    }

    public ArrayList<User> getUserUpvotes() {
        return userUpvotes;
    }

    public double getGrade() {
        return grade;
    }

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public ArrayList<Comment> getReplies() {
        return this.replies;
    }

    public void addReply(Comment reply) {
        replies.add(reply);
    }

    public void removeReply(Comment reply) {
        replies.remove(reply);
    }

    public void removeComment() {
        post.removeComment(this);
    }

    public void displayComment(boolean displayReplies) {
        System.out.println(owner.getName() + "\t" + timestamp);
        System.out.println(content);
        System.out.printf("Likes: %d\n", votes);
        if (displayReplies) {
            for (Comment r : replies) r.displayComment(false);
        }
        System.out.println();
    }

    public void displayComment(boolean displayReplies, boolean displayGrade) {
        if (displayGrade) System.out.println(owner.getName() + "\t" + timestamp + " Grade" + grade);
        else System.out.println(owner.getName() + "\t" + timestamp);
        System.out.println(content);
        System.out.printf("Likes: %d\n", votes);
        if (displayReplies) {
            for (Comment r : replies) r.displayComment(false, false);
        }
        System.out.println();
    }

    public String toString() {
        content = content.replace("\n", " ");
        String out = String.format("%s,\"%s\",%s,%s,%s\n", owner.getUsername(), content, timestamp,
                Integer.toString(votes), Double.toString(grade));
        for (Comment reply : replies) out += String.format("Reply:%s\n", reply.toString(false));
        for (User user : userUpvotes) out += String.format("Upvote:%s\n", user.getUsername());
        return out;
    }

    public String toString(boolean displayReplies) {
        if (displayReplies == false)
            return String.format("%s,\"%s\",    %s,%s,%s\n", owner.getUsername(), content, timestamp,
                    Integer.toString(votes), Double.toString(grade));
        String out = String.format("%s,\"%s\",%s,%s,%s\n", owner.getUsername(), content, timestamp,
                Integer.toString(votes), Double.toString(grade));
        for (Comment reply : replies) out += String.format("Reply:%s\n", reply.toString(false));
        for (User user : userUpvotes) out += String.format("Upvote:%s\n", user.getUsername());
        return out;
    }

    public boolean hasComments() {
        return (replies.size() > 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return votes == comment.votes &&
                Double.compare(comment.grade, grade) == 0 &&
                Objects.equals(owner, comment.owner) &&
                Objects.equals(post, comment.post) &&
                Objects.equals(content, comment.content) &&
                Objects.equals(timestamp, comment.timestamp) &&
                Objects.equals(replies, comment.replies);
    }


}
