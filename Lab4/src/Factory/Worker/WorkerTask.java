package Factory.Worker;

import Factory.Details.Accessory;
import Factory.Details.Body;
import Factory.Details.Car;
import Factory.Details.Engine;
import Factory.Storages.ControlledStorage;
import Factory.Storages.Storage;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WorkerTask implements Runnable{
    private final static Logger logger = Logger.getLogger(WorkerTask.class.getName());

    private final Storage<Body> bodyStorage;

    private final Storage<Engine> engineStorage;

    private final Storage<Accessory> accessoryStorage;

    private final ControlledStorage<Car> carStorage;

    public WorkerTask(Storage<Body> bodyStorage,
                      Storage<Engine> engineStorage,
                      Storage<Accessory> accessoryStorage,
                      ControlledStorage<Car> carStorage){
        this.accessoryStorage = accessoryStorage;
        this.bodyStorage = bodyStorage;
        this.engineStorage = engineStorage;
        this.carStorage = carStorage;
    }

    @Override
    public void run() {
        logger.log(Level.INFO, "Worker:[" + Thread.currentThread().getName() + "] get started.");

        try {
            Body body = bodyStorage.get();
            Engine engine = engineStorage.get();
            Accessory accessory = accessoryStorage.get();
            Car car = new Car();

            logger.log(Level.INFO, Thread.currentThread().getName() + " has made a Car# " + car.get_ID()
                    + " Engine#" + engine.get_ID()
                    + " Body#" + body.get_ID()
                    + " Accessory#" + accessory.get_ID());

            carStorage.put(car);
            logger.log(Level.INFO, Thread.currentThread().getName()
                    + " add a Car#" + car.get_ID()
                    + " to car storage");

        }
        catch (InterruptedException e){
            logger.log(Level.INFO, Thread.currentThread().getName() + " Order denied");
        }
    }
}
