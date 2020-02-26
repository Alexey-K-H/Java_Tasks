import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main{
    private final static Logger logger = Logger.getLogger(Main.class.getName());
    public static void main (String[] args) {
        LogManager logManager = LogManager.getLogManager();
        try {
            logManager.readConfiguration(new FileInputStream("log.properties"));
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Cannot get log configuration!");
        }

        Calculator calculator;
        String input_name;
        if (args.length > 1) {
            logger.log(Level.WARNING, "Error. To many args");
        } else if (args.length == 1) {
            logger.log(Level.INFO, "Open input file...\n");
            input_name = args[0];
            FileInputStream fileInputStream;
            try{
                fileInputStream = new FileInputStream(input_name);
                calculator = new Calculator(fileInputStream);
                calculator.ran_calc();
            } catch (FileNotFoundException e) {
                logger.log(Level.WARNING, "Cannot open file!");
            }
        }else {
                calculator = new Calculator(System.in);
                calculator.ran_calc();
        }
        logger.log(Level.FINE, "End of the application work.");
    }
}
