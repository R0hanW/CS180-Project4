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
        public void testMain() {
            
            String input = "1\nJDoe25\npassword123\n4\n";
            
            receiveInput(input);

            try {
                Main.main(new String[0]);
            } catch (Exception e) {
                e.printStackTrace();
                fail("Unexpected exception!");
            }
            String out = getOutput();

            String expectedFull = "Welcome to Discussion Posts\n"
            		+ "Type the number next to the option you would like to choose\n"
            		+ "[1]Log In\n"
            		+ "[2]Sign Up\n"
            		+ "[3]Exit\n"
            		+ "1\n"
            		+ "Enter the Username\n"
            		+ "JDoe25\n"
            		+ "Enter the Password\n"
            		+ "password123\n"
            		+ "Logged In Successfully!\n"
            		+ "Type the number next to the option you would like to choose\n"
            		+ "[1]Edit my Account\n"
            		+ "[2]Delete my Account\n"
            		+ "[3]Proceed to Courses\n"
            		+ "[4]Log out\n"
            		+ "4\n"
            		+ "";
            assertEquals("Ensure your PlayGame.java output contains the correct winning information!", expectedFull, out);
            
            String gameLog = "";
            try { 
                FileReader fr = new FileReader("GameLog.txt");
                BufferedReader br = new BufferedReader(fr);
                String line = br.readLine();
                while (line != null) {
                    gameLog += line;
                    gameLog += "\n";
                    line = br.readLine();
                }
                fr.close();
                br.close();
            } catch (Exception ex) {
                ex.printStackTrace(); 
                Assert.fail("Unexpected Exception!"); 
            }
            
            String expectedGameLog = "Battleship Game Log:\nWinning Player: Player 2\nHits: 0 - 17\n" +
                "Number of Turns To Win: 17\nPlayer 1 Board Pattern: Bottom Heavy\nPlayer 2 Board Pattern: Bottom Heavy\n"; 
            
            assertEquals("Ensure your GameLog.txt file output is correct", 
                         expectedGameLog, gameLog);

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

