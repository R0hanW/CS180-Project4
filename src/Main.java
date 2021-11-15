import java.util.*;

public class Main {

    public static void main(String[] args) {
        String explanation = "Type the number next to the option you would like to choose";
        String yesNo = "[1]Yes\n[2]No";
        int signUpResponse = 2;
        Boolean login = false;
        User currentUser = null;
        //all the menus go here (asking to login/modify/log out etc.)
        Scanner scan = new Scanner(System.in);
        ProgramManager program;
        try{
            program = new ProgramManager();
        } catch(Exception e) {
            e.printStackTrace();
            System.out.println("Error reading user and course files. Please restart the program!");
            return;
        }

        System.out.println("Welcome to Discussion Posts");

        do {
            //Initial menu
            try {
                System.out.println(explanation);
                System.out.println("[1]Log In");
                System.out.println("[2]Sign Up");
                System.out.println("[3]Exit");
                int input = scan.nextInt();
                scan.nextLine();

                //Log in page
                if (input == 1) {
                    //System.out.println(explanation);
                    System.out.println("Enter the Username");
                    String username = scan.nextLine();
                    System.out.println("Enter the Password");
                    String password;
                    boolean exit = false;
                    password = scan.nextLine();
                    User user = program.findUser(username);
                    if (user != null) {
                        while (!(user.getPassword().equals(password))) {
                            System.out.println("Wrong Password, Please Re-enter or type 'exit'");
                            password = scan.nextLine();
                            if (password.equals("exit")) {
                                exit = true;
                                break;
                            }
                        }
                        if (!exit) {
                            System.out.println("Logged In Successfully!");
                            currentUser = user;
                            login = true;
                            break;
                        }
                    } else {
                        System.out.println("Username doesn't exist, would you like to sign up?");
                        System.out.println(explanation);
                        System.out.println(yesNo);
                        signUpResponse = scan.nextInt();
                        scan.nextLine();
                    }
                }

                //sign up page
                if (signUpResponse == 1 || input == 2) {
                    System.out.println("What is your name");
                    String newName = scan.nextLine();
                    String newUsername = "";
                    do {
                        System.out.println("Enter the name you would like to keep for your username");
                        newUsername = scan.nextLine();
                        if (program.findUser(newUsername) != null){
                            System.out.println("That username is taken");
                        }
                    } while (program.findUser(newUsername) != null);
                    System.out.println("Enter what would you like to be for your password");
                    String newPassword = scan.nextLine();
                    System.out.println("Are you a teacher?");
                    System.out.println(explanation);
                    System.out.println(yesNo);
                    boolean isTeacher = false;
                    int ans = scan.nextInt();
                    scan.nextLine();
                    if (ans == 1) {
                        isTeacher = true;
                    } else if (ans == 2) {
                        isTeacher = false;
                    }
                    User newUser = new User(newName, newUsername, newPassword, isTeacher);
                    program.addUser(newUser);
                } else if (input == 3) {
                    break;
                } else {
                    System.out.println("Enter a valid option");
                }
            } catch (InputMismatchException e){
                System.out.println("Enter a valid number");
                scan.nextLine();
            }

        } while (!login);

        //At this point user is either logged in or exited application
        do {//for exiting after deleting account
            boolean back = false;
            ArrayList<Course> courses = program.getCourses();
            do {//for back purposes
                if (login) {
                    back = false;
                    //menu#2
                    while (true) {
                        try {
                            System.out.println(explanation);
                            System.out.println("[1]Edit my Account");
                            System.out.println("[2]Delete my Account");
                            System.out.println("[3]Proceed to Courses");
                            System.out.println("[4]Log out");
                            int input2 = scan.nextInt();
                            scan.nextLine();
                            if (input2 == 1) {
                                System.out.println("What is your name");
                                String newName = scan.nextLine();
                                //boolean taken = true;
                                String newUsername = "";
                                do {
                                    System.out.println("Enter the name you would like to keep for your username");
                                    newUsername = scan.nextLine();
                                    if (program.findUser(newUsername) != null){
                                        System.out.println("That username is taken");
                                    }
                                } while(program.findUser(newUsername) != null);
                                System.out.println("Enter what would you like to be for your password");
                                String newPassword = scan.nextLine();
                                System.out.println("Are you a teacher?");
                                System.out.println(explanation);
                                System.out.println(yesNo);
                                boolean isTeacher = false;
                                int ans = scan.nextInt();
                                scan.nextLine();
                                if (ans == 1) {
                                    isTeacher = true;
                                } else if (ans == 2) {
                                    isTeacher = false;
                                }
                                program.modifyUser(currentUser, newName, newUsername, newPassword, isTeacher);
                                System.out.println("Successfully modified!");
                                //back = true;
                            } else if (input2 == 2) {
                                program.removeUser(currentUser);
                                System.out.println("Successfully deleted, farewell!");
                                break;
                            } else if (input2 == 3) {
                                //Teacher menu for create courses
                                if (currentUser.isTeacher()) {
                                    boolean courseAdded = false;
                                    do {
                                        courseAdded = false;
                                        System.out.println(explanation);
                                        System.out.println("[1]Create courses");
                                        System.out.println(("[2]Access Existing courses"));
                                        System.out.println("[3]back");
                                        int input = scan.nextInt();
                                        scan.nextLine();
                                        if (input == 1) {
                                            //course creation here
                                            System.out.println("Enter the course name");
                                            String courseName = scan.nextLine();
                                            Course currentCourse = new Course(courseName, currentUser);
                                            program.addCourse(currentCourse);
                                            System.out.println("Course added!");
                                            //program.writeFile();
                                            courseAdded = true;
                                        } else if (input == 2) {
                                            //display courses etc
                                            System.out.println(explanation);
                                            for (int i = 0; i < courses.size(); i++) {
                                                //System.out.printf("[%d]%s\n", i + 1, courses.get(i).getName());
                                                System.out.println("[" + (i + 1) + "] " + courses.get(i).getName());
                                            }

                                            int courseNum = scan.nextInt() - 1;
                                            scan.nextLine();
                                            if (courseNum < courses.size()) {
                                                Course currentCourse = courses.get(courseNum);
                                                System.out.println("Existing posts are below: ");
                                                currentCourse.displayCourse();//displays posts list
                                                ArrayList<Post> posts = currentCourse.getPosts();
                                                System.out.println(explanation);
                                                //System.out.println("[1]Display post");
                                                System.out.println("[1]Create post");
                                                System.out.println("[2]Edit post");
                                                System.out.println("[3]Delete post");
                                                System.out.println("[4]View Post");
                                                int input3 = scan.nextInt();
                                                scan.nextLine();
                                                if (input3 == 1) {
                                                    System.out.println("Enter the post topic");
                                                    String postTopic = scan.nextLine();
                                                    System.out.println(("Enter the post description"));
                                                    String postDisc = scan.nextLine();
                                                    currentCourse.addPost(new Post(currentUser, currentCourse, postDisc, postTopic));
                                                    System.out.println("Post added successfully");
                                                } else if (input3 == 2) {
                                                    System.out.println("Enter the Number next to the post you want to edit");
                                                    int postNum = scan.nextInt() - 1;
                                                    scan.nextLine();
                                                    System.out.println("Enter the new post topic");
                                                    String postTopic = scan.nextLine();
                                                    System.out.println(("Enter the new post description"));
                                                    String postDisc = scan.nextLine();
                                                    if ((postNum) < posts.size()) {
                                                        Post currentPost = posts.get(postNum);
                                                        currentCourse.modifyPost(currentPost, postDisc, postTopic, currentUser);
                                                        System.out.println("Post Edited successfully");
                                                    }
                                                } else if (input3 == 3) {
                                                    System.out.println("Enter the Number next to the post you want to delete ");
                                                    int postNum = scan.nextInt() - 1;
                                                    scan.nextLine();
                                                    if ((postNum) < posts.size()) {
                                                        Post currentPost = posts.get(postNum);
                                                        currentPost.removePost();
                                                        System.out.println("Post deleted successfully");
                                                    }
                                                } else if (input3 == 4) {
                                                    boolean back1 = false;
                                                    do {
                                                        back1 = false;
                                                        System.out.println("Enter the number next to the post you want to view");
                                                        int postNum = scan.nextInt() - 1;
                                                        scan.nextLine();
                                                        if (postNum < posts.size()) {
                                                            Post currentPost = posts.get(postNum);
                                                            currentPost.displayPost();
                                                            System.out.println(explanation);
                                                            System.out.println("[1]Reply to this post");
                                                            System.out.println("[2]Reply to student responses");
                                                            System.out.println("[3]Back");
                                                            int input5 = scan.nextInt();
                                                            scan.nextLine();
                                                            if (input5 == 1) {
                                                                System.out.println("Enter your text below");
                                                                String input6 = scan.nextLine();
                                                                currentPost.addComment(new Comment(currentUser, currentPost, input6));
                                                                System.out.println("Comment added");
                                                            } else if (input5 == 2) {
                                                                ArrayList<Comment> comments = currentPost.getComments();
                                                                System.out.println("Enter the number next to the response" +
                                                                        " you want to reply");
                                                                int replyNum = scan.nextInt() - 1;
                                                                scan.nextLine();
                                                                if (replyNum < comments.size()) {
                                                                    System.out.println("Enter your text below");
                                                                    String text = scan.nextLine();
                                                                    Comment currentComment = comments.get(replyNum);

                                                                    currentComment.addReply(new Comment(currentUser, currentPost, text));
                                                                }

                                                            } else if (input5 == 3) {
                                                                back1 = true;
                                                            }
                                                        }
                                                    } while (back1);
                                                }
                                            }
                                        } else if (input == 3) {
                                            back = true;
                                        }
                                    } while (courseAdded);

                                }
                                //Student menu for courses
                                else {
                                    System.out.println(explanation);
                                    System.out.println(("[1]Access Existing courses"));
                                    System.out.println("[2]back");
                                    int input3 = scan.nextInt();
                                    scan.nextLine();
                                    System.out.println(input3);
                                    if (input3 == 1) {
                                        //program.readFile();
                                        //System.out.println("HERE");
                                        //display courses etc
                                        System.out.println(explanation);
                                        for (int i = 0; i < courses.size(); i++) {
                                            //System.out.printf("[%d]%s\n", i + 1, courses.get(i).getName());
                                            System.out.println("[" + (i + 1) + "] " + courses.get(i).getName());
                                        }

                                        int courseNum = scan.nextInt() - 1;
                                        scan.nextLine();
                                        if (courseNum < courses.size()) {
                                            Course currentCourse = courses.get(courseNum);
                                            System.out.println("Existing posts are below: ");
                                            currentCourse.displayCourse();//displays posts list
                                            ArrayList<Post> posts = currentCourse.getPosts();
                                            System.out.println(explanation);
                                            //System.out.println("[1]Display post");
                                            System.out.println("[1]View post");
                                            int input9 = scan.nextInt();
                                            scan.nextLine();
                                            if (input9 == 1) {
                                                System.out.println("Enter the post topic");
                                                String postTopic = scan.nextLine();
                                                System.out.println(("Enter the post description"));
                                                String postDisc = scan.nextLine();
                                                currentCourse.addPost(new Post(currentUser, currentCourse, postDisc, postTopic));
                                                System.out.println("Post added successfully");
                                            } else if (input9 == 1) {
                                                boolean back1 = false;
                                                do {
                                                    back1 = false;
                                                    System.out.println("Enter the number next to the post you want to view");
                                                    int postNum = scan.nextInt() - 1;
                                                    scan.nextLine();
                                                    if (postNum < posts.size()) {
                                                        Post currentPost = posts.get(postNum);
                                                        currentPost.displayPost();
                                                        System.out.println(explanation);
                                                        System.out.println("[1]Reply to this post");
                                                        System.out.println("[2]Reply to other student responses");
                                                        System.out.println("[3]Back");
                                                        int input5 = scan.nextInt();
                                                        scan.nextLine();
                                                        if (input5 == 1) {
                                                            System.out.println("Enter your text below");
                                                            String input6 = scan.nextLine();
                                                            currentPost.addComment(new Comment(currentUser, currentPost, input6));
                                                            System.out.println("Comment added");
                                                        } else if (input5 == 2) {
                                                            ArrayList<Comment> comments = currentPost.getComments();
                                                            System.out.println("Enter the number next to the response" +
                                                                    " you want to reply");
                                                            int replyNum = scan.nextInt() - 1;
                                                            scan.nextLine();
                                                            if (replyNum < comments.size()) {
                                                                System.out.println("Enter your text below");
                                                                String text = scan.nextLine();
                                                                Comment currentComment = comments.get(replyNum);

                                                                currentComment.addReply(new Comment(currentUser, currentPost, text));
                                                            }

                                                        } else if (input5 == 3) {
                                                            back1 = true;
                                                        }
                                                    }
                                                } while (back1);
                                            }
                                        }


                                        //display courses and within there display discussion post
                                    } else if (input3 == 2) {
                                        back = true;
                                    }

                                }
                            } else if (input2 == 4) {
                                System.out.println("Logged out Successfully!");
                                break;
                            } else {
                                System.out.println("Enter a valid option");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Enter a valid number");
                            scan.nextLine();
                        }
                    }

                }
            } while (back);
            break;
        } while (false);
        try {
            program.writeFile();
        } catch (Exception e) {
            System.out.println("Error writing to files. Data may not be saved! Please try again.");
            e.printStackTrace();
            return;
        }
    }
}

