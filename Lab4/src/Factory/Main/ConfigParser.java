package Factory.Main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConfigParser {
    private final static Logger logger = Logger.getLogger(ConfigParser.class.getName());

    private int bodyStorageSize;
    private int motorStorageSize;
    private int accessoryStorageSize;
    private int carStorageSize;

    private int accessoryProviders;
    private int workers;
    private int dealers;

    public ConfigParser(String nameFile) throws IOException{
        Properties p;
        try{
            InputStream resource = ConfigParser.class.getClassLoader().getResourceAsStream(nameFile);
            p = new Properties();

            if(resource != null){
                p.load(resource);
            }
        }catch (IOException e){
            logger.log(Level.SEVERE, "Cannot get production configuration!");
            throw e;
        }

        String value;

        value = p.getProperty("BodyStorageCapacity");
        if(value == null){
            logger.log(Level.SEVERE, "Couldn't read (BodyStorageSize) from properties file");
        }
        else {
            bodyStorageSize = Integer.parseInt(value);
        }

        value = p.getProperty("EngineStorageCapacity");
        if(value == null){
            logger.log(Level.SEVERE, "Couldn't read (MotorStorageSize) from properties file");
        }
        else{
            motorStorageSize = Integer.parseInt(value);
        }

        value = p.getProperty("AccessoryStorageCapacity");
        if(value == null){
            logger.log(Level.SEVERE, "Couldn't read (AccessoryStorageSize) from properties file");
        }
        else{
            accessoryStorageSize = Integer.parseInt(value);
        }

        value = p.getProperty("CarStorageCapacity");
        if(value == null){
            logger.log(Level.SEVERE, "Couldn't read (CarStorageSize) from properties file");
        }
        else{
            carStorageSize = Integer.parseInt(value);
        }

        value = p.getProperty("Workers");
        if(value == null){
            logger.log(Level.SEVERE, "Couldn't read (Count of Workers) from properties file");
        }
        else{
            workers = Integer.parseInt(value);
        }

        value = p.getProperty("Dealers");
        if(value == null){
            logger.log(Level.SEVERE, "Couldn't read (Count of Dealers) from properties file");
        }
        else {
            dealers = Integer.parseInt(value);
        }

        value = p.getProperty("AccessoryProviders");
        if(value == null){
            logger.log(Level.SEVERE, "Couldn't read (Count of accessory providers) from properties file");
        }
        else{
            accessoryProviders = Integer.parseInt(value);
        }
    }

    public int getBodyStorageSize() {
        return bodyStorageSize;
    }

    public int getMotorStorageSize() {
        return motorStorageSize;
    }

    public int getAccessoryStorageSize() {
        return accessoryStorageSize;
    }

    public int getCarStorageSize() {
        return carStorageSize;
    }

    public int getAccessoryProviders() {
        return accessoryProviders;
    }

    public int getWorkers() {
        return workers;
    }

    public int getDealers() {
        return dealers;
    }
}
