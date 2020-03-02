import Commands.Command;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Factory {
    private volatile static Factory factory = null;
    public final Properties p;
    private final static Logger logger = Logger.getLogger(Factory.class.getName());

    private Factory(){
        try{
            InputStream resource = Factory.class.getClassLoader().getResourceAsStream("Config.properties");
            p = new Properties();

            if(resource != null){
                p.load(resource);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Cannot get configuration of commands");
            throw new RuntimeException();
        }
    }

    public static Factory getInstance(){
        if(factory == null){
            synchronized (Factory.class) {
                if(factory == null) {
                    factory = new Factory();
                }
            }
        }
        return factory;
    }

    public Command make_command(String[] arguments){
        Command command = null;
        String className;

        try{
            if(arguments[0].equals("#")){
                className = p.getProperty("COMMENT");
            }
            else
                className = p.getProperty(arguments[0]);

            if(className == null){
                logger.log(Level.WARNING, "Unexpected command: (" + arguments[0] + ") What do you mean?");
            }
            else
            {
                Class<?> factoryClass = Class.forName(className);
                if(factoryClass.getDeclaredConstructor().newInstance() instanceof Command){
                    command = (Command) factoryClass.getDeclaredConstructor().newInstance();
                }
                else{
                    logger.log(Level.WARNING, "Cannot create command!");
                }
            }
        } catch (InstantiationException e) {
            logger.log(Level.SEVERE, "Fail instance!");
        } catch (InvocationTargetException e) {
            logger.log(Level.SEVERE, "Fail to get constructor!");
        } catch (NoSuchMethodException e) {
            logger.log(Level.SEVERE, "No such method!");
        } catch (IllegalAccessException e) {
            logger.log(Level.SEVERE, "Illegal access!");
        } catch (ClassNotFoundException e) {
            logger.log(Level.SEVERE, "Class not found!");
        }
        return command;
    }

}
