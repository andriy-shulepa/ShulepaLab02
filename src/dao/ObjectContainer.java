package dao;

import com.rits.cloning.Cloner;

public class ObjectContainer<E> {
    private static final long MAX_VALID_TIME = 1_000_000_000L * 60 * 10; //maximum time for data to be valid in cache (10 min)
    private long timestamp;
    private E object;

    public ObjectContainer(E object) {
        this.timestamp = System.nanoTime();
        this.object = object;
    }

    public E getObject() {
        Cloner cloner = new Cloner();
        return cloner.deepClone(object);
    }

    public boolean isActual() {
        return (System.nanoTime() - timestamp) <= MAX_VALID_TIME;
    }
}