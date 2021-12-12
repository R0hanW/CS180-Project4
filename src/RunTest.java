// import static org.junit.Assert.*;

// import java.io.ByteArrayInputStream;
// import java.io.ByteArrayOutputStream;
// import java.io.FileOutputStream;
// import java.io.InputStream;
// import java.io.PrintStream;
// import java.io.PrintWriter;

// import org.junit.*;
// import org.junit.After;
// import org.junit.Before;
// import org.junit.runner.JUnitCore;
// import org.junit.runner.notification.Failure;

// /**
//  * RunTest.java
//  * <p>
//  * Test cases for the program
//  *
//  * @author Thanmaya Pattanashetty Purdue CS180
//  * @version 11/15/21
//  */
// //@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// public class RunTest {
//     public static void main(String[] args) {
//         Result result = JUnitCore.runClasses(TestCase.class);
//         if (result.wasSuccessful()) {
//             System.out.println("Excellent - Test ran successfully");
//         } else {
//             for (Failure failure : result.getFailures()) {
//                 System.out.println(failure.toString());
//             }
//         }
//     }

//     /**
//      * A set of public test cases.
//      *
//      * <p>Purdue University -- CS18000 -- Fall 2021</p>
//      *
//      * @author Purdue CS
//      * @version August 23, 2021
//      */
//     public static class TestCase {
//         private final InputStream originalInput = System.in;
//         private final PrintStream originalOutput = System.out;
//         @SuppressWarnings("FieldCanBeLocal")
//         private ByteArrayInputStream testIn;
//         private ByteArrayOutputStream testOut;

//         @Before
//         public void outputStart() {
//             testOut = new ByteArrayOutputStream();
//             System.setOut(new PrintStream(testOut));
//         }

//         @Test(timeout = 10000)
//         public void testLogin() {
//             try {
//                 String input =
//                         "1" + System.lineSeparator() +
//                                 "JDoe25" + System.lineSeparator() +
//                                 "password123" + System.lineSeparator() +
//                                 "2" + System.lineSeparator();
//                 receiveInput(input);
//                 LoginFrame.main(new String[0]);
//                 String out = getOutput();
//                 String expectedFull =
//                         "Welcome to Discussion Posts" + System.lineSeparator() +
//                                 "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                                 "[1]Log In" + System.lineSeparator() +
//                                 "[2]Sign Up" + System.lineSeparator() +
//                                 "[3]Exit" + System.lineSeparator() +
//                                 "Enter the Username" + System.lineSeparator() +
//                                 "Enter the Password" + System.lineSeparator() +
//                                 "Username doesn't exist, would you like to sign up?" + System.lineSeparator() +
//                                 "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                                 "[1]Yes" + System.lineSeparator() +
//                                 "[2]No" + System.lineSeparator() +
//                                 "Have a great day!";
//                 expectedFull = expectedFull.replaceAll("\r\n", "\n");
//                 out = out.replaceAll("\r\n", "\n");
//                 assertEquals("Ensure your Main matches output!", expectedFull.trim(), out.trim());


//             } catch (Exception e) {
//                 fail("Unexpected exception!");
//             }
//         }

//         @Test(timeout = 10000)
//         public void testExit() {
//             try {
//                 String input = "3" + System.lineSeparator();

//                 receiveInput(input);
//                 LoginFrame.main(new String[0]);
//                 String out = getOutput();
//                 String expectedFull = "Welcome to Discussion Posts" + System.lineSeparator() +
//                         "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                         "[1]Log In" + System.lineSeparator() +
//                         "[2]Sign Up" + System.lineSeparator() +
//                         "[3]Exit" + System.lineSeparator();
//                 expectedFull = expectedFull.replaceAll("\r\n", "\n");
//                 out = out.replaceAll("\r\n", "\n");
//                 assertEquals("Ensure your Main matches output!", expectedFull.trim(), out.trim());

//             } catch (Exception e) {
//                 fail("Unexpected exception!");
//             }
//         }

//         @Test(timeout = 10000)
//         public void testSignUp() {
//             try {
//                 FileOutputStream fos = new FileOutputStream("Users.txt", false);
//                 PrintWriter pw = new PrintWriter(fos);
//                 pw.println("");

//                 String input = "2" + System.lineSeparator() +
//                         "Pete" + System.lineSeparator() +
//                         "purduePete" + System.lineSeparator() +
//                         "pass123" + System.lineSeparator() +
//                         "2" + System.lineSeparator() +
//                         "3" + System.lineSeparator();

