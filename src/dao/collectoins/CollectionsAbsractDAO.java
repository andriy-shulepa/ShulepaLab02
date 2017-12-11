package dao.collectoins;

import com.rits.cloning.Cloner;
import dao.DAOUtils;
import dao.ObjectContainer;
import dao.GenericDAO;
import dao.oracle.IDable;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CollectionsDAO <E extends IDable> implements GenericDAO<E>{
    private Map<BigInteger, ObjectContainer<E>> pool = new HashMap<>();
    @Override
    public E getByPK(BigInteger id) {
        return pool.get(id).getObject();
    }

    @Override
    public Set<E> getAll() {
        Set<E> set = new HashSet<>();
        for(BigInteger key : pool.keySet()) {
            set.add(pool.get(key).getObject());
        }
        return set;
    }

    @Override
    public BigInteger insert(E object) {
        BigInteger id = DAOUtils.generateID(1);
        E object = new E(id);
    }

    @Override
    public void update(E object) {

    }

    @Override
    public void delete(E object) {

    }
}
