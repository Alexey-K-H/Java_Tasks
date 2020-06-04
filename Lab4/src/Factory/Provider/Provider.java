package Factory.Provider;

import Factory.Details.StorageDetail;
import Factory.Storages.Storage;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Provider<T extends StorageDetail> implements Runnable{
    private final static Logger logger = Logger.getLogger(Provider.class.getName());
    private final Storage<T> storage;
    private final int delay;
    private final Class<T> providerDetailClass;

    public Provider(Storage<T> storage, int delay, Class<T> detail){
        this.storage = storage;
        this.delay = delay;
        this.providerDetailClass = detail;
    }

    @Override
    public void run() {
        logger.log(Level.INFO, Thread.currentThread().getName() + " get started");

        try{
            while(!Thread.currentThread().isInterrupted()){
                T detail = providerDetailClass.getDeclaredConstructor().newInstance();
                storage.put(detail);
                logger.log(Level.INFO,  this.getClass().getSimpleName() + " put a detail to " + storage.toString());
                Thread.sleep(delay);
            }
        }
        catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e){
            e.printStackTrace();
        }
        catch (InterruptedException e){
            logger.log(Level.INFO, Thread.currentThread().getName() + " has finished the work!");
        }
    }
}
