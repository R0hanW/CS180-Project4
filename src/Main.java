import java.util.*;
public class Main {

    public static void main(String[] args) {
        String explanation = "Type the number next to the option you would like to choose";
        //all the menus go here (asking to login/modify/log out etc.)
        Scanner scan = new Scanner(System.in);
        ProgramManager program = new ProgramManager();
        System.out.println("Welcome to Discussion Posts");

        //Initial menu
        System.out.println(explanation);
        System.out.println("[1]Log In");
        System.out.println("[2]Sign Up");
        int input = scan.nextInt();
        scan.nextLine();
        if(input == 1){
            //System.out.println(explanation);
            System.out.print("Enter the Username: ");
            String username = scan.nextLine();
            System.out.print("\nEnter the Password: ");
            String password = scan.nextLine();
            User user = program.findUser(username);
            if(user.getPassword().equals(password)){

            }
        }




        //System.out.println("testing commit");


    }
}
