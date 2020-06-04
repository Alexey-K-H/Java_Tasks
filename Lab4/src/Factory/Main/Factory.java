package Factory.Main;

import Factory.Controller.CarStorageController;
import Factory.Dealer.Dealer;
import Factory.Details.*;
import Factory.Provider.Provider;
import Factory.Storages.ControlledStorage;
import Factory.Storages.Storage;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Factory {
    private final static Logger logger = Logger.getLogger(Factory.class.getName());
    private int factoryWorkers;
    private int carDealers;
    private int accessoryProviders;
    private int engineStorageCapacity;
    private int bodyStorageCapacity;
    private int carStorageCapacity;
    private int accessoryStorageCapacity;
    private ThreadPoolExecutor threadPool;
    private Thread[] providersAccessory;
    private Thread providerBody;
    private Thread providerEngine;
    private Thread[] dealers;

    private static volatile Factory instance;

    public Factory(){}

    public static Factory getInstance(){
        Factory localInstance = instance;
        if(localInstance == null){
            synchronized (Factory.class){
                localInstance = instance;
                if(localInstance == null){
                    instance = localInstance = new Factory();
                }
            }
        }
        return localInstance;
    }

    public void getWorkPlan(ConfigParser parser){
        factoryWorkers = parser.getWorkers();
        carDealers = parser.getDealers();
        accessoryProviders = parser.getAccessoryProviders();
        engineStorageCapacity = parser.getMotorStorageSize();
        bodyStorageCapacity = parser.getBodyStorageSize();
        carStorageCapacity = parser.getCarStorageSize();
        accessoryStorageCapacity = parser.getAccessoryStorageSize();
    }

    public void startWork() {
        logger.log(Level.INFO, "Factory starts work!");
        logger.log(Level.INFO, "Open storage!");
        Storage<Body> bodyStorage = new Storage<>(Body.class, bodyStorageCapacity);
        Storage<Engine> engineStorage = new Storage<>(Engine.class, engineStorageCapacity);
        Storage<Accessory> accessoryStorage = new Storage<>(Accessory.class, accessoryStorageCapacity);
        ControlledStorage<Car> carStorage = new ControlledStorage<>(Car.class, carStorageCapacity);

        logger.log(Level.INFO, "Call to providers!");
        providersAccessory = new Thread[accessoryProviders];
        dealers = new Thread[carDealers];

        for (int i = 0; i < accessoryProviders; i++) {
            providersAccessory[i] = new Thread(new Provider<>(accessoryStorage, 5000, Accessory.class), "Accessory provider:" + i);
            providersAccessory[i].start();
        }

        providerBody = new Thread(new Provider<>(bodyStorage, 2000, Body.class), "Body Provider");
        providerBody.start();

        providerEngine = new Thread(new Provider<>(engineStorage, 3000, Engine.class), "Engine Provider");
        providerEngine.start();

        logger.log(Level.INFO, "Call to dealers");
        for (int i = 0; i < carDealers; i++) {
            dealers[i] = new Thread(new Dealer(carStorage), "Dealer:" + i);
            dealers[i].start();
        }

        //Создаем пул потоков (Число потоков равно числу работяг)
        threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(factoryWorkers);

        CarStorageController carStorageController = new CarStorageController(carStorage, accessoryStorage, bodyStorage, engineStorage);
        carStorage.addObserver(carStorageController);

        logger.log(Level.INFO, "Start make orders!");
        while (carStorageController.needAdditionalTask()){
            threadPool.execute(carStorageController.createTask());
        }
    }

    public void finishWork(){
        for(int i = 0; i < accessoryProviders; i++){
            providersAccessory[i].interrupt();
        }

        providerBody.interrupt();
        providerEngine.interrupt();

        for(int i = 0; i < carDealers; i++){
            dealers[i].interrupt();
        }

        threadPool.shutdownNow();

        try {
            for(int i = 0; i < accessoryProviders; i++){
                providersAccessory[i].join();
            }

            providerBody.join();
            providerEngine.join();

            for(int i = 0; i < carDealers; i++){
                dealers[i].join();
            }

            threadPool.awaitTermination(1, TimeUnit.MINUTES);
        }
        catch (InterruptedException ex){
            logger.log(Level.INFO, "Every thread finished the work!");
        }

    }
}
