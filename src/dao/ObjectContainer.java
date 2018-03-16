package dao;

import Model.AbstractDAOObject;
import com.rits.cloning.Cloner;

public class ObjectContainer<E extends AbstractDAOObject> {
    private static final long MAX_VALID_TIME = 1_000_000_000L * 60 * 10; //maximum time for data to be valid in cache (10 min)
    private long timestamp;
    private E object;
    private Cloner cloner = new Cloner();

    public ObjectContainer(E object) {
        this.timestamp = System.nanoTime();
        this.object = cloner.deepClone(object);
    }

    public E getObject() {
        return cloner.deepClone(object);
    }

    public void updateObject(E object) {
        this.object = cloner.deepClone(object);
        this.object.setVersion(this.object.getVersion()+1);
    }

    public boolean isActual() {
        return (System.nanoTime() - timestamp) <= MAX_VALID_TIME;
    }
}