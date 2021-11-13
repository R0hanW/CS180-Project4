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
        ProgramManager program = new ProgramManager();


        System.out.println("Welcome to Discussion Posts");

        do {
            //Initial menu
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
                System.out.println("Enter the name you would like to keep for your username");
                String newUsername = scan.nextLine();
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
                        System.out.println("Enter the name you would like to keep for your username");
                        String newUsername = scan.nextLine();
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
                        back = true;
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
                                    for (int i = 0; i < courses.size(); i++) {
                                        if (courses.get(i).getOwner() == currentUser) {
                                            System.out.println("[" + i + 1 + "]" + courses.get(i).getName());
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
                                System.out.println("HERE");
                                for (int i = 0; i < courses.size(); i++) {
                                    if (courses.get(i).getOwner() == currentUser) {
                                        System.out.println("Here1");
                                        System.out.println("[" + i + 1 + "]" + courses.get(i).getName());
                                    }
                                }

                                //display courses and within there display discussion post
                            } else if (input3 == 2) {
                                back = true;
                            }

                        }
                    } else if (input2 == 4) {
                        break;
                    }
                }
            } while (back);
            break;
        } while (false);
    }

}
