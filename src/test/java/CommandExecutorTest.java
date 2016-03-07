import helpers.CommandExecutor;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class CommandExecutorTest {

    /**
     * Test if external programs can be executed.
     */
    @Test
    public void executeCommandsTest() {
        CommandExecutor ce = new CommandExecutor();
        String command = "echo This is it!";
        String results = ce.execute(command);
        assertTrue(results.equals("This is it!"));
    }
}
