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
            }

            else if(input == 3){
                break;
            }

        }while (!login);
        
    }

}
