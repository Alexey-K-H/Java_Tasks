package Factory.Dealer;

import Factory.Details.Car;
import Factory.Storages.ControlledStorage;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Dealer implements Runnable{
    private final static Logger logger = Logger.getLogger(Dealer.class.getName());
    private final ControlledStorage<Car> storage;

    public Dealer(ControlledStorage<Car> storage){
        this.storage = storage;
    }

    @Override
    public void run() {
        logger.log(Level.INFO, Thread.currentThread().getName() + " get started");
        try{
             while (!Thread.currentThread().isInterrupted()){
                logger.log(Level.INFO, Thread.currentThread().getName() + " has made order");
                Car car = this.storage.get();
                logger.log(Level.INFO, Thread.currentThread().getName() + " get a Car#" + car.get_ID());

                 Thread.sleep(10000);
            }
        }catch (InterruptedException e){
            logger.log(Level.INFO, Thread.currentThread().getName() + " has finished the work!");
        }
    }
}
