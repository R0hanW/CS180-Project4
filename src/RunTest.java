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
        public void testRun1() {
            // Set the input        
            // Separate each input with a newline (\n). 
            String input = "1\nJDoe25\npassword123\n4\n"; 

            // Pair the input with the expected result
            String expected = "Welcome to Discussion Posts\n"
            		+ "Type the number next to the option you would like to choose\n"
            		+ "[1]Log In\n"
            		+ "[2]Sign Up\n"
            		+ "[3]Exit\n"
            		+ "Enter the Username\n"
            		+ "Enter the Password\n"
            		+ "Logged In Successfully!\n"
            		+ "Type the number next to the option you would like to choose\n"
            		+ "[1]Edit my Account\n"
            		+ "[2]Delete my Account\n"
            		+ "[3]Proceed to Courses\n"
            		+ "[4]Log out\n";

            // Runs the program with the input values
            // Replace TestProgram with the name of the class with the main method
            receiveInput(input);
            Main.main(new String[0]);

            // Retrieves the output from the program
            String stuOut = getOutput();

            // Trims the output and verifies it is correct. 
            stuOut = stuOut.replace("\r\n", "\n");
            assertEquals("Error message if output is incorrect, customize as needed",
                            expected.trim(), stuOut.trim());

        }
        
        
        @Test(timeout = 1000)
        public void testRun2() {
            // Set the input        
            // Separate each input with a newline (\n). 
            String input = "2\nAidan Chen\nchen4002\npassword123\n1\n"
            		+ "1\nchen4002\npassword123\n4\n"; 

            // Pair the input with the expected result
            String expected = "Welcome to Discussion Posts\n"
            		+ "Type the number next to the option you would like to choose\n"
            		+ "[1]Log In\n"
            		+ "[2]Sign Up\n"
            		+ "[3]Exit\n"
            		+ "What is your name\n"
            		+ "Enter the name you would like to keep for your username\n"
            		+ "Enter what would you like to be for your password\n"
            		+ "Are you a teacher?\n"
            		+ "Type the number next to the option you would like to choose\n"
            		+ "[1]Yes\n"
            		+ "[2]No\n"
            		+ "Type the number next to the option you would like to choose\n"
            		+ "[1]Log In\n"
            		+ "[2]Sign Up\n"
            		+ "[3]Exit\n"
            		+ "Enter the Username\n"
            		+ "Enter the Password\n"
            		+ "Logged In Successfully!\n"
            		+ "Type the number next to the option you would like to choose\n"
            		+ "[1]Edit my Account\n"
            		+ "[2]Delete my Account\n"
            		+ "[3]Proceed to Courses\n"
            		+ "[4]Log out\n";

            // Runs the program with the input values
            // Replace TestProgram with the name of the class with the main method
            receiveInput(input);
            Main.main(new String[0]);

            // Retrieves the output from the program
            String stuOut = getOutput();

            // Trims the output and verifies it is correct. 
            stuOut = stuOut.replace("\r\n", "\n");
            assertEquals("Error message if output is incorrect, customize as needed",
                            expected.trim(), stuOut.trim());

        }
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

