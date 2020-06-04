package Factory.Details;

import java.util.concurrent.atomic.AtomicInteger;

public class Engine extends StorageDetail{
    private static final AtomicInteger next_ID = new AtomicInteger(0);
    private final int id;

    public Engine(){
        this.id = next_ID.getAndIncrement();
    }

    public String getName(){
        return "Engine#" + id;
    }

    public int get_ID(){
        return this.id;
    }

}
