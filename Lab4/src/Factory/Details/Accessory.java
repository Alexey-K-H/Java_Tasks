package Factory.Details;

import java.util.concurrent.atomic.AtomicInteger;

public class Accessory extends StorageDetail{
    private static final AtomicInteger next_ID = new AtomicInteger(0);
    private final int id;

    public Accessory(){
        this.id = next_ID.getAndIncrement();
    }

    public String getName(){
        return "Accessory#" + id;
    }

    public int get_ID(){
        return this.id;
    }
}
