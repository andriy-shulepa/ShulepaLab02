package dao;

import Model.AbstractDAOObject;

import java.math.BigInteger;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cache<E extends AbstractDAOObject> {
    private static final int DEFAULT_SIZE = 100;

    private int size;
    private Map<BigInteger, ObjectContainer<E>> cache;

    public Cache() {
        this(DEFAULT_SIZE);
    }

    public Cache(int size) {
        this.size = size;
        cache = new LinkedHashMap<>(size);
        cache.size();
    }

    public void add(E object) {
        if (cache.size() >= size) {
            removeFirstElement();
        }
        cache.put(object.getId(), new ObjectContainer<>(object));
    }

    private void removeFirstElement() {
        ObjectContainer firstElement = cache.entrySet().iterator().next().getValue();
        cache.remove(firstElement.getObject().getId());
    }

    public E get(BigInteger id) {
        if (cache.containsKey(id)) {
            return cache.get(id).getObject();
        } else {
            return null;
        }
    }

    public boolean isActual(BigInteger id) {
        return cache.containsKey(id) && cache.get(id).isActual();

    }

    public void update(E object) {
        ObjectContainer<E> container = cache.get(object.getId());
        container.updateObject(object);
    }

    public void remove(E object) {
        cache.remove(object.getId());
    }

    public boolean isCorrectVersion(E object) {
        return object.getVersion() == cache.get(object.getId()).getObject().getVersion();
    }


}
