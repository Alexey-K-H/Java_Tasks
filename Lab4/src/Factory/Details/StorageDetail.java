package Factory.Details;

 public abstract class StorageDetail {
    public abstract String getName();
    public abstract int get_ID();

     @Override
     public String toString() {
         return this.getName();
     }
 }
