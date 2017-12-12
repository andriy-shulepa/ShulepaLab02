package dao.collectoins;

import dao.*;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class CollectionsAbsractDAO<E extends IDable & Versionable> implements GenericDAO<E> {
    protected Map<BigInteger, ObjectContainer<E>> pool = new HashMap<>();

    @Override
    public E getByPK(BigInteger id) {
        return pool.get(id).getObject();
    }

    @Override
    public Set<E> getAll() {
        Set<E> set = new HashSet<>();
        for (BigInteger key : pool.keySet()) {
            set.add(pool.get(key).getObject());
        }
        return set;
    }

    @Override
    public BigInteger insert(E object) {
        BigInteger id = DAOUtils.generateID(0);
        E customer = cloneObjectWithId(id,object);
        pool.put(id,new ObjectContainer<>(customer));
        return id;
    }

    protected abstract E cloneObjectWithId(BigInteger id, E object);

    @Override
    public void update(E object) throws OutdatedObjectVersionException {

        if (object.getVersion()!=pool.get(object.getId()).getObject().getVersion()) {
            throw new OutdatedObjectVersionException();
        }
        pool.put(object.getId(), new ObjectContainer<>(object));
    }

    @Override
    public void delete(E object) {
        pool.remove(object.getId());
    }
}
