package Observe;

import java.util.Vector;

public class ArkanoidObservable {
    private boolean changed;
    private Vector<ArkanoidObserver> observers;

    public ArkanoidObservable(){
        observers = new Vector<>();
    }

    public void addObserver(ArkanoidObserver o){
        if(o == null){
            throw new NullPointerException();
        }
        if(!observers.contains(o)){
            observers.add(o);
        }
    }

    public void deleteObserver(ArkanoidObserver o){
        observers.removeElement(o);
    }

    public void setChanged(){
        changed = true;
    }

    public void clearChanged(){
        changed = false;
    }

    public boolean hasChanged(){
        return changed;
    }

    public void notifyObserver(boolean isOver){
        if(!hasChanged()){
            return;
        }

        Object[] arrLocal;
        arrLocal = observers.toArray();

        for(int i = arrLocal.length-1; i>= 0; i--){
            ((ArkanoidObserver)arrLocal[i]).update(this, isOver);
        }
        clearChanged();
    }
}
