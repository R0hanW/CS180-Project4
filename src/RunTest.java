import org.junit.Test;
import org.junit.After;

import java.lang.reflect.Field;

import org.junit.Assert;
import org.junit.Before;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.*;

import static org.hamcrest.CoreMatchers.containsString;

import static org.junit.Assert.*;

/**
 * A framework to run public test cases.
 *
 * <p>Purdue University -- CS18000 -- Fall 2021</p>
 *
 * @author Purdue CS
 * @version August 23, 2021
 */
public class RunTest {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestCase.class);
        if (result.wasSuccessful()) {
            System.out.println("Excellent - Test ran successfully");
        } else {
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
    }

    /**
     * A set of public test cases.
     *
     * <p>Purdue University -- CS18000 -- Fall 2021</p>
     *
     * @author Purdue CS
     * @version August 23, 2021
     */
    public static class TestCase {
        private final InputStream originalInput = System.in;
        private final PrintStream originalOutput = System.out;
        @SuppressWarnings("FieldCanBeLocal")
        private ByteArrayInputStream testIn;
        private ByteArrayOutputStream testOut;

        @Before
        public void outputStart() {
            testOut = new ByteArrayOutputStream();
            System.setOut(new PrintStream(testOut));
        }

        @Test(timeout = 1000)
        public void testLogin() {


            try {
                String input = "1" + System.lineSeparator() + "JDoe25" + System.lineSeparator() + "password123" + System.lineSeparator() + "4" + System.lineSeparator();
                receiveInput(input);
                Main.main(new String[0]);
                String out = getOutput();
                String expectedFull = "Welcome to Discussion Posts" +
                        System.lineSeparator() +
                        "Type the number next to the option you would like to choose" + System.lineSeparator() +
                        "[1]Log In" + System.lineSeparator() +
                        "[2]Sign Up" + System.lineSeparator() +
                        "[3]Exit" + System.lineSeparator() + "Enter the Username" + System.lineSeparator() +
                        "Enter the Password" +
                        System.lineSeparator() +
                        "Welcome, John Doe!" + System.lineSeparator() +

                        "Type the number next to the option you would like to choose" + System.lineSeparator() +
                        "[1]Edit my Account" + System.lineSeparator() +
                        "[2]Delete my Account" + System.lineSeparator() +
                        "[3]Proceed to Courses" + System.lineSeparator() +
                        "[4]Log out"
                        + System.lineSeparator() +

                        "Logged out Successfully!";
                expectedFull = expectedFull.replaceAll("\r\n", "\n");
                out = out.replaceAll("\r\n", "\n");
                assertEquals("Ensure your Main matches output!", expectedFull.trim(), out.trim());


            } catch (Exception e) {
                e.printStackTrace();
                fail("Unexpected exception!");
            }
        }
        /*
        @Test(timeout = 1000)
        public void testIncorrectLogin() {
            try {
                String input = "1" + System.lineSeparator() + "PPurdue123" + System.lineSeparator() +
                        "pass123" + System.lineSeparator() + "1" + System.lineSeparator()+"Purdue Pete"
                        + System.lineSeparator() + "PPurdue123" + System.lineSeparator() +"pass123"
                        + System.lineSeparator() + "1"+ System.lineSeparator() + "3";
                receiveInput(input);
                Main.main(new String[0]);
                String out = getOutput();
                String expectedFull = "Welcome to Discussion Posts" +
                        System.lineSeparator() + System.lineSeparator() +
                        "Type the number next to the option you would like to choose" + System.lineSeparator() +
                        "[1]Log In" + System.lineSeparator() +
                        "[2]Sign Up" + System.lineSeparator() +
                        "[3]Exit" + System.lineSeparator() + "Enter the Username" + System.lineSeparator() +
                        "Enter the Password" +
                        System.lineSeparator() +
                        "Username doesn't exist, would you like to sign up?" + System.lineSeparator()+ System.lineSeparator() +
                        "Type the number next to the option you would like to choose" + System.lineSeparator() +
                        "[1]Yes" + System.lineSeparator() +
                        "[2]No" + System.lineSeparator() +
                        "What is your name" + System.lineSeparator()+
                        System.lineSeparator() +"Enter the name you would like to keep for your username" +
                        System.lineSeparator() + "Enter what would you like to be for your password" + System.lineSeparator() +
                        "Are you a teacher?" + System.lineSeparator() +System.lineSeparator() +
                        "Type the number next to the option you would like to choose" + System.lineSeparator() +
                        "[1]Yes" + System.lineSeparator() +
                        "[2]No" + System.lineSeparator() +
                        "Successfully Signed Up!" + System.lineSeparator() + System.lineSeparator() +
                        "Type the number next to the option you would like to choose" + System.lineSeparator() +
                        "[1]Log In" + System.lineSeparator() +
                        "[2]Sign Up" + System.lineSeparator() +
                        "[3]Exit" + System.lineSeparator() ;
                expectedFull = expectedFull.replaceAll("\r\n", "\n");
                out = out.replaceAll("\r\n", "\n");
                assertEquals("Ensure your Main matches output!", expectedFull.trim(), out.trim());


            } catch (Exception e) {
              //  e.printStackTrace();
                fail("Unexpected exception!");
            }
        }*/

        /**
         * UTILITY METHODS BELOW
         */

        private void receiveInput(String str) {
            testIn = new ByteArrayInputStream(str.getBytes());
            System.setIn(testIn);
        }

        private String getOutput() {
            return testOut.toString();
        }

        @After
        public void restoreInputAndOutput() {
            System.setIn(originalInput);
            System.setOut(originalOutput);
        }
    }
}