//                 receiveInput(input);
//                 LoginFrame.main(new String[0]);
//                 String out = getOutput();
//                 String expectedFull = "Welcome to Discussion Posts" + System.lineSeparator() +
//                         "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                         "[1]Log In" + System.lineSeparator() +
//                         "[2]Sign Up" + System.lineSeparator() +
//                         "[3]Exit" + System.lineSeparator() +
//                         "What is your name" + System.lineSeparator() +
//                         "Enter the name you would like to keep for your username" + System.lineSeparator() +
//                         "Enter what would you like to be for your password" + System.lineSeparator() +
//                         "Are you a teacher?" + System.lineSeparator() +
//                         "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                         "[1]Yes" + System.lineSeparator() +
//                         "[2]No" + System.lineSeparator() +
//                         "Successfully Signed Up!" + System.lineSeparator() +
//                         "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                         "[1]Log In" + System.lineSeparator() +
//                         "[2]Sign Up" + System.lineSeparator() +
//                         "[3]Exit" + System.lineSeparator();
//                 expectedFull = expectedFull.replaceAll("\r\n", "\n");
//                 out = out.replaceAll("\r\n", "\n");
//                 assertEquals("Ensure your Main matches output!", expectedFull.trim(), out.trim());

//             } catch (Exception e) {
//                 fail("Unexpected exception!");
//             }
//         }

//         @Test(timeout = 10000)
//         public void testSignBackIn() {
//             try {
//                 /*
//                 FileOutputStream fos = new FileOutputStream("src/Users.txt", false);
//                 PrintWriter pw = new PrintWriter(fos);
//                 pw.println("User{name=\"Pete\",username=\"purduePete\",password=\"pass123\",isTeacher=\"false\"}\n");
//                 */
//                 String input = "1" + System.lineSeparator() +
//                         "purduePete" + System.lineSeparator() +
//                         "pass123" + System.lineSeparator() +
//                         "4" + System.lineSeparator();

//                 receiveInput(input);
//                 LoginFrame.main(new String[0]);
//                 String out = getOutput();
//                 String expectedFull = "Welcome to Discussion Posts" + System.lineSeparator() +
//                         "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                         "[1]Log In" + System.lineSeparator() +
//                         "[2]Sign Up" + System.lineSeparator() +
//                         "[3]Exit" + System.lineSeparator() +
//                         "Enter the Username" + System.lineSeparator() +
//                         "Enter the Password" + System.lineSeparator() +
//                         "Welcome, Pete!" + System.lineSeparator() +
//                         "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                         "[1]Edit my Account" + System.lineSeparator() +
//                         "[2]Delete my Account" + System.lineSeparator() +
//                         "[3]Proceed to Courses" + System.lineSeparator() +
//                         "[4]Log out" + System.lineSeparator() +
//                         "Logged out Successfully!" + System.lineSeparator();
//                 expectedFull = expectedFull.replaceAll("\r\n", "\n");
//                 out = out.replaceAll("\r\n", "\n");
//                 assertEquals("Ensure your Main matches output!", expectedFull.trim(), out.trim());

//             } catch (Exception e) {
//                 fail("Unexpected exception!");
//             }
//         }

//         @Test(timeout = 10000)
//         public void testSignBackInWrongPassword() {
//             try {
//                 String input = "1" + System.lineSeparator() +
//                         "purduePete" + System.lineSeparator() +
//                         "pass" + System.lineSeparator() +
//                         "pass123" + System.lineSeparator() +
//                         "4" + System.lineSeparator();

//                 receiveInput(input);
//                 LoginFrame.main(new String[0]);
//                 String out = getOutput();
//                 String expectedFull = "Welcome to Discussion Posts" + System.lineSeparator() +
//                         "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                         "[1]Log In" + System.lineSeparator() +
//                         "[2]Sign Up" + System.lineSeparator() +
//                         "[3]Exit" + System.lineSeparator() +
//                         "Enter the Username" + System.lineSeparator() +
//                         "Enter the Password" + System.lineSeparator() +
//                         "Wrong Password, Please Re-enter or type 'exit'" + System.lineSeparator() +
//                         "Welcome, Pete!" + System.lineSeparator() +
//                         "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                         "[1]Edit my Account" + System.lineSeparator() +
//                         "[2]Delete my Account" + System.lineSeparator() +
//                         "[3]Proceed to Courses" + System.lineSeparator() +
//                         "[4]Log out" + System.lineSeparator() +
//                         "Logged out Successfully!" + System.lineSeparator();
//                 expectedFull = expectedFull.replaceAll("\r\n", "\n");
//                 out = out.replaceAll("\r\n", "\n");
//                 assertEquals("Ensure your Main matches output!", expectedFull.trim(), out.trim());

