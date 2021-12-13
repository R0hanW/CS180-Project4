/**
 * User.java
 * Represents a user in the discussion forum. Keeps track of a name, username, password, and user type. The user
 * type can be either a student or a teacher, and determines the user's permissions within the program.
 *
 * @author Allen Chang Purdue CS180
 * @version 11/15/21
 */

package backend;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class User implements Serializable {
    private String name;
    private String username;
    private String password;
    private ArrayList<Comment> comments = new ArrayList<Comment>();
    private boolean isTeacher;

    public User() {
        name = "";
        username = "";
        password = "";
    }

    public User(String name, String username, String password, boolean isTeacher) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.isTeacher = isTeacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isTeacher() {
        return this.isTeacher;
    }

    public void setIsTeacher(boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void removeComments() {
        comments.stream().forEach(comment -> comment.removeComment());
    }

    public String toString() {
        return String.format("User{name=\"%s\",username=\"%s\",password=\"%s\",isTeacher=\"%b\"}",
                name, username, password, isTeacher);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isTeacher == user.isTeacher &&
                Objects.equals(name, user.name) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(comments, user.comments);
    }

}
