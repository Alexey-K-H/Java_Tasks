package Factory.Storages;

import java.util.LinkedList;

public class Storage<T>{
    private final LinkedList<T> details;
    private final int capacity;
    private int detailsAmount;
    private final String name;

    public Storage(Class<T> typeStorage, int capacity){
        this.detailsAmount = 0;
        this.capacity = capacity;
        this.details = new LinkedList<>();
        this.name = typeStorage.toString() + " storage";
    }

    public synchronized T get() throws InterruptedException{
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

        notifyAll();
    }

    @Override
    public String toString() {
        return this.name;
    }
}
