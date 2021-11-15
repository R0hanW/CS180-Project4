import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Post {
    private User owner;
    private String content;
    private String topic;
    private Course course;
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private ArrayList<Integer> pollResults = new ArrayList<Integer>();
    private ArrayList<String> pollOptions = new ArrayList<String>();
    private ArrayList<User> userPollVotes = new ArrayList<User>();
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
        comment.setPost(this);
    }

    public void addComment(User author, String content){
        Comment comment = new Comment(author, this, content);
        comments.add(comment);
        author.addComment(comment);

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

    public boolean removePost(User user){
        if(!user.isTeacher()) return false;
        course.removePost(this);
        return true;
    }

    public ArrayList<String> getPollOptions(){
        return pollOptions;
    }

    public void addPollOption(String pollOption){
        pollOptions.add(pollOption);
        pollResults.add(0);
    }

    public void addPollResult(int result){
        pollResults.add(result);
    }

    public void addUserVoter(User user){
        userPollVotes.add(user);
    }

    public void addPollVote(int option, User user){
        if(userPollVotes.contains(user)) {
            System.out.println("You have already voted on this poll! You cannot vote twice!");
            return;
        }
        pollResults.set(option, pollResults.get(option) + 1);
        System.out.println("Vote added!");
    }

    public String toString(){
        String out = String.format("Post:%s,%s,%s,%s\n", owner.getUsername(), content, topic, timestamp);
        for(String option: pollOptions) out += String.format("pollOption:%s\n", option);
        for(int result: pollResults) out += String.format("pollResult:%s\n", result);
        for(User user: userPollVotes) out += String.format("userPollVote:%s\n", user.getUsername());
        for(Comment comment: comments) out += String.format("Comment:%s\n", comment.toString());
        return out;
    }

    public ArrayList<Comment> getComments() {
    	return comments;
    }
    
    public void displayPost(){
        int counter = 0;
        System.out.println(topic + "    " + timestamp + "\n");
        System.out.println(content);
        if(pollOptions.size() > 0) this.displayPoll();
        for (Comment c : comments){
            System.out.printf("[%d]", counter++);
            c.displayComment(true);
        }
    }

    public void displayGradeDashboard(User user){
        for(Comment comment: comments) comment.displayComment(false, true);
    }

    public void displayCommentDashboard(boolean sortByVotes){
        ArrayList<Comment> sortedComments = comments;
        // if(sortedComments.size() <= 1) return sortedComments;
        if(sortByVotes) sortedComments.sort(Comparator.comparing(Comment::getVotes));
        else Collections.sort(sortedComments, (c1, c2) -> c1.getOwner().getName().compareTo(c2.getOwner().getName()));
        System.out.printf("Dashboard for %s\n", topic);
        sortedComments.stream()
            .forEach(comment -> 
            { 
                System.out.printf("(Post: %s)   ");
                comment.displayComment(false);
            });
    }

    public void displayPoll(){
        for(int i = 0; i < pollOptions.size(); i++){
            System.out.printf("[%d]%s: %d votes", i+1, pollOptions.get(i), pollResults.get(i));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(owner, post.owner) &&
                Objects.equals(content, post.content) &&
                Objects.equals(topic, post.topic) &&
                Objects.equals(course, post.course) &&
                Objects.equals(comments, post.comments) &&
                Objects.equals(timestamp, post.timestamp);
    }


}
