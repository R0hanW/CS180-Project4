import java.util.ArrayList;

public class Comment {
    private User owner;
    private Post post;
    private String content;
    private String timestamp;
    private ArrayList<Comment> replies = new ArrayList<Comment>();
    
}
