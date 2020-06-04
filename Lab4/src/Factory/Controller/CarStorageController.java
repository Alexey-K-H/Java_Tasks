package Factory.Controller;

import Factory.Details.Accessory;
import Factory.Details.Body;
import Factory.Details.Car;
import Factory.Details.Engine;
import Factory.Storages.ControlledStorage;
import Factory.Storages.Storage;
import Factory.Worker.WorkerTask;

public class CarStorageController implements StorageObserver{
    private int tasksAmount;
    private final ControlledStorage<Car> carStorage;
    private final Storage<Accessory> accessoryStorage;
    private final Storage<Body> bodyStorage;
    private final Storage<Engine> engineStorage;

    public CarStorageController(ControlledStorage<Car> carStorage,
                                Storage<Accessory> accessoryStorage,
                                Storage<Body> bodyStorage,
                                Storage<Engine> engineStorage){
        this.tasksAmount = 0;
        this.engineStorage = engineStorage;
        this.carStorage = carStorage;
        this.bodyStorage = bodyStorage;
        this.accessoryStorage = accessoryStorage;
    }

    //Проверяет необходимость содавать новые задачи
    public boolean needAdditionalTask(){
        tasksAmount++;
        return (carStorage.getDetailsAmount() + tasksAmount < (carStorage.getCapacity() * 0.2));
    }

    public WorkerTask createTask(){
        return new WorkerTask(bodyStorage, engineStorage, accessoryStorage, carStorage);
    }

    @Override
    public void update(boolean isPutToStorage) {
        if(isPutToStorage){
            tasksAmount--;
        }
    }
}
