package Factory.Main;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main{
    private final static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args){
        LogManager logManager = LogManager.getLogManager();
        try{
            logManager.readConfiguration(new FileInputStream("log.properties"));
        }catch (IOException e){
            logger.log(Level.SEVERE, "Cannot get log configuration!");
        }

        Factory factory = Factory.getInstance();
        ConfigParser parser;

        logger.log(Level.INFO, "Get production plan....");
        try {
            parser = new ConfigParser("production.properties");
            factory.getWorkPlan(parser);
        }catch (IOException e){
            logger.log(Level.SEVERE, "Cannot get production information!");
        }
        logger.log(Level.INFO, "Successful!");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        logger.log(Level.INFO, "Start of the work:");
        factory.startWork();

        try{
            reader.readLine();
            factory.finishWork();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        logger.log(Level.INFO, "Finish of the work!");
    }
}
