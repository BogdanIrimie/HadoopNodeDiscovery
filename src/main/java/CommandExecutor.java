import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Executes commands in terminal
 */
public class CommandExecutor {
    Logger logger = LoggerFactory.getLogger(CommandExecutor.class);

    /**
     * Start a new process with the provided command
     *
     * @return command output
     */
    public String execute(String command) {
        String line;
        Process process;
        StringBuilder commandOutput = new StringBuilder();

        try {
            process = Runtime.getRuntime().exec(command);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            while ((line = br.readLine()) != null) {
                commandOutput.append(line);
            }
            process.waitFor();
            process.exitValue();  // throw some error if exit value is incorrect
            process.destroy();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }

        return commandOutput.toString();
    }

}