//             } catch (Exception e) {
//                 fail("Unexpected exception!");
//             }
//         }

//         @Test(timeout = 10000)
//         public void testSignUpSameUsername() {
//             try {
//                 String input = "2" + System.lineSeparator() +
//                         "Bob" + System.lineSeparator() +
//                         "purduePete" + System.lineSeparator() +
//                         "purdueBob" + System.lineSeparator() +
//                         "belltower123" + System.lineSeparator() +
//                         "1" + System.lineSeparator() +
//                         "3" + System.lineSeparator();

//                 receiveInput(input);
//                 LoginFrame.main(new String[0]);
//                 String out = getOutput();
//                 String expectedFull = "Welcome to Discussion Posts" + System.lineSeparator() +
//                         "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                         "[1]Log In" + System.lineSeparator() +
//                         "[2]Sign Up" + System.lineSeparator() +
//                         "[3]Exit" + System.lineSeparator() +
//                         "What is your name" + System.lineSeparator() +
//                         "Enter the name you would like to keep for your username" + System.lineSeparator() +
//                         "That username is taken" + System.lineSeparator() +
//                         "Enter the name you would like to keep for your username" + System.lineSeparator() +
//                         "Enter what would you like to be for your password" + System.lineSeparator() +
//                         "Are you a teacher?" + System.lineSeparator() +
//                         "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                         "[1]Yes" + System.lineSeparator() +
//                         "[2]No" + System.lineSeparator() +
//                         "Successfully Signed Up!" + System.lineSeparator() +
//                         "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                         "[1]Log In" + System.lineSeparator() +
//                         "[2]Sign Up" + System.lineSeparator() +
//                         "[3]Exit" + System.lineSeparator();
//                 expectedFull = expectedFull.replaceAll("\r\n", "\n");
//                 out = out.replaceAll("\r\n", "\n");
//                 assertEquals("Ensure your Main matches output!", expectedFull.trim(), out.trim());

//             } catch (Exception e) {
//                 fail("Unexpected exception!");
//             }
//         }

//         @Test(timeout = 10000)
//         public void testIncorrectInput() {
//             try {
//                 String input = "4" + System.lineSeparator() +
//                         "nonnumber" + System.lineSeparator() +
//                         "3" + System.lineSeparator();

//                 receiveInput(input);
//                 LoginFrame.main(new String[0]);
//                 String out = getOutput();
//                 String expectedFull = "Welcome to Discussion Posts" + System.lineSeparator() +
//                         "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                         "[1]Log In" + System.lineSeparator() +
//                         "[2]Sign Up" + System.lineSeparator() +
//                         "[3]Exit" + System.lineSeparator() +
//                         "Enter a valid option" + System.lineSeparator() +
//                         "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                         "[1]Log In" + System.lineSeparator() +
//                         "[2]Sign Up" + System.lineSeparator() +
//                         "[3]Exit" + System.lineSeparator() +
//                         "Enter a valid number" + System.lineSeparator() +
//                         "Type the number next to the option you would like to choose" + System.lineSeparator() +
//                         "[1]Log In" + System.lineSeparator() +
//                         "[2]Sign Up" + System.lineSeparator() +
//                         "[3]Exit" + System.lineSeparator();
//                 expectedFull = expectedFull.replaceAll("\r\n", "\n");
//                 out = out.replaceAll("\r\n", "\n");
//                 assertEquals("Ensure your Main matches output!", expectedFull.trim(), out.trim());

//             } catch (Exception e) {
//                 fail("Unexpected exception!");
//             }
//         }

//         /**
//          * UTILITY METHODS BELOW
//          */

//         private void receiveInput(String str) {
//             testIn = new ByteArrayInputStream(str.getBytes());
//             System.setIn(testIn);
//         }

//         private String getOutput() {
//             return testOut.toString();
//         }

//         @After
//         public void restoreInputAndOutput() {
//             System.setIn(originalInput);
//             System.setOut(originalOutput);
//         }
//     }
// }
