package Factory.Storages;

import Factory.Controller.CarStorageController;

import java.util.LinkedList;

public class ControlledStorage<T>{
    private final LinkedList<T> details;
    private final int capacity;
    private int detailsAmount;
    private final String name;
    private int allTimeDetails = 0;
    private CarStorageController controller;

    public ControlledStorage(Class<T> detail, int capacity){
        this.detailsAmount = 0;
        this.capacity = capacity;
        this.details = new LinkedList<>();
        this.name = detail.toString() + " storage";
    }

    public synchronized T get()throws InterruptedException{
        while (this.detailsAmount == 0){
            wait();
        }

        --detailsAmount;

        T detail = this.details.get(this.detailsAmount);
        this.details.remove(this.detailsAmount);

        notifyAll();

        return detail;
    }

    public synchronized void put(T detail) throws InterruptedException {
        while (this.detailsAmount == this.capacity){
            wait();
        }

        this.details.add(this.detailsAmount, detail);

        ++detailsAmount;
        ++allTimeDetails;

        notifyController();
        notifyAll();
    }

    public void addObserver(CarStorageController controller){
        this.controller = controller;
    }

    public void notifyController(){
        this.controller.update(true);
    }

    public int getCapacity(){
        return capacity;
    }

    public synchronized int getDetailsAmount(){
        return detailsAmount;
    }

    @Override
    public String toString(){
        return this.name;
    }
}
